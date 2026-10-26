# Runtime Scheduling Component

This component contains Federation Learning-specific K3s resources: scheduler
ServiceAccounts and RBAC, runtime dataset mounts, the platform aggregation
engine, and platform/runtime-agent configuration patches. It is maintained in
Git and is not an environment or registry overlay.

The generated `environments/dev` environment is the base configuration. A
machine-specific `operations/config/dev.private.yml` may generate the ignored
`environments/dev-registry` directory and `cluster/registries.yaml`. Secrets
are applied separately from an ignored `environments/dev/secrets.dev.yaml`.

Use the project helper to combine the selected environment and this component
in one temporary Kustomization under `k3s/.work`, then apply it once:

```bash
operations/k3s/scripts/federation-learning-dev.sh apply
```

Do not apply `environments/dev-registry` and this component as two separate
releases: a second application of the base environment can restore its image
references. The helper uses `dev-registry` when present and otherwise uses
`dev`.

This component currently names `*-dev-config` and `*-dev-secret` resources and
uses development image tags. It is not production-ready as-is. Before using it
with another environment, move or override those values in that environment's
configuration, then compose the environment and component in a single
Kustomization. Keep registry endpoints, credentials, and environment-specific
Secret files outside Git.

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
