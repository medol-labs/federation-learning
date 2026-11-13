# Deployment Stage

This entry point prepares infrastructure only. It does not initialize an IAM user,
register participants, seed dictionaries, create datasets, or submit training jobs.
Platform-managed agents are created later through business provisioning commands.

## Configuration

`environment-k3d.json-example` specifies cluster creation/reuse and workerNodes.
`deployment.json-example` specifies the Operations environment, kubeconfig,
target architecture, selected services and registry prefix. Node count is not
participant count or service replica count. Existing undersized clusters fail
instead of being deleted. Use a different cluster name or explicitly resize first.

The target machine needs Docker, K3d, kubectl, Node.js, prepared Operations manifests,
registry connectivity, and configuration/secrets. Images must already be published
for the target architecture. Private registry configuration stays on the target in
`operations/k3s/cluster/registries.yaml`. The existing test-environment preparation
command can initialize disposable test secrets; do not use them in production.

## Local Execution

Run from the project root:

```bash
node simulation/extensions/runtime/deploy.mjs simulation/pipelines/deployment.json-example --plan
node simulation/src/prepare-environment.mjs simulation/pipelines/environment-k3d.json-example
node simulation/extensions/runtime/deploy.mjs simulation/pipelines/deployment.json-example
```

Change workerNodes in environment-k3d.json-example to 20 for twenty runtime slots. This creates
resources, not twenty business organizations or twenty running managed agents.
Ensure the host has sufficient CPU, memory, storage and available ports first.

Environment preparation controls workerNodes independently of participant count.
Deployment only checks the supplied kubeconfig; it never creates the cluster.
It does not label or taint nodes for business ownership. During the project's
participant preparation stage, the existing one-node-per-participant policy
checks capacity before creating records, then labels and taints the assigned
node before confirming infrastructure readiness. This is a handwritten project
extension, not generated Simulation behavior. Insufficient capacity fails without
changing the cluster. Existing labels/taints are not removed automatically.

## Remote Execution

Sync the worker with the existing sync-worker command and matching SSH host config,
then run the deployment-only pipeline. Its host and workDir must match the sync config.

```bash
node simulation/extensions/runtime/sync-worker.mjs simulation/pipelines/remote.json
node simulation/src/run.mjs --mode remote --pipeline simulation/pipelines/deploy-remote.json-example --plan
node simulation/src/run.mjs --mode remote --pipeline simulation/pipelines/deploy-remote.json-example
```

## Output And Limits

Successful deployment writes a unique JSON instance under
`simulation/reports/deployments/` on the execution host. It records cluster,
namespace, capacity, node architecture, resolved images, API addresses and credential
file references, never credential values. Addresses are relative to the execution
host/network; localhost on an SSH worker is not the operator's machine.

Readiness includes the database, event store, both consoles, gateway, platform,
support and aggregation engine. Failure exits nonzero and does not publish a ready
instance or delete resources. Pipeline reports retain step failures.
The deployment entry also writes `<attemptId>.steps.json` as each action starts,
completes or fails. Successful instances reference that attempt ID.

The two-node ARM64 SSH deployment was verified on 2026-11-13 using the existing
published images: cluster preparation, digest resolution, application deployment,
eight deployment readiness checks and platform/support health checks passed.
Twenty-node capacity is configurable but has not been load-tested.

The same deployment adapter accepts an existing Kubernetes kubeconfig, but its
Operations assets must match that cluster's storage, network and registry setup.
Compose deployment, remote report collection, build reuse based on artifact
manifests and automatic host capacity planning are not implemented here.
The K3d extension delegates orchestration and reporting to the generated
`src/runtime/deployment.mjs`, while still reusing infrastructure methods from
training-environment. Its separate entry point never invokes business methods.
The generic adapter contract is documented in `simulation/DEPLOYMENT.md`.
`instances` is mandatory and selects Deployment/Service manifests and replica
counts. Shared supporting resources (Secrets, ConfigMaps, RBAC and storage) remain
in the Operations overlay. Unselected workloads are omitted from apply, not
deleted from a running cluster. Runtime Agent is not selected in the example.
This adapter does not yet resolve dependencies automatically; include required
dependencies explicitly. Do not confuse the
legacy full-training pipeline with this deployment-only pipeline.
