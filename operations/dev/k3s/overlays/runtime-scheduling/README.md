# Federation Learning Runtime Overlay

This overlay is hand-maintained for Federation Learning runtime operations. It references the generated `environments/dev` manifests and the shared `components/runtime-scheduling` component, which adds the Kubernetes permissions, service accounts, runtime dataset mounts, and scheduler configuration required by platform-managed runtime agents and runtime-engine workloads.

Use this overlay instead of `environments/dev` when the platform must deploy runtime agents or when runtime agents must schedule runtime-engine workloads:

```bash
kubectl apply -k overlays/runtime-scheduling
kubectl -n federation-learning-platform get pods,svc,pvc
```

## What It Adds

- `components/runtime-scheduling/runtime-scheduler-rbac.yaml` grants the platform permissions to manage runtime-agent Deployments and Services, and grants runtime agents permissions to manage runtime-engine Deployments and Services.
- `components/runtime-scheduling/patches/platform-runtime-scheduler.yaml` assigns the runtime-agent scheduler ServiceAccount to the platform Deployment.
- `components/runtime-scheduling/patches/runtime-agent-engine-scheduler.yaml` assigns the runtime-engine scheduler ServiceAccount to the runtime-agent Deployment and mounts dataset/runtime work host paths.
- `components/runtime-scheduling/patches/runtime-scheduler-config.yaml` adds Federation Learning runtime scheduler environment variables to the platform and runtime-agent ConfigMaps.

## Add A K3d Runtime Node

A k3d node is a containerized K3s node on the same Docker host. Use native K3s agents instead when nodes must run on different physical machines.

Add an agent node to the existing cluster:

```bash
k3d node create <k3d-node-name> \
  --cluster <cluster-name> \
  --role agent \
  --volume ../../../volumes/datasets:/workspace/datasets \
  --volume ../../../volumes/tmp/runtime-engine:/workspace/tmp/runtime-engine \
  --wait
kubectl get nodes -o wide
```

Replace `<kubernetes-node-name>` with the name returned by `kubectl get nodes`, and use the organization and runtime infrastructure identifiers from the installation guide:

```bash
kubectl label node <kubernetes-node-name> \
  medol.dev/node-role=runtime \
  medol.dev/organization-id=<organization-id> \
  medol.dev/runtime-infrastructure-id=<runtime-infrastructure-id> \
  --overwrite

kubectl taint node <kubernetes-node-name> \
  medol.dev/runtime-only=true:NoSchedule \
  --overwrite
```

Alternatively, create the k3d agent with runtime labels already attached:

```bash
k3d node create <k3d-node-name> \
  --cluster <cluster-name> \
  --role agent \
  --image rancher/k3s:v1.33.5-k3s1 \
  --volume ../../../volumes/datasets:/workspace/datasets \
  --volume ../../../volumes/tmp/runtime-engine:/workspace/tmp/runtime-engine \
  --k3s-node-label "medol.dev/node-role=runtime" \
  --k3s-node-label "medol.dev/organization-id=<organization-id>" \
  --k3s-node-label "medol.dev/runtime-infrastructure-id=<runtime-infrastructure-id>" \
  --wait

kubectl taint node <kubernetes-node-name> \
  medol.dev/runtime-only=true:NoSchedule \
  --overwrite
```

Verify that platform-managed runtime-agent scheduling can find the node:

```bash
kubectl get node <kubernetes-node-name> --show-labels
kubectl get nodes -l medol.dev/runtime-infrastructure-id=<runtime-infrastructure-id>
```

After adding a node, import locally built images into the cluster again when the new node cannot pull them:

```bash
k3d image import <runtime-agent-image> <runtime-engine-image> --cluster <cluster-name>
```
