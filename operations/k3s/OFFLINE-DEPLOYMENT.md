# Offline K3s Deployment

This runbook applies to non-`local` environments such as `dev`, `test`, and
production. It assumes native K3s on disconnected machines. Build and export
images on a connected build host; do not build, pull, or push on the target.
The generated K3s `base/` and `environments/<env>/sites/<site>/` directories
are generator-owned. Federation Learning scheduling components and this
runbook are project-owned and must not be added to the generic generator.

The `platform` and `participant` entries are complete deployment boundaries,
not merely offline-package filters. Each site has its own APISIX instance and
only exposes routes for applications assigned to that site. Deploy the sites
to their respective clusters. Configure platform-to-participant and
participant-to-platform URLs with addresses reachable across those clusters;
Kubernetes Service names such as `federation-learning-runtime-agent` resolve
only inside the cluster where that Service exists.


## 1. Record the release

Choose `<env>`, the target CPU architecture (`linux/amd64` or `linux/arm64`),
the K3s version, and an immutable application image tag. Use the same values
for every node. From the Federation Learning repository on the build host:

```bash
git rev-parse HEAD
node operations/images/images.mjs list --environment <env> --platform linux/amd64
```

`<env>` is a placeholder: replace it in every command. Confirm that the
generated operations files match the code and Medol generation inputs before
building. Do not bypass a generation hash or image manifest failure. Preserve
the commit ID, image names/tags, architecture, and archive checksums with the
release.


## 2. Prepare deployment configuration

Complete this configuration review before rendering the delivery in step 4.
It does not require rebuilding images merely because a runtime URL, password,
or token changes. Rebuild only when a changed value is compiled into an image;
regenerate deployment outputs when their configuration source changes. Keep
private deployment values outside generated source files.

From the repository root, deployable site entry points are under
`operations/k3s/environments/<env>/sites/<site>/`. For example, `test` uses:

```text
operations/k3s/environments/test/
  sites/
    platform/
      kustomization.yaml
      configmap.yaml
      secrets.example.yaml
    participant/
      kustomization.yaml
      configmap.yaml
      secrets.example.yaml
```

Review endpoints, service ports, image references, and storage settings in the
ConfigMaps. The packager copies `*.example.yaml` files into each site delivery
as configuration templates. It never reads or packages a real Secret manifest.
Create the real Secret from the example on the target, or deliver it separately
through a controlled channel. Replace database passwords, JWT signing keys, and
internal tokens; JWT keys must meet the configured algorithm's minimum length.
The Secret resource names must match the references in the rendered deployment.

The project-owned `operations/k3s/components/runtime-scheduling/` contains
separate `platform/` and `participant/` components. Automated packaging selects
the component matching the site and adapts its `*-dev-config` references in a
temporary copy; it does not create real credentials or fix business settings.
Inspect the selected component's scheduler configuration, aggregation/runtime
engine resources, URLs, ports, image tags, node selectors, hostPaths, and
ServiceAccount names.
Review the final rendered YAML rather than assuming a suffix replacement
makes the configuration correct. Physical node directories are prepared in
step 6, not during image construction.


## 3. Build and export on the connected host

Individual `export` and `export-dependencies` commands preserve other archives
in the output directory, so application and dependency exports can coexist in
either execution order. Full `package`/`all` commands still reset the output
directory by default; pass `--clean false` to preserve it for those commands.

Build/export use the portable model's local image prefix (for this project,
`fl`), independently of the environment Registry address. Registry settings
select deployment/push destinations only. `--prefix` or `DOCKER_IMAGE_PREFIX`
is an explicit override of the local build/export name; avoid setting it to a
Registry address for offline delivery. Push retags the verified local image
using `--push-prefix`, `DOCKER_PUSH_IMAGE_PREFIX`, or the environment Registry
configuration; it does not rebuild or delete the local tag. To publish the
same build, use `node operations/images/images.mjs push --environment <env>`.

Images built by the old scripts with a Registry-qualified source name need
to be rebuilt under the stable local prefix, or explicitly exported with
their original `--prefix`. The exporter does not silently substitute unrelated
images. Review deployment image references against the delivered tar files.

