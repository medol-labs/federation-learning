# Native Single-Node Debugging

This is a combined debugging topology, available for every generated environment
(local, dev, test, staging, prod, or a custom environment). It does not change the
formal split-site releases. Never apply both site releases into this namespace.

On macOS, create one Linux ARM64 VM (for example an OrbStack Linux machine).
Install one pinned K3s server inside it, with its agent enabled. No k3d or Docker
node containers are involved. Choose the K3s version explicitly; keep the binary,
air-gap system images, and application image architecture consistent. Keep the
default local-path storage provisioner. APISIX uses NodePort 30080; Traefik is not
required and may be disabled at installation. Configure the API TLS SAN and VM
network access if kubectl runs on the Mac. Access the UI at http://<vm-ip>:30080,
not necessarily localhost:30080. VM creation and K3s installation are not performed
by the deployment helper.

Generate each desired environment through the operations generator. It emits
`environments/<environment>/single-node` and `scripts/k3s-single-node.sh`.
The generated entry contains all applications, one gateway, one Postgres and one
UmaDB, with one namespace and one set of PVCs. Select a fresh cluster/namespace
when switching environments; their resource names are intentionally the same.

The project wrapper adds scheduler RBAC, the aggregation engine and runtime engine
settings. It adapts environment resource names and labels the single node runtime,
but never adds a runtime-only taint. It starts with the generated standalone agent.
Do not also bootstrap a platform-managed agent unless you explicitly disable the
standalone deployment. A single node cannot hold multiple different values for
the runtime-infrastructure-id label; test one infrastructure at a time.

Run inside the Linux node, from this repository:

```bash
# Replace test with any generated environment.
bash operations/k3s/scripts/federation-learning-single-node.sh test render
sudo bash operations/k3s/scripts/federation-learning-single-node.sh test import /delivery/images/*.tar
sudo bash operations/k3s/scripts/federation-learning-single-node.sh test check
sudo SECRETS_FILE=/secure/secrets.test.yaml bash operations/k3s/scripts/federation-learning-single-node.sh test apply
```

Prepare real environment Secrets separately from secrets.example.yaml. Include
the standalone runtime-agent Secret and ensure JWT/internal-token settings agree
with the platform. Import application, dependency, K3s system and every required
Runtime Engine profile under the exact names referenced by the rendered YAML.
Docker/OrbStack host images are not the K3s containerd image store. Registry mode
requires node-side registries.yaml; do not copy an unrelated environment's mirror.

Place datasets in the VM at /workspace/datasets, or mount the Mac directory there
before deployment. The wrapper creates /workspace/tmp/runtime-engine. Local-path
PVCs and engine hostPaths live on the VM disk; deleting the VM loses those data.
The shared project configuration now requests 10Gi for each database PVC. Existing
PVCs are not automatically migrated: local-path may not support expansion. Do not
delete a PVC to resolve an apply error; back up data and plan its migration first.
New backend Pods request 512Mi/250m and are limited to 1536Mi/2 CPUs, with a 180s
startup probe. Environment applicationOverrides can replace these settings.
The single-node component adds a LimitRange so dynamically created engine Pods and
other containers without explicit resources receive 128Mi/100m requests and
2Gi/2 CPU limits. These are initial debugging budgets, not measured DenseNet
capacity guarantees; adjust them for dataset size/batch size and VM memory. Start
with one training task. No namespace-wide training concurrency limit is implied.

The helper rejects common placeholder credentials, imports for the Linux node's
CPU architecture and waits for Deployments to roll out instead of treating apply
success as deployment readiness. The dataset directory must be populated inside
the VM; an empty automatically created mount is not accepted.

Use same-cluster platform/support Service URLs for this combined topology. Browser
API routes should use the gateway origin; review any leftover Supabase settings
against the actual frontend authentication provider. Environment runtime settings
must be configured before generation. Do not run the k3d training flow against
this cluster: its node discovery, taints, image import and kubeconfig are k3d-specific.
