#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
K3S_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
WORK_DIR="${K3S_DIR}/.work"

CLUSTER_NAME="${CLUSTER_NAME:-federation-learning-platform-dev}"
NAMESPACE="${NAMESPACE:-federation-learning-platform}"
K3D_CONFIG="${K3D_CONFIG:-${K3S_DIR}/cluster/k3d-dev.yaml}"
REGISTRY_CONFIG="${REGISTRY_CONFIG:-${K3S_DIR}/cluster/registries.yaml}"
KUBECONFIG_FILE="${KUBECONFIG_FILE:-${WORK_DIR}/kubeconfig-${CLUSTER_NAME}.yaml}"
ENVIRONMENT_OVERLAY="${ENVIRONMENT_OVERLAY:-${K3S_DIR}/environments/dev}"
REGISTRY_OVERLAY="${REGISTRY_OVERLAY:-${K3S_DIR}/environments/dev-registry}"
SECRETS_FILE="${SECRETS_FILE:-${K3S_DIR}/environments/dev/secrets.dev.yaml}"
EXTRA_COMPONENT="${EXTRA_COMPONENT:-}"
EXTRA_OVERLAY="${EXTRA_OVERLAY:-}"
PRE_APPLY_FILE="${PRE_APPLY_FILE:-}"

command="${1:-help}"

usage() {
    cat <<USAGE
Usage:
  $0 create        Create the dev k3d cluster and write kubeconfig
  $0 recreate      Delete and create the dev k3d cluster
  $0 delete        Delete the dev k3d cluster
  $0 kubeconfig    Write kubeconfig and print export command
  $0 apply         Apply the dev manifests
  $0 restart       Roll out restart generated application deployments
  $0 status        Show pods, services, pvc, and recent events

Environment overrides:
  CLUSTER_NAME=${CLUSTER_NAME}
  NAMESPACE=${NAMESPACE}
  K3D_CONFIG=${K3D_CONFIG}
  REGISTRY_CONFIG=${REGISTRY_CONFIG}
  KUBECONFIG_FILE=${KUBECONFIG_FILE}
  ENVIRONMENT_OVERLAY=${ENVIRONMENT_OVERLAY}
  REGISTRY_OVERLAY=${REGISTRY_OVERLAY}
  SECRETS_FILE=${SECRETS_FILE}
  EXTRA_COMPONENT=${EXTRA_COMPONENT}
  EXTRA_OVERLAY=${EXTRA_OVERLAY}
  PRE_APPLY_FILE=${PRE_APPLY_FILE}
USAGE
}

ensure_work_dir() {
    mkdir -p "${WORK_DIR}"
}

registry_args() {
    if [[ -f "${REGISTRY_CONFIG}" ]]; then
        printf '%s\n' "--registry-config" "${REGISTRY_CONFIG}"
    fi
}

kustomize_path() {
    local path="$1"
    if [[ "${path}" == "${K3S_DIR}/"* ]]; then
        printf '../../%s\n' "${path#"${K3S_DIR}/"}"
    else
        printf '%s\n' "${path}"
    fi
}

apply_overlay() {
    local overlay="$1"
    if [[ -f "${EXTRA_COMPONENT}/kustomization.yaml" ]]; then
        local combined="${WORK_DIR}/apply-overlay"
        rm -rf "${combined}"
        mkdir -p "${combined}"
        cat > "${combined}/kustomization.yaml" <<YAML
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization
resources:
  - "$(kustomize_path "${overlay}")"
components:
  - "$(kustomize_path "${EXTRA_COMPONENT}")"
YAML
        echo "[k3d-dev] kubectl apply -k ${combined}"
        kubectl apply -k "${combined}"
        return
    fi
    echo "[k3d-dev] kubectl apply -k ${overlay}"
    kubectl apply -k "${overlay}"
    if [[ -n "${EXTRA_OVERLAY}" && -f "${EXTRA_OVERLAY}/kustomization.yaml" ]]; then
        echo "[k3d-dev] kubectl apply -k ${EXTRA_OVERLAY}"
        kubectl apply -k "${EXTRA_OVERLAY}"
    fi
}

create_cluster() {
    ensure_work_dir
    local args=("--config" "${K3D_CONFIG}")
    while IFS= read -r item; do
        args+=("${item}")
    done < <(registry_args)
    echo "[k3d-dev] k3d cluster create ${args[*]}"
    k3d cluster create "${args[@]}"
    write_kubeconfig
}

delete_cluster() {
    echo "[k3d-dev] k3d cluster delete ${CLUSTER_NAME}"
    k3d cluster delete "${CLUSTER_NAME}"
}

write_kubeconfig() {
    ensure_work_dir
    echo "[k3d-dev] writing kubeconfig: ${KUBECONFIG_FILE}" >&2
    k3d kubeconfig write "${CLUSTER_NAME}" --output "${KUBECONFIG_FILE}" >/dev/null
    echo "export KUBECONFIG=${KUBECONFIG_FILE}"
}

apply_manifests() {
    local overlay="${ENVIRONMENT_OVERLAY}"
    if [[ -f "${REGISTRY_OVERLAY}/kustomization.yaml" ]]; then
        overlay="${REGISTRY_OVERLAY}"
    fi
    echo "[k3d-dev] ensure namespace ${NAMESPACE}"
    kubectl create namespace "${NAMESPACE}" --dry-run=client -o yaml | kubectl apply -f -
    if [[ -f "${SECRETS_FILE}" ]]; then
        echo "[k3d-dev] kubectl -n ${NAMESPACE} apply -f ${SECRETS_FILE}"
        kubectl -n "${NAMESPACE}" apply -f "${SECRETS_FILE}"
    else
        echo "[k3d-dev] skip missing secrets file: ${SECRETS_FILE}"
    fi
    if [[ -f "${PRE_APPLY_FILE}" ]]; then
        echo "[k3d-dev] kubectl -n ${NAMESPACE} apply -f ${PRE_APPLY_FILE}"
        kubectl -n "${NAMESPACE}" apply -f "${PRE_APPLY_FILE}"
    fi
    apply_overlay "${overlay}"
}

restart_apps() {
    kubectl -n "${NAMESPACE}" rollout restart \
        deploy/console \
        deploy/federation-learning-support \
        deploy/federation-learning-platform \
        deploy/federation-learning-runtime-agent \
        deploy/apisix
}

show_status() {
    kubectl -n "${NAMESPACE}" get pods,svc,pvc
    kubectl -n "${NAMESPACE}" get events --sort-by=.lastTimestamp | tail -n 40
}

case "${command}" in
    create)
        create_cluster
        ;;
    recreate)
        delete_cluster || true
        create_cluster
        ;;
    delete)
        delete_cluster
        ;;
    kubeconfig)
        write_kubeconfig
        ;;
    apply)
        apply_manifests
        ;;
    restart)
        restart_apps
        ;;
    status)
        show_status
        ;;
    help|--help|-h)
        usage
        ;;
    *)
        usage
        exit 1
        ;;
esac