```bash
node operations/images/images.mjs package \
  --environment <env> \
  --platform linux/amd64 \
  --skip-archive
ls -lh operations/images/<env>/.work/image-bundle/images/
```

This single command builds every application, pulls dependencies, and exports
separate archives for every configured deployment site. `--skip-archive`
avoids creating an additional aggregate `.tar.gz`; the per-site image tar
files required by the offline packager remain in the `images/` directory.
For incremental work, the equivalent `build`, `export`, `pull-dependencies`,
and `export-dependencies` commands can still be run separately.

Replace the platform for ARM targets. Site names come from
`networkTopology.sites` in the operations model; they are not fixed generator
roles. With no `--site`, `export` and `export-dependencies` automatically fan
out across every configured site and write site-prefixed archives, allowing
all exports to coexist. Add `--site <name>` only to rebuild or re-export one
site. Do not send one site's archive to every other site by default.

The application script exports the selected backend/frontend and infrastructure images.
It does **not** export the Runtime Engine. Build each engine plugin profile
required by the release separately, for example:

```bash
cd federation-learning-runtime-engine
node scripts/build-images.mjs --platform linux/amd64 --plugin-profile pytorch-vision
node scripts/export-images.mjs --plugin-profile pytorch-vision \
  --output federation-learning-runtime-engine-images.tar
cd ..
```

Check the engine archive against both the platform aggregation Deployment
image and the dynamically scheduled engine image in the runtime configuration.
Check that the managed agent and participant console image references also
match the exported images. The current component contains explicit image tags;
they do not change automatically with the application release.


## 4. Assemble the automated delivery

After exporting application, dependency, and Runtime Engine images, run from
the repository root on the build host (Node.js and `kubectl` are required):

```bash
node scripts/package-offline.mjs --environment test \
  --engine-archive federation-learning-runtime-engine/federation-learning-runtime-engine-images.tar \
  --output /tmp/fl-offline-test
```

Replace `test` with the actual non-local environment. `/tmp/fl-offline-test`
is an example output path; the command creates it and refuses to overwrite an
existing directory. It does not build or download images. By default it takes
site-prefixed application/dependency tar files from
`operations/images/<env>/.work/image-bundle/images/`. With no `--site`, it
creates one child delivery directory for every configured site. Use
`--site <name>` to package only one site. Repeated `--archive` options explicitly
select files for a single `--site`; they cannot be combined with automatic
multi-site packaging. The optional `--engine-archive`
adds the separately exported Runtime Engine images. Omit it when those images
are delivered separately or are already available on eligible target nodes;
the rendered workloads still reference their configured engine images.

The project wrapper calls the generated generic
`operations/scripts/package-offline.mjs`. The generic tool renders the
pre-isolated `environments/<env>/sites/<site>` entry point and never deletes
applications from a combined manifest. Only the Federation Learning wrapper
selects the matching `runtime-scheduling/<site>` business component and adapts
its dev ConfigMap references. The generic tool has no Federation Learning
rules and accepts repeated `--component` and `--archive` arguments.

The result is a self-contained directory:

```text
fl-offline-test/
  platform/
    images/                 platform-site image tar files and manifests
    manifests/deployment.yaml
    config/                 copied *.example.yaml templates only
    README.txt
    SHA256SUMS
  participant/
    images/                 participant-site image tar files and manifests
    manifests/deployment.yaml
    config/                 copied *.example.yaml templates only
    README.txt
    SHA256SUMS
```

For the automated delivery, `manifests/deployment.yaml` is the complete
non-secret Kubernetes release. The packager renders the selected
`environments/<env>/sites/<site>` entry point with its matching project
component and flattens them into this one file. The site entry already contains
only its own applications, infrastructure, database initialization, ConfigMaps,
gateway, and Secret example. Generated source folders therefore do not need to
be copied to the target. The target still needs a real Secret created from
`config/*.example.yaml`, plus separately managed K3s installation files,
datasets, certificates, or Registry configuration.

