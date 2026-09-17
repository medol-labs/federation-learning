#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
K3S_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
PROJECT_ROOT="$(cd "${K3S_DIR}/../../.." && pwd)"
WORK_DIR="${K3S_DIR}/.work"
COMMAND="${1:-help}"

CLUSTER_NAME="${CLUSTER_NAME:-federation-learning-platform-dev}"
DATASETS_HOST_ROOT="${FL_K3D_DATASETS_HOST_ROOT:-${PROJECT_ROOT}/volumes/datasets}"
RUNTIME_ENGINE_HOST_ROOT="${FL_K3D_RUNTIME_ENGINE_HOST_ROOT:-${PROJECT_ROOT}/volumes/tmp/runtime-engine}"
FEDERATION_LEARNING_K3D_CONFIG="${WORK_DIR}/k3d-federation-learning-dev.yaml"
FEDERATION_LEARNING_RUNTIME_COMPONENT="${WORK_DIR}/runtime-scheduling-component"
IMAGE_VERSION="${IMAGE_VERSION:-0.0.1-SNAPSHOT}"

export PRE_APPLY_FILE="${PRE_APPLY_FILE:-${K3S_DIR}/components/runtime-scheduling/runtime-scheduler-rbac.yaml}"

image_from_registry_overlay() {
    local image_name="$1"
    local registry_overlay="${REGISTRY_OVERLAY:-${K3S_DIR}/environments/dev-registry}"
    local registry_kustomization="${registry_overlay}/kustomization.yaml"
    if [[ ! -f "${registry_kustomization}" ]]; then
        return 1
    fi
    awk -v target="medol/${image_name}" '
        $1 == "-" && $2 == "name:" {
            in_block = ($0 ~ "\"" target "\"")
            new_name = ""
            new_tag = ""
        }
        in_block && $1 == "newName:" {
            new_name = $2
            gsub(/"/, "", new_name)
        }
        in_block && $1 == "newTag:" {
            new_tag = $2
            gsub(/"/, "", new_tag)
            if (new_name != "") {
                print new_name ":" new_tag
                exit
            }
        }
    ' "${registry_kustomization}"
}

image_prefix_from_registry_overlay() {
    local registry_overlay="${REGISTRY_OVERLAY:-${K3S_DIR}/environments/dev-registry}"
    local registry_kustomization="${registry_overlay}/kustomization.yaml"
    if [[ ! -f "${registry_kustomization}" ]]; then
        return 1
    fi
    awk '
        $1 == "newName:" {
            image_prefix = $2
            gsub(/"/, "", image_prefix)
            split(image_prefix, parts, "/")
            image_prefix = parts[1]
            for (i = 2; i < length(parts); i++) {
                image_prefix = image_prefix "/" parts[i]
            }
            print image_prefix
            exit
        }
    ' "${registry_kustomization}"
}

default_image() {
    local image_name="$1"
    local inferred_image_prefix
    inferred_image_prefix="$(image_prefix_from_registry_overlay || true)"
    local image_prefix="${DOCKER_IMAGE_PREFIX:-${inferred_image_prefix}}"
    image_prefix="${image_prefix%/}"
    if [[ -n "${image_prefix}" ]]; then
        printf '%s/%s:%s\n' "${image_prefix}" "${image_name}" "${IMAGE_VERSION}"
    else
        printf 'medol/%s:%s\n' "${image_name}" "${IMAGE_VERSION}"
    fi
}

runtime_image() {
    local env_name="$1"
    local image_name="$2"
    local configured="${!env_name:-}"
    local registry_image
    if [[ -n "${configured}" ]]; then
        printf '%s\n' "${configured}"
        return
    fi
    registry_image="$(image_from_registry_overlay "${image_name}")"
    if [[ -n "${registry_image}" ]]; then
        printf '%s\n' "${registry_image}"
    else
        default_image "${image_name}"
    fi
}

prepare_federation_learning_runtime_component() {
    local runtime_agent_image
    local runtime_engine_image
    local participant_console_image
    runtime_agent_image="$(runtime_image FL_RUNTIME_AGENT_IMAGE federation-learning-runtime-agent)"
    runtime_engine_image="$(runtime_image FL_RUNTIME_ENGINE_IMAGE federation-learning-runtime-engine)"
    participant_console_image="$(runtime_image FL_PARTICIPANT_CONSOLE_IMAGE federation-learning-participant-console)"

    rm -rf "${FEDERATION_LEARNING_RUNTIME_COMPONENT}"
    mkdir -p "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/patches"
    cp "${K3S_DIR}/components/runtime-scheduling/runtime-scheduler-rbac.yaml" \
        "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/runtime-scheduler-rbac.yaml"
    cp "${K3S_DIR}/components/runtime-scheduling/patches/platform-runtime-scheduler.yaml" \
        "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/patches/platform-runtime-scheduler.yaml"
    cp "${K3S_DIR}/components/runtime-scheduling/patches/runtime-agent-engine-scheduler.yaml" \
        "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/patches/runtime-agent-engine-scheduler.yaml"
    cp "${K3S_DIR}/components/runtime-scheduling/patches/runtime-scheduler-config.yaml" \
        "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/patches/runtime-scheduler-config.yaml"
    cat > "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/kustomization.yaml" <<YAML
apiVersion: kustomize.config.k8s.io/v1alpha1
kind: Component
resources:
  - "runtime-scheduler-rbac.yaml"
patches:
  - path: "patches/platform-runtime-scheduler.yaml"
  - path: "patches/runtime-agent-engine-scheduler.yaml"
  - path: "patches/runtime-scheduler-config.yaml"
  - path: "runtime-agent-disabled.yaml"
  - path: "runtime-scheduler-images.yaml"
YAML
    cat > "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/runtime-agent-disabled.yaml" <<YAML
apiVersion: "apps/v1"
kind: "Deployment"
metadata:
  name: "federation-learning-runtime-agent"
spec:
  replicas: 0
YAML
    cat > "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/runtime-scheduler-images.yaml" <<YAML
apiVersion: "v1"
kind: "ConfigMap"
metadata:
  name: "federation-learning-platform-dev-config"
data:
  PLATFORM_RUNTIME_K3S_AGENT_IMAGE: "${runtime_agent_image}"
  PLATFORM_RUNTIME_K3S_PARTICIPANT_CONSOLE_IMAGE: "${participant_console_image}"
  PLATFORM_RUNTIME_K3S_RUNTIME_ENGINE_IMAGE: "${runtime_engine_image}"
---
apiVersion: "v1"
kind: "ConfigMap"
metadata:
  name: "federation-learning-runtime-agent-dev-config"
data:
  MEDOL_SYNC_ENABLED: "true"
  MEDOL_SYNC_FIXED_DELAY_MS: "5000"
  MEDOL_SYNC_MODE: "outbox-delta"
  MEDOL_SYNC_SOURCE_BASE_URL: "http://federation-learning-platform:8081"
  RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_KUBERNETES_IMAGE: "${runtime_engine_image}"
YAML
}

prepare_federation_learning_k3d_config() {
    mkdir -p "${WORK_DIR}" "${DATASETS_HOST_ROOT}" "${RUNTIME_ENGINE_HOST_ROOT}"
    cat > "${FEDERATION_LEARNING_K3D_CONFIG}" <<YAML
apiVersion: "k3d.io/v1alpha5"
kind: "Simple"
metadata:
  name: "${CLUSTER_NAME}"
image: "rancher/k3s:v1.33.5-k3s1"
servers: 1
agents: 2
kubeAPI:
  hostIP: "127.0.0.1"
  hostPort: "6550"
ports:
  - port: "30080:30080"
    nodeFilters:
      - "server:0"
  - port: "30082:30082"
    nodeFilters:
      - "server:0"
volumes:
  - volume: "${DATASETS_HOST_ROOT}:/workspace/datasets"
    nodeFilters:
      - "agent:*"
  - volume: "${RUNTIME_ENGINE_HOST_ROOT}:/workspace/tmp/runtime-engine"
    nodeFilters:
      - "agent:*"
options:
  k3d:
    wait: true
    timeout: "120s"
  k3s:
    extraArgs:
      - arg: "--disable=traefik"
        nodeFilters:
          - "server:*"
      - arg: "--disable-default-registry-endpoint"
        nodeFilters:
          - "server:*"
      - arg: "--disable-default-registry-endpoint"
        nodeFilters:
          - "agent:*"
YAML
}

case "${COMMAND}" in
    create|recreate)
        prepare_federation_learning_k3d_config
        export CLUSTER_NAME
        export K3D_CONFIG="${K3D_CONFIG:-${FEDERATION_LEARNING_K3D_CONFIG}}"
        ;;
esac

case "${COMMAND}" in
    apply)
        prepare_federation_learning_runtime_component
        export EXTRA_COMPONENT="${EXTRA_COMPONENT:-${FEDERATION_LEARNING_RUNTIME_COMPONENT}}"
        ;;
esac

exec "${SCRIPT_DIR}/k3d-dev.sh" "$@"
