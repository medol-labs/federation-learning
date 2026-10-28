# Offline K3s Deployment

This runbook applies to non-`local` environments such as `dev`, `test`, and
production. It assumes native K3s on disconnected machines. Build and export
images on a connected build host; do not build, pull, or push on the target.
The generated K3s `base/` and `environments/<env>/` are generator-owned. This
component and this runbook are Federation Learning-owned and must not be added
to the generic generator.

## 0. Record the release

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

## 1. Build and export on the connected host

```bash
node operations/images/images.mjs build --environment <env> --platform linux/amd64
node operations/images/images.mjs export --environment <env> --platform linux/amd64
node operations/images/images.mjs pull-dependencies --environment <env> --platform linux/amd64
node operations/images/images.mjs export-dependencies --environment <env> --platform linux/amd64
ls -lh operations/images/<env>/.work/image-bundle/images/
```

Replace the platform for ARM targets. The application script exports the
backend, platform console, participant console, and infrastructure images.
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

## 2. Assemble the transfer package

Transfer the following, preserving paths inside `operations/k3s/`:

```text
release-<env>/
  images/
    backend-images.tar
    federation-learning-console-images.tar
    federation-learning-participant-console-images.tar
    dependency-images.tar
    federation-learning-runtime-engine-images.tar
    *.manifest.json (when emitted)
  operations/k3s/
    base/
    environments/<env>/
    components/runtime-scheduling/
  k3s-install/
    <version-matched K3s binary, install artifacts, and K3s air-gap images>
  SHA256SUMS
```

The exact application archive names are those under
`operations/images/<env>/.work/image-bundle/images/`. Include the image
bundle manifest and image archive manifests when present. Create and verify
checksums before and after transfer. The K3s installation images are separate
from `dependency-images.tar`; prepare them for the pinned K3s version and
target architecture. Do not package real credentials or private environment
configuration in a broadly distributed release archive.

Training datasets, model artifacts, and node-local runtime work directories
are separate deployment inputs. Place them on the physical participant nodes
that will run those workloads.

## 3. Install and check native K3s

Use the pinned offline K3s installation artifacts to install the server and
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

## 4. Import on every eligible node

Copy the relevant tar files to every node that can host application, managed
agent, or engine Pods. Import them into **K3s containerd**, not Docker:

```bash
sudo k3s ctr images import images/backend-images.tar
sudo k3s ctr images import images/federation-learning-console-images.tar
sudo k3s ctr images import images/federation-learning-participant-console-images.tar
sudo k3s ctr images import images/dependency-images.tar
sudo k3s ctr images import images/federation-learning-runtime-engine-images.tar
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

## 5. Prepare environment-owned configuration

Work in the transferred package, not in generator-owned source files. Confirm
`environments/<env>/configmap.yaml` and create an untracked
`environments/<env>/secrets.<env>.yaml` from `secrets.example.yaml`. Replace
every example credential, JWT signing key, internal token, endpoint, storage
setting, and image reference as appropriate. Keep the Secret values out of Git
and release archives. The JWT signing key must meet the configured algorithm's
minimum key length.

The runtime-scheduling component currently contains `*-dev-config` and
`*-dev-secret` references. In the **deployment copy**, change those references
to the actual names in `environments/<env>/configmap.yaml` and
`secrets.<env>.yaml`. Specifically inspect
`patches/runtime-scheduler-config.yaml` and
`platform-aggregation-runtime-engine.yaml`; also inspect every other patch,
URL, service port, image tag, node selector, hostPath, and ServiceAccount name.
Merely changing the environment suffix is not enough to validate a release.

The platform aggregation engine and runtime workloads require their hostPath
directories on the nodes where they are scheduled. Prepare
`/workspace/datasets` and `/workspace/tmp/runtime-engine` as appropriate for
the node role and arrange the dataset mount before deploying workloads.

## 6. Compose and preview a single Kustomize release

Do not apply `environments/<env>` and `components/runtime-scheduling` as two
independent releases: the second apply can revert changes made by the first.
Create a deployment-owned Kustomization alongside `base/`, `environments/`,
and `components/` with this content (replace `<env>` with the selected name):

```yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization
resources:
  - ../environments/<env>
components:
  - ../components/runtime-scheduling
```

If this file lives in `operations/k3s/releases/<env>/kustomization.yaml`, the
relative paths above are correct. Keep `releases/<env>` as an environment-owned
deployment input; it is not generated. Preview and inspect the rendered result:

```bash
kubectl kustomize operations/k3s/releases/<env> > /tmp/fl-<env>-rendered.yaml
grep -E 'image:|imagePullPolicy:|serviceAccountName:|secretKeyRef:' /tmp/fl-<env>-rendered.yaml
```

Confirm there are no leftover `-dev-config` or `-dev-secret` references when
deploying another environment, and no registry-only image references if using
local imports. Verify all referenced images exist under the same name/tag in
K3s containerd on their eligible nodes.

## 7. Apply Secret, then the combined release

On the control node, with a working kubeconfig:

```bash
kubectl apply -f operations/k3s/base/namespace.yaml
kubectl -n federation-learning-platform apply \
  -f operations/k3s/environments/<env>/secrets.<env>.yaml
kubectl -n federation-learning-platform get secrets
kubectl apply -k operations/k3s/releases/<env>
```

The namespace is currently named `federation-learning-platform` in the
generated manifests. If the target environment changes it, use the actual
namespace consistently. Apply updated Secret/ConfigMap values and restart
affected workloads when necessary; an existing Pod's environment variables do
not change in place.

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

## Release checklist

- [ ] Generation state and commit ID recorded; build passed.
- [ ] Application, dependency, Runtime Engine, and K3s system images match node architecture.
- [ ] Archive checksums verified after transfer; exact image names/tags imported on eligible nodes.
- [ ] Environment Secret, ConfigMaps, ports, paths, image policies, and managed-workload images reviewed.
- [ ] Runtime-scheduling dev references adapted in the deployment copy.
- [ ] One combined Kustomize release rendered and applied.
- [ ] CoreDNS, storage, database, UmaDB, applications, aggregation engine, and one training flow verified.