Copying `components/` and the selected environment site sources is only
required by the manual source-Kustomize alternative in the appendix. The
generated site entry is self-contained and does not reference the combined
`base/`. Do not combine that alternative with the rendered automated delivery.

Review the rendered YAML before delivery, especially full image references,
Secret names, node selectors, and storage paths. The packager cannot prove that
the supplied tar files contain every referenced image or the correct CPU
architecture. It does not bundle real Secret manifests, K3s installers, or
datasets. The component/environment source files must already be compatible;
Kustomize errors fail packaging. No manual `releases/<env>` entry point is
needed when using this command. The appendix contains manual assembly instructions as an
alternative, not additional steps for this automated package.


## 5. Transfer and check native K3s on target

Transfer each site delivery only to its intended target. The platform package
goes to the platform site; each independent participant receives the
participant package and required Runtime Engine profiles. Deliver real Secrets
separately through an access-controlled channel. If K3s is not installed, also transfer the pinned offline K3s installation artifacts. Use those installation artifacts to install the server and
join agent nodes according to the site's server URL, token, and network plan.
Do not use the generated `cluster/k3d-dev.yaml` for native K3s. On the control
node, continue only when these checks succeed:

```bash
kubectl get nodes -o wide
kubectl -n kube-system get pods
kubectl -n kube-system get deploy coredns local-path-provisioner
```

If CoreDNS, the pause image, or local-path-provisioner cannot start, fix the
K3s air-gap image installation before applying application resources. Ensure
the default StorageClass and DNS work if the generated manifests require them.


## 6. Prepare target nodes and import images

Before deploying Pods, prepare the configured hostPath directories on each
physical K3s node that can run the corresponding workload. A hostPath refers
to a target node's filesystem, not the build host's filesystem. For the
current runtime configuration, eligible participant/aggregation nodes need:

```bash
sudo mkdir -p /workspace/datasets /workspace/tmp/runtime-engine
```

Mount or place the actual participant datasets under the configured data
directory and ensure the workload's user can read datasets and write runtime
work files. Directory creation alone does not provide data or guarantee
permissions. Match node labels/selectors and mounts to the nodes where Pods
will actually run. Do not copy old build-host temporary training results.
Registry-based deployments need this node preparation too, even when image
tar import is skipped.

Change into the received site delivery directory and run
`sha256sum -c SHA256SUMS`. Copy its tar files only to nodes eligible to run that
site's workloads. Import them into **K3s containerd**, not Docker:

```bash
for archive in images/*.tar; do
  sudo k3s ctr images import "$archive"
done
sudo k3s ctr images list | grep -E 'federation-learning|postgres|umadb|apisix'
```

On agent installations where `k3s ctr` is unavailable, use the installed
containerd client against the K3s socket and `k8s.io` namespace. Verify full
repository **and tag** on each node. A Docker `load` does not populate K3s
containerd. Pod and dynamically scheduled workload image pull policies must
permit local images (normally `IfNotPresent`); otherwise K3s can still try an
external registry. Do not use a locally imported `fl/...` image with a
registry-qualified manifest reference unless that exact reference is also
present in containerd.


## 7. Apply the automated delivery

On the control node, change into the received delivery directory. Images
must already have been imported in step 6. Apply the namespace, separately
delivered Secrets, and then the rendered workloads:

```bash
kubectl create namespace federation-learning-platform --dry-run=client -o yaml | kubectl apply -f -
kubectl -n federation-learning-platform apply -f /secure/path/runtime-secrets.yaml
kubectl apply -f manifests/deployment.yaml
```

Replace the Secret path/environment with the separately delivered credentials.
The target does not need Node.js, project sources, or Kustomize composition.


## 8. Verify infrastructure, application, and training

```bash
kubectl -n federation-learning-platform get pods,svc,pvc -o wide
kubectl -n federation-learning-platform get events --sort-by=.lastTimestamp
kubectl -n federation-learning-platform rollout status deploy/postgres
kubectl -n federation-learning-platform rollout status deploy/umadb
kubectl -n federation-learning-platform rollout status deploy/federation-learning-support
kubectl -n federation-learning-platform rollout status deploy/federation-learning-platform
kubectl -n federation-learning-platform rollout status deploy/federation-learning-aggregation-runtime-engine
```

