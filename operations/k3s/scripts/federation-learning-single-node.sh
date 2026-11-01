#!/usr/bin/env bash
set -euo pipefail
K3S_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ENVIRONMENT="${1:?Usage: federation-learning-single-node.sh <environment> render|import|check|apply [archive ...]}"
COMMAND="${2:-check}"
[[ "$ENVIRONMENT" =~ ^[a-z][a-z0-9-]*$ ]] || exit 1
K3S_BIN="${K3S_BIN:-k3s}"
mkdir -p "$K3S_DIR/.work"
COMPONENT="$(mktemp -d "$K3S_DIR/.work/native-runtime.XXXXXX")"
trap 'rm -rf "$COMPONENT"' EXIT
cp -R "$K3S_DIR/components/runtime-scheduling/." "$COMPONENT/"
awk '{print; if ($0 == "resources:") print "  - single-node-budget.yaml"}' "$COMPONENT/kustomization.yaml" > "$COMPONENT/kustomization.tmp"
mv "$COMPONENT/kustomization.tmp" "$COMPONENT/kustomization.yaml"
cat > "$COMPONENT/single-node-budget.yaml" <<'YAML'
apiVersion: v1
kind: LimitRange
metadata:
  name: single-node-container-budget
spec:
  limits:
    - type: Container
      default: {cpu: "2", memory: 2Gi}
      defaultRequest: {cpu: 100m, memory: 128Mi}
YAML
while IFS= read -r file; do
    sed -e "s/-dev-config/-${ENVIRONMENT}-config/g" -e "s/-dev-secret/-${ENVIRONMENT}-secret/g" "$file" > "$file.tmp"
    mv "$file.tmp" "$file"
done < <(find "$COMPONENT" -type f -name '*.yaml')
printf '  - path: single-node-config.yaml\n' >> "$COMPONENT/kustomization.yaml"
cat > "$COMPONENT/single-node-config.yaml" <<YAML
apiVersion: v1
kind: ConfigMap
metadata:
  name: federation-learning-platform-${ENVIRONMENT}-config
data:
  PLATFORM_RUNTIME_K3S_AGENT_SECURITY_SECRET_NAME: federation-learning-runtime-agent-${ENVIRONMENT}-secret
YAML
printf '  - path: single-node-resources.yaml\n' >> "$COMPONENT/kustomization.yaml"
cat > "$COMPONENT/single-node-resources.yaml" <<'YAML'
apiVersion: apps/v1
kind: Deployment
metadata:
  name: federation-learning-aggregation-runtime-engine
spec:
  template:
    spec:
      containers:
        - name: runtime-engine
          resources:
            requests: {cpu: 250m, memory: 256Mi}
            limits: {cpu: "2", memory: 2Gi}
YAML
if [[ "$COMMAND" == apply ]]; then
    bash "$K3S_DIR/scripts/k3s-single-node.sh" "$ENVIRONMENT" check
    [[ -d /workspace/datasets ]] || { echo 'Prepare the real dataset at /workspace/datasets on the Linux node first.' >&2; exit 1; }
    [[ -n "$(find /workspace/datasets -mindepth 1 -maxdepth 1 -print -quit)" ]] || { echo 'Dataset mount is empty; populate it inside the Linux VM before deployment.' >&2; exit 1; }
    mkdir -p /workspace/tmp/runtime-engine
    NODE="$("$K3S_BIN" kubectl get nodes -o name)"
    "$K3S_BIN" kubectl label "$NODE" medol.dev/node-role=runtime --overwrite
fi
EXTRA_COMPONENT="$COMPONENT" bash "$K3S_DIR/scripts/k3s-single-node.sh" "$@"
