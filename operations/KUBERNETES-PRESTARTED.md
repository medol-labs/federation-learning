# Kubernetes 固定 Runtime Engine 部署

本方案复用 Compose 的 prestarted 模式。平台固定部署 aggregation-engine，参与方固定部署 training-engine。Agent 经 ClusterIP Service 提交、查询和释放作业，不创建或删除 Kubernetes Engine Deployment。不需要 runtime-engine-scheduler RBAC，也不要叠加原 runtime-scheduling 动态调度组件。

## 1. 准备

先生成目标环境的 Kubernetes 站点配置：`operations/kubernetes/environments/<env>/sites/platform` 和 `participant`。本脚本不构建或发布镜像。

以下示例使用 test；其他环境替换环境名并先生成对应站点。

在实际 Engine 节点创建工作目录；参与方还需放置数据集，并确保容器用户有相应读写权限：

```bash
# 参与方节点
sudo mkdir -p /data/participant-01/datasets /data/participant-01/runtime-work
# 平台聚合节点
sudo mkdir -p /data/platform/aggregation-work
```

`--node` 填节点的 kubernetes.io/hostname 标签值。Agent 与训练 Engine 固定到同一个节点，挂载相同 hostPath，因此不能把它们迁移到没有这些文件的节点。目录必须预先存在。每个站点一套 Engine，副本固定为 1，更新使用 Recreate；这是非高可用部署。

## 2. 生成业务 Overlay

在 Federation Learning 项目根目录运行：

```bash
node scripts/prepare-kubernetes-runtime.mjs --environment test --site participant \
  --node participant-01 \
  --engine-image registry.example.com:5000/fl/federation-learning-runtime-engine:pytorch-vision-0.0.1-SNAPSHOT \
  --dataset-dir /data/participant-01/datasets --work-dir /data/participant-01/runtime-work

node scripts/prepare-kubernetes-runtime.mjs --environment test --site platform \
  --node platform-01 \
  --engine-image registry.example.com:5000/fl/federation-learning-runtime-engine:pytorch-vision-0.0.1-SNAPSHOT \
  --work-dir /data/platform/aggregation-work
```

产物在 `operations/kubernetes/overlays/prestarted/<env>/<site>/`，不提交真实部署配置。重复运行覆盖该 overlay。镜像支持 `--plugin-profile`，默认 pytorch-vision；参与方执行计划的镜像引用必须与预启动 Engine 一致，不会自动换镜像。

## 3. 配置并部署

按站点原有 secrets.example.yaml 准备私有 Secret，先创建命名空间再应用 Secret。Engine 引用相应业务服务的 `<服务名>-<env>-secret` 内的 MEDOL_SECURITY_INTERNAL_TOKEN。参与方 Engine 从 Agent ConfigMap 的 FEDERATION_LEARNING_SUPPORT_URL 获取远程文件服务地址，必须配置为可访问的平台网关路径，不能使用跨集群 Docker 服务名。同步地址、平台 API 地址、JWT、CORS 同站点部署要求。

Registry、认证、架构、存储类、网关暴露方式均由站点基础配置管理。节点必须能够拉取镜像。不要将两套同名站点资源直接应用到同一集群的同一 namespace。

```bash
# 在各自对应集群的 context 中操作。先渲染检查，再部署。
kubectl kustomize operations/kubernetes/overlays/prestarted/test/participant
kubectl apply -k operations/kubernetes/overlays/prestarted/test/participant
kubectl -n federation-learning-platform rollout status deploy/training-engine
kubectl -n federation-learning-platform rollout status deploy/federation-learning-runtime-agent

# 平台集群
kubectl apply -k operations/kubernetes/overlays/prestarted/test/platform
kubectl -n federation-learning-platform rollout status deploy/aggregation-engine
```

Kubernetes 没有 Compose 的 depends_on；先确认 Engine Ready 再提交训练任务。作业结束只释放引擎内作业，不销毁常驻 Engine。多个参与方推荐独立集群；共享集群需独立 namespace、数据路径及网关入口，并对全部站点资源做 namespace 隔离，而不只是改 Engine 名字。

此入口独立于现有 `scripts/package-offline.mjs`（该脚本仍使用 K3s 动态调度组件）。可用 `kubectl kustomize <overlay> > deployment.yaml` 导出最终清单，另行携带 Secret 和镜像；不要把旧动态调度包当作本方案的交付物。