For a failed Pod, use `kubectl -n federation-learning-platform describe pod
<pod>` and `kubectl -n federation-learning-platform logs <pod> --previous`.
Check image pull events first, then Secret/ConfigMap references, storage,
service DNS, and application logs.

Before deploying managed runtime agents, label eligible participant nodes
with `medol.dev/node-role=runtime`, organization ID, and runtime infrastructure
ID, and apply the intended taint only after confirming tolerations. Prepare
datasets and work directories on those same nodes. Then register/verify the
participant infrastructure, deploy the agent, initialize business data, and
submit a small training job. Confirm the platform and participant read models
and the resulting model artifact, not just Pod readiness.


## Alternative: target nodes can reach an internal Registry

Use this branch when target nodes cannot reach the Internet but can reach an
internal Registry. Follow steps 1-3 for source selection, configuration, and
building; replace tar delivery/import (steps 4-7) with the following steps.
Archive export is optional for this branch. Real Secrets, datasets, K3s
installation prerequisites, and workload verification still apply.

Do not run `images.mjs package` for a normal Registry delivery. That command
performs build plus image export and creates site-specific image tar files;
those archives are for offline import. Build and push directly, then run
`scripts/package-offline.mjs --image-source registry` to package only the
Kubernetes manifests and checksums:

```bash
node operations/images/images.mjs build --environment <env> --platform linux/amd64
node operations/images/images.mjs push --environment <env>
node operations/images/images.mjs pull-dependencies --environment <env> --platform linux/amd64
node operations/images/images.mjs push-dependencies --environment <env>
node scripts/package-offline.mjs --environment <env> --image-source registry \
  --output /tmp/application-<env>-registry
```

With no `--site`, these commands publish all configured application and
dependency images once to the shared Registry. Platform and participant
delivery contains manifests and configuration only, not image tar archives;
each node pulls images required by Pods scheduled there. Use `--site <name>`
on application `build`/`push` only when publication itself must be limited to
one site. The generated
`environments/<env>-registry/sites/<site>` entry points are already isolated;
do not apply the parent combined overlay to a physically separated cluster.

### A. Configure and publish from the build host

Configure the selected environment's ignored
`operations/config/<env>.private.yml` using the matching example. For example:

```yaml
registry:
  host: registry.example.com:5000
  namespace: fl
  insecure: true
```

Replace the example host. Use `insecure: true` only for the site's intended
plain-HTTP Registry setup; configure TLS trust/authentication as required by
the actual Registry. Keep credentials outside the public delivery files.
Prepare the local Registry overlay, then push the already built images:

```bash
bash operations/scripts/prepare-registry.sh test
node operations/images/images.mjs push --environment test
node operations/images/images.mjs pull-dependencies --environment test --platform linux/amd64
node operations/images/images.mjs push-dependencies --environment test
cd federation-learning-runtime-engine
node scripts/push-images.mjs --environment test --plugin-profile pytorch-vision
cd ..
node scripts/package-offline.mjs --environment test --image-source registry \
  --output /tmp/federation-learning-test-registry
```

Replace `test` and the engine profile. Push every engine profile needed by
training and aggregation. Push retags the stable local build rather than
rebuilding it. Registry configuration must match the namespace used by the
generated deployment overlay. For K3s dependency mirrors, infrastructure/system
images use their original repository paths at the Registry root rather than
the application `/fl` namespace; review the printed dependency targets.

### B. Prepare every target node and deliver manifests

Deliver the prepared `operations/k3s/cluster/registries.yaml` through the
site's configuration channel when mirrors are required. On native K3s nodes,
install the appropriate configuration as `/etc/rancher/k3s/registries.yaml`
before installation, or restart the applicable K3s server/agent service after
changing it on an existing cluster. Configure credentials and CA trust on
every eligible node. A Docker daemon Registry setting does not configure
K3s containerd. Do not recreate a production cluster just to change Registry
configuration.

