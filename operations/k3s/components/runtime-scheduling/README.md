# Runtime Scheduling Component

For disconnected native K3s environments other than `local`, follow the
[offline deployment runbook](../../OFFLINE-DEPLOYMENT.md).

This component contains Federation Learning-specific K3s resources: scheduler
ServiceAccounts and RBAC, runtime dataset mounts, the platform aggregation
engine, and platform/runtime-agent configuration patches. It is maintained in
Git and is not an environment or registry overlay.

The helper defaults to the generated `environments/local` environment. A
machine-specific `operations/config/local.private.yml` may generate the ignored
`environments/local-registry` directory and `cluster/registries.yaml`. Secrets
are applied separately from an ignored `environments/local/secrets.local.yaml`.

Use the project helper to combine the selected environment and this component
in one temporary Kustomization under `k3s/.work`, then apply it once:

```bash
operations/k3s/scripts/federation-learning-dev.sh apply
# Select another generated environment explicitly:
FL_OPERATIONS_ENVIRONMENT=dev operations/k3s/scripts/federation-learning-dev.sh apply
```

Do not apply the registry overlay and this component as two separate
releases: a second application of the base environment can restore its image
references. The helper uses `<environment>-registry` when present and otherwise
uses `<environment>`.

The source patches use dev resource names. The helper substitutes the selected
environment's ConfigMap and Secret names when assembling its temporary component,
including the managed runtime-agent security Secret. These are local debugging
defaults; production requires environment-owned configuration. Keep registry
endpoints, credentials, and environment-specific Secret files outside Git.

Native K3s participant nodes need the dataset and runtime-work paths mounted
at `/workspace/datasets` and `/workspace/tmp/runtime-engine`. Label each
runtime node with `medol.dev/node-role=runtime`, its organization ID, and its
runtime infrastructure ID before asking the platform to deploy an agent.

```bash
kubectl label node <node-name> \
  medol.dev/node-role=runtime \
  medol.dev/organization-id=<organization-id> \
  medol.dev/runtime-infrastructure-id=<runtime-infrastructure-id> \
  --overwrite
kubectl taint node <node-name> medol.dev/runtime-only=true:NoSchedule --overwrite
```

On k3d, node creation has no `--volume` flag. Put host mounts in the k3d
cluster configuration when creating the cluster, or use the project helper
that prepares that configuration. For physical participant machines, use
native K3s agents instead of k3d nodes.