Verify the Registry is reachable from the nodes, the required application and
K3s system images are published, and K3s can pull them. A mirror can redirect
image requests, but it cannot supply an image that has not been published.

Transfer the generated site directories under the chosen output, for example
`/tmp/application-test-registry/platform` and
`/tmp/application-test-registry/participant`. Each contains rendered
`manifests/deployment.yaml`, copied `config/*.example.yaml` templates, a
target-side README, and checksums. It contains no image tar files and no real
Secret values. Deliver the corresponding site directory only to that site,
then create or deliver the actual Secret separately.

### C. Package and review Registry-based site releases

After `prepare-registry.sh` and image publication, create all site releases:

```bash
node scripts/package-offline.mjs \
  --environment test \
  --image-source registry \
  --output /tmp/federation-learning-test-registry
```

With no `--site`, the command reads `networkTopology.sites` and creates one
manifest-only directory per site. Add `--site <name>` only to regenerate one
site. It renders `environments/<env>-registry/sites/<site>`, adapts the matching
project component in a temporary copy, excludes real Secrets, and does not copy
image archives. No post-render workload deletion is performed.

Review every rendered image reference. Generated application Deployments use
the Registry overlay's full names. Dependency and custom workload references
may retain their original names and rely on the K3s mirror in
`registries.yaml`; verify those paths exist in the Registry and that every
target node received the same mirror configuration.

### D. Apply on the control node

From the reviewed site delivery directory:

```bash
kubectl create namespace federation-learning-platform --dry-run=client -o yaml | kubectl apply -f -
kubectl -n federation-learning-platform apply -f /secure/path/runtime-secrets.yaml
kubectl apply -f manifests/deployment.yaml
kubectl -n federation-learning-platform get pods,svc,pvc -o wide
kubectl -n federation-learning-platform get events --sort-by=.lastTimestamp
```

Replace paths/environment/namespace with the real values. Continue with step
8 to verify the services and training flow. For `ImagePullBackOff`, inspect
Pod events and distinguish missing tags, wrong repository paths, node-side
TLS/authentication, and network failures. Prefer immutable release tags or
digests; do not assume reapplying an unchanged manifest refreshes a cached
image with a reused tag.

## Release checklist

- [ ] Generation state and commit ID recorded; build passed.
- [ ] Application, dependency, Runtime Engine, and K3s system images match node architecture.
- [ ] Archive checksums verified after transfer; exact image names/tags imported on eligible nodes.
- [ ] Environment Secret, ConfigMaps, ports, paths, image policies, and managed-workload images reviewed.
- [ ] Runtime-scheduling dev references adapted in the deployment copy.
- [ ] One combined Kustomize release rendered and applied.
- [ ] CoreDNS, storage, database, UmaDB, applications, aggregation engine, and one training flow verified.

## Appendix: manual assembly alternative

Do not perform these steps after using the automated packager. They are an alternative that requires transferring the source Kustomize tree.

### Assemble the transfer package

Assemble the following directory on the build host, then transfer it to the
offline deployment environment. This is a delivery checklist, not a complete
package currently produced automatically by `images.mjs`. Preserve paths
inside `operations/k3s/`: Kustomize uses relative references between files.

`release-<env>` is an example folder name, **not a command, a generated
directory, or a required project convention**. For `test`, you may manually
create `/tmp/release-test` on the build host and put the delivery files there.
You can choose any other folder name. `<env>` means replace the placeholder
with an actual environment name; do not type the angle brackets in commands.

The items below have different sources:

- `images/*.tar`: copy existing export results from step 3.
- `operations/k3s/environments/<env>/sites/<site>/`: copy the selected generated
  site from the project. For Registry delivery, also copy the matching
  `<env>-registry/sites/<site>/` entry.
- `operations/k3s/components/runtime-scheduling/`: copy the project-owned
  component tree preserving paths, then select its `<site>/` entry point.
- `operations/k3s/releases/<env>/<site>/kustomization.yaml`: manually create in
  the delivery copy using the next appendix subsection.
- `k3s-install/`: manually collect the offline installation files when needed.
- `SHA256SUMS`: create a checksum list after assembling the final files.

The automated packager produces rendered YAML instead of this source tree. The tree below
describes the intended result of that manual preparation, not existing files
that can all be found under a pre-existing `release-test` directory.

```text
release-<env>-<site>/
  images/
    <site>-backend-images.tar
    <site>-<frontend>-images.tar
    <site>-dependency-images.tar
    federation-learning-runtime-engine-images.tar
    *.manifest.json (when emitted)
  operations/k3s/
    environments/<env>/
      sites/<site>/
        kustomization.yaml
        secrets.example.yaml
    components/runtime-scheduling/
      platform/
      participant/
      patches/
      *.yaml
    releases/<env>/<site>/
      kustomization.yaml
  k3s-install/
    <version-matched K3s binary, install artifacts, and K3s air-gap images>
  SHA256SUMS
```

`releases/<env>/<site>/kustomization.yaml` is the deployment-owned combined entry
point described in the next appendix subsection. Prepare it before transferring the package; it
references the selected generated site and matching project-owned runtime
scheduling component. Apply that entry point on the target, not the parent
environment or component by itself.

Create a separate manual tree for each deployment site rather than sending a
combined platform/participant image set everywhere. The exact application archive names are those under
`operations/images/<env>/.work/image-bundle/images/`. Include the image
bundle manifest and image archive manifests when present. Create and verify
checksums before and after transfer. The K3s installation images are separate
from the site dependency archive; prepare them for the pinned K3s version and
target architecture. Do not package real credentials or private environment
configuration in a broadly distributed release archive.

If the target already has a working, version-compatible K3s cluster, the
`k3s-install/` installation files are not needed. Deliver the real environment
Secret manifests separately through an access-controlled channel and prepare
them on the control node before applying the release.

Training datasets and model artifacts are separate deployment inputs. Place
node-local inputs on the physical participant nodes that will run those
workloads, or provide the configured artifact service. Runtime work directories
normally need to be created with appropriate permissions on eligible nodes;
do not copy a build host's temporary training results as deployment inputs.


### Compose and preview a single Kustomize release

Do not apply a generated site and its runtime-scheduling component as two
independent releases: render and apply them as one release.
In the delivery copy, manually create
`operations/k3s/releases/<env>/<site>/kustomization.yaml` with this content
(replace both placeholders):

```yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization
resources:
  - ../../../environments/<env>/sites/<site>
components:
  - ../../../components/runtime-scheduling/<site>
```

The three `../` segments go from `releases/<env>/<site>/` back to `k3s/`. Keep this
file as an environment-owned deployment input; it is not generated. Run the
following from the delivery folder containing `operations/` (for example,
`/tmp/release-test` on the build host), then inspect the rendered result:

```bash
kubectl kustomize operations/k3s/releases/<env>/<site> > /tmp/fl-<env>-<site>-rendered.yaml
grep -E 'image:|imagePullPolicy:|serviceAccountName:|secretKeyRef:' /tmp/fl-<env>-<site>-rendered.yaml
```

Confirm there are no leftover `-dev-config` or `-dev-secret` references when
deploying another environment, and no registry-only image references if using
local imports. Verify all referenced images exist under the same name/tag in
K3s containerd on their eligible nodes.


### Apply Secret, then the combined release

On the control node, with a working kubeconfig, change into the transferred
delivery folder containing `operations/`. For example, if the folder was
transferred to `/opt/release-test`, first run `cd /opt/release-test`. That path
is an example you choose, not a directory created by the deployment scripts.
Then run the following, replacing `<env>` with the actual environment:

```bash
kubectl create namespace federation-learning-platform --dry-run=client -o yaml | kubectl apply -f -
kubectl -n federation-learning-platform apply \
  -f /secure/path/runtime-secrets.yaml
kubectl -n federation-learning-platform get secrets
kubectl apply -k operations/k3s/releases/<env>/<site>
```

The namespace is currently named `federation-learning-platform` in the
generated manifests. If the target environment changes it, use the actual
namespace consistently. Apply updated Secret/ConfigMap values and restart
affected workloads when necessary; an existing Pod's environment variables do
not change in place.
