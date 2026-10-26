#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
K3S_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
PROJECT_ROOT="$(cd "${K3S_DIR}/../.." && pwd)"
WORK_DIR="${K3S_DIR}/.work"
COMMAND="${1:-help}"
NAMESPACE="${NAMESPACE:-federation-learning-platform}"

CLUSTER_NAME="${CLUSTER_NAME:-federation-learning-platform-dev}"
DATASETS_HOST_ROOT="${FL_K3D_DATASETS_HOST_ROOT:-${PROJECT_ROOT}/volumes/datasets}"
RUNTIME_ENGINE_HOST_ROOT="${FL_K3D_RUNTIME_ENGINE_HOST_ROOT:-${PROJECT_ROOT}/volumes/tmp/runtime-engine}"
FEDERATION_LEARNING_K3D_CONFIG="${WORK_DIR}/k3d-federation-learning-dev.yaml"
FEDERATION_LEARNING_RUNTIME_COMPONENT="${WORK_DIR}/runtime-scheduling-component"
IMAGE_VERSION="${IMAGE_VERSION:-0.0.1-SNAPSHOT}"
TRAINING_SCENARIO="${FL_TRAINING_SCENARIO:-csv}"
PLATFORM_API_URL="${FL_PLATFORM_URL:-http://localhost:30080/api/federation-learning-platform}"
SUPPORT_API_URL="${FL_SUPPORT_URL:-http://localhost:30080/api/federation-learning-support}"
RUNTIME_AGENT_API_URL="${FL_RUNTIME_AGENT_URL:-http://localhost:30082/api/runtime-agent}"
DEFAULT_PARTICIPANT_COUNT="${FL_DEV_PARTICIPANT_COUNT:-2}"
INIT_TRAINING_SCRIPT="${PROJECT_ROOT}/scripts/init-training-prerequisites.mjs"
DEFAULT_INTERNAL_TOKEN="${MEDOL_SECURITY_INTERNAL_TOKEN:-local-dev-internal-token}"

export PRE_APPLY_FILE="${PRE_APPLY_FILE:-${K3S_DIR}/components/runtime-scheduling/runtime-scheduler-rbac.yaml}"

image_from_registry_overlay() {
    local image_name="$1"
    local registry_overlay="${REGISTRY_OVERLAY:-${K3S_DIR}/environments/dev-registry}"
    local registry_kustomization="${registry_overlay}/kustomization.yaml"
    if [[ ! -f "${registry_kustomization}" ]]; then
        return 1
    fi
    awk -v image_target="fl/${image_name}" '
        $1 == "-" && $2 == "name:" {
            in_block = ($0 ~ "\"" image_target "\"")
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
        printf 'fl/%s:%s\n' "${image_name}" "${IMAGE_VERSION}"
    fi
}

b64() {
    printf '%s' "$1" | base64 | tr -d '\n'
}

apply_managed_runtime_agent_secret_patch() {
    local runtime_agent_db_username="${RUNTIME_AGENT_DB_USERNAME:-medol}"
    local runtime_agent_db_password="${RUNTIME_AGENT_DB_PASSWORD:-medol}"
    local runtime_agent_jwt_secret="${MEDOL_SECURITY_JWT_SECRET:-medol-medol-medol-medoldol-medol-medol-medol}"
    local runtime_agent_internal_token="${MEDOL_SECURITY_INTERNAL_TOKEN:-local-dev-internal-token}"
    local runtime_agent_admin_bootstrap_setup_token="${MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN:-medol}"
    local patched_secret="${WORK_DIR}/runtime-agent-managed-secret.yaml"

    kubectl -n "${NAMESPACE}" create secret generic federation-learning-runtime-agent-dev-secret \
        --from-literal=DB_PASSWORD="${runtime_agent_db_password}" \
        --from-literal=DB_USERNAME="${runtime_agent_db_username}" \
        --from-literal=MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN="${runtime_agent_admin_bootstrap_setup_token}" \
        --from-literal=MEDOL_SECURITY_INTERNAL_TOKEN="${runtime_agent_internal_token}" \
        --from-literal=MEDOL_SECURITY_JWT_SECRET="${runtime_agent_jwt_secret}" \
        --from-literal=UMADB_API_KEY="" \
        --dry-run=client -o yaml > "${patched_secret}"
    echo "[federation-learning-dev] kubectl -n ${NAMESPACE} apply managed runtime-agent secret patch"
    kubectl -n "${NAMESPACE}" apply -f "${patched_secret}"
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
    local aggregation_runtime_engine_image
    local participant_console_image
    local runtime_agent_db_username
    local runtime_agent_db_password
    local runtime_agent_jwt_secret
    local runtime_agent_internal_token
    local runtime_agent_admin_bootstrap_setup_token
    local runtime_agent_db_username_b64
    local runtime_agent_db_password_b64
    local runtime_agent_jwt_secret_b64
    local runtime_agent_internal_token_b64
    local runtime_agent_admin_bootstrap_setup_token_b64
    runtime_agent_image="$(runtime_image FL_RUNTIME_AGENT_IMAGE federation-learning-runtime-agent)"
    runtime_engine_image="$(runtime_image FL_RUNTIME_ENGINE_IMAGE federation-learning-runtime-engine)"
    aggregation_runtime_engine_image="${FL_AGGREGATION_RUNTIME_ENGINE_IMAGE:-${runtime_engine_image%:*}:pytorch-vision-${IMAGE_VERSION}}"
    participant_console_image="$(runtime_image FL_PARTICIPANT_CONSOLE_IMAGE federation-learning-participant-console)"
    runtime_agent_db_username="${RUNTIME_AGENT_DB_USERNAME:-medol}"
    runtime_agent_db_password="${RUNTIME_AGENT_DB_PASSWORD:-medol}"
    runtime_agent_jwt_secret="${MEDOL_SECURITY_JWT_SECRET:-medol-medol-medol-medoldol-medol-medol-medol}"
    runtime_agent_internal_token="${MEDOL_SECURITY_INTERNAL_TOKEN:-local-dev-internal-token}"
    runtime_agent_admin_bootstrap_setup_token="${MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN:-medol}"
    runtime_agent_db_username_b64="$(b64 "${runtime_agent_db_username}")"
    runtime_agent_db_password_b64="$(b64 "${runtime_agent_db_password}")"
    runtime_agent_jwt_secret_b64="$(b64 "${runtime_agent_jwt_secret}")"
    runtime_agent_internal_token_b64="$(b64 "${runtime_agent_internal_token}")"
    runtime_agent_admin_bootstrap_setup_token_b64="$(b64 "${runtime_agent_admin_bootstrap_setup_token}")"

    rm -rf "${FEDERATION_LEARNING_RUNTIME_COMPONENT}"
    mkdir -p "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/patches"
    cp "${K3S_DIR}/components/runtime-scheduling/runtime-scheduler-rbac.yaml" \
        "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/runtime-scheduler-rbac.yaml"
    cp "${K3S_DIR}/components/runtime-scheduling/platform-aggregation-runtime-engine.yaml" \
        "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/platform-aggregation-runtime-engine.yaml"
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
  - "platform-aggregation-runtime-engine.yaml"
  - "runtime-agent-managed-secret.yaml"
patches:
  - path: "patches/platform-runtime-scheduler.yaml"
  - path: "patches/runtime-agent-engine-scheduler.yaml"
  - path: "patches/runtime-scheduler-config.yaml"
  - path: "runtime-agent-disabled.yaml"
  - path: "runtime-scheduler-images.yaml"
  - path: "aggregation-runtime-engine-image.yaml"
YAML
    cat > "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/aggregation-runtime-engine-image.yaml" <<YAML
apiVersion: "apps/v1"
kind: "Deployment"
metadata:
  name: "federation-learning-aggregation-runtime-engine"
spec:
  template:
    spec:
      containers:
        - name: "runtime-engine"
          image: "${aggregation_runtime_engine_image}"
YAML
    cat > "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/runtime-agent-disabled.yaml" <<YAML
apiVersion: "apps/v1"
kind: "Deployment"
metadata:
  name: "federation-learning-runtime-agent"
spec:
  replicas: 0
YAML
    cat > "${FEDERATION_LEARNING_RUNTIME_COMPONENT}/runtime-agent-managed-secret.yaml" <<YAML
apiVersion: "v1"
kind: "Secret"
metadata:
  name: "federation-learning-runtime-agent-dev-secret"
type: "Opaque"
data:
  DB_PASSWORD: "${runtime_agent_db_password_b64}"
  DB_USERNAME: "${runtime_agent_db_username_b64}"
  MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN: "${runtime_agent_admin_bootstrap_setup_token_b64}"
  MEDOL_SECURITY_INTERNAL_TOKEN: "${runtime_agent_internal_token_b64}"
  MEDOL_SECURITY_JWT_SECRET: "${runtime_agent_jwt_secret_b64}"
  UMADB_API_KEY: ""
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
  MEDOL_SYNC_SOURCE_BASE_URLS_DICTIONARYMAINTENANCE: "http://federation-learning-support:8080"
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
  - port: "30082-30181:30082-30181"
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

init_training_args() {
    printf '%s\n' \
        "--scenario" "${TRAINING_SCENARIO}" \
        "--platform-url" "${PLATFORM_API_URL}" \
        "--support-url" "${SUPPORT_API_URL}" \
        "--runtime-agent-url" "${RUNTIME_AGENT_API_URL}" \
        "--runtime-environment-type" "K3S" \
        "--agent-install-mode" "PLATFORM_MANAGED" \
        "--endpoint-scope" "CLUSTER"
}

participant_key_for_slot() {
    local slot="${1:-1}"
    local env_name="FL_PARTICIPANT_${slot}_KEY"
    local configured="${!env_name:-}"
    if [[ -n "${configured}" ]]; then
        printf '%s\n' "${configured}"
    else
        printf 'participant-%s\n' "${slot}"
    fi
}

seed_json_for_slot() {
    local slot="${1:-1}"
    local participant_key
    participant_key="$(participant_key_for_slot "${slot}")"
    (
        cd "${PROJECT_ROOT}"
        node "${INIT_TRAINING_SCRIPT}" \
            --print-seed-json-only \
            --participant-key "${participant_key}" \
            $(init_training_args)
    )
}

seed_field_for_slot() {
    local slot="$1"
    local field="$2"
    local json
    json="$(seed_json_for_slot "${slot}")"
    node -e 'const input = process.argv[1]; const field = process.argv[2]; const value = JSON.parse(input)[field]; if (value == null) process.exit(1); console.log(value);' \
        "${json}" "${field}"
}

runtime_agent_url_for_slot() {
    local slot="${1:-1}"
    local env_name="FL_RUNTIME_AGENT_URL_${slot}"
    local configured="${!env_name:-}"
    if [[ -n "${configured}" ]]; then
        printf '%s\n' "${configured}"
        return
    fi
    if [[ "${slot}" == "1" ]]; then
        printf '%s\n' "${RUNTIME_AGENT_API_URL}"
        return
    fi

    local runtime_agent_id
    local node_port
    runtime_agent_id="$(seed_field_for_slot "${slot}" runtimeAgentId)"
    node_port="$(
        kubectl -n "${NAMESPACE}" get svc \
            -l "medol.dev/runtime-agent-id=${runtime_agent_id}" \
            -o jsonpath='{range .items[*]}{range .spec.ports[*]}{.nodePort}{"\n"}{end}{end}' 2>/dev/null |
            awk 'NF {print $1; exit}'
    )"
    if [[ -z "${node_port}" ]]; then
        cat >&2 <<MSG
[federation-learning-dev] Unable to discover runtime-agent NodePort for participant slot ${slot}.
Register the runtime infrastructure first, then wait for the managed runtime-agent:
  ${0} register-runtime-${slot}
  ${0} wait-runtime-agent
Or set FL_RUNTIME_AGENT_URL_${slot}=http://localhost:<node-port>/api/runtime-agent.
MSG
        exit 1
    fi
    printf 'http://localhost:%s/api/runtime-agent\n' "${node_port}"
}

runtime_agent_url_for_slot_or_default() {
    local slot="${1:-1}"
    local env_name="FL_RUNTIME_AGENT_URL_${slot}"
    local configured="${!env_name:-}"
    if [[ -n "${configured}" ]]; then
        printf '%s\n' "${configured}"
    elif [[ "${slot}" == "1" ]]; then
        printf '%s\n' "${RUNTIME_AGENT_API_URL}"
    else
        printf '%s\n' "${RUNTIME_AGENT_API_URL}"
    fi
}

args_contain() {
    local expected="$1"
    shift || true
    local value
    for value in "$@"; do
        [[ "${value}" == "${expected}" ]] && return 0
    done
    return 1
}

run_init_training() {
    local target="$1"
    shift || true
    if [[ ! -f "${INIT_TRAINING_SCRIPT}" ]]; then
        echo "[federation-learning-dev] missing init script: ${INIT_TRAINING_SCRIPT}" >&2
        exit 1
    fi
    echo "[federation-learning-dev] node ${INIT_TRAINING_SCRIPT} --target ${target}"
    (
        cd "${PROJECT_ROOT}"
        export MEDOL_SECURITY_INTERNAL_TOKEN="${MEDOL_SECURITY_INTERNAL_TOKEN:-${DEFAULT_INTERNAL_TOKEN}}"
        if [[ -n "${MEDOL_SECURITY_INTERNAL_TOKEN}" ]]; then
            export FL_API_TOKEN=""
        fi
        node "${INIT_TRAINING_SCRIPT}" \
            --target "${target}" \
            $(init_training_args) \
            "$@"
    )
}

wait_core_apps() {
    kubectl -n "${NAMESPACE}" rollout status deploy/postgres --timeout="${KUBECTL_ROLLOUT_TIMEOUT:-180s}" || true
    kubectl -n "${NAMESPACE}" rollout status deploy/umadb --timeout="${KUBECTL_ROLLOUT_TIMEOUT:-180s}" || true
    kubectl -n "${NAMESPACE}" rollout status deploy/federation-learning-support --timeout="${KUBECTL_ROLLOUT_TIMEOUT:-240s}"
    kubectl -n "${NAMESPACE}" rollout status deploy/federation-learning-platform --timeout="${KUBECTL_ROLLOUT_TIMEOUT:-240s}"
    kubectl -n "${NAMESPACE}" rollout status deploy/apisix --timeout="${KUBECTL_ROLLOUT_TIMEOUT:-180s}"
}

wait_managed_runtime_agent() {
    local deployments
    deployments="$(kubectl -n "${NAMESPACE}" get deploy -o name | grep 'deployment.apps/federation-learning-runtime-agent-managed-' || true)"
    if [[ -z "${deployments}" ]]; then
        echo "[federation-learning-dev] no managed runtime-agent deployment found yet"
        return 1
    fi
    while IFS= read -r deployment; do
        [[ -z "${deployment}" ]] && continue
        kubectl -n "${NAMESPACE}" rollout status "${deployment}" --timeout="${KUBECTL_ROLLOUT_TIMEOUT:-240s}"
    done <<< "${deployments}"
}

label_runtime_node() {
    local slot="${1:-1}"
    local node_name="${RUNTIME_NODE_NAME:-${2:-}}"
    local organization_id="${ORGANIZATION_ID:-${FL_ORGANIZATION_ID:-}}"
    local runtime_infrastructure_id="${RUNTIME_INFRASTRUCTURE_ID:-${FL_RUNTIME_INFRASTRUCTURE_ID:-}}"
    if [[ -z "${organization_id}" ]]; then
        organization_id="$(seed_field_for_slot "${slot}" organizationId)"
    fi
    if [[ -z "${runtime_infrastructure_id}" ]]; then
        runtime_infrastructure_id="$(seed_field_for_slot "${slot}" runtimeInfrastructureId)"
    fi
    if [[ -z "${node_name}" ]]; then
        node_name="$(kubectl get nodes --no-headers | awk -v slot="${slot}" '$1 ~ /agent/ {count += 1; if (count == slot) {print $1; exit}}')"
    fi
    if [[ -z "${node_name}" ]]; then
        echo "[federation-learning-dev] runtime node name is required. Set RUNTIME_NODE_NAME or pass it as the first argument." >&2
        exit 1
    fi
    if [[ -z "${organization_id}" || -z "${runtime_infrastructure_id}" ]]; then
        cat >&2 <<MSG
[federation-learning-dev] ORGANIZATION_ID/FL_ORGANIZATION_ID and RUNTIME_INFRASTRUCTURE_ID/FL_RUNTIME_INFRASTRUCTURE_ID are required.
Run:
  ${0} seed-platform
Then copy organizationId and runtimeInfrastructureId from the printed JSON, or set them in the environment.
MSG
        exit 1
    fi
    echo "[federation-learning-dev] label runtime node ${node_name}"
    kubectl label node "${node_name}" \
        medol.dev/node-role=runtime \
        "medol.dev/organization-id=${organization_id}" \
        "medol.dev/runtime-infrastructure-id=${runtime_infrastructure_id}" \
        --overwrite
    kubectl taint node "${node_name}" \
        medol.dev/runtime-only=true:NoSchedule \
        --overwrite
}

run_init_training_for_slot() {
    local slot="$1"
    local target="$2"
    shift 2 || true
    local participant_key
    local runtime_agent_url
    participant_key="$(participant_key_for_slot "${slot}")"
    if [[ "${target}" == "runtime-agent" ]] && ! args_contain "--dry-run" "$@"; then
        runtime_agent_url="$(runtime_agent_url_for_slot "${slot}")"
    else
        runtime_agent_url="$(runtime_agent_url_for_slot_or_default "${slot}")"
    fi
    run_init_training "${target}" \
        --participant-key "${participant_key}" \
        --runtime-agent-url "${runtime_agent_url}" \
        "$@"
}

register_runtime_for_slot() {
    local slot="$1"
    shift || true
    local participant_key
    participant_key="$(participant_key_for_slot "${slot}")"
    run_init_training platform \
        --participant-key "${participant_key}" \
        --register-runtime-infrastructure \
        "$@"
}

runtime_agent_url_status() {
    local count="${1:-${DEFAULT_PARTICIPANT_COUNT}}"
    local slot
    for ((slot = 1; slot <= count; slot += 1)); do
        local participant_key
        local runtime_agent_id
        participant_key="$(participant_key_for_slot "${slot}")"
        runtime_agent_id="$(seed_field_for_slot "${slot}" runtimeAgentId)"
        local node_port
        node_port="$(
            kubectl -n "${NAMESPACE}" get svc \
                -l "medol.dev/runtime-agent-id=${runtime_agent_id}" \
                -o jsonpath='{range .items[*]}{range .spec.ports[*]}{.nodePort}{"\n"}{end}{end}' 2>/dev/null |
                awk 'NF {print $1; exit}'
        )"
        if [[ -n "${node_port}" ]]; then
            echo "participant ${slot} (${participant_key}) runtime-agent API: http://localhost:${node_port}/api/runtime-agent"
        else
            echo "participant ${slot} (${participant_key}) runtime-agent API: not deployed yet"
        fi
    done
}

seed_status() {
    cat <<STATUS
[federation-learning-dev] current flow settings
  namespace: ${NAMESPACE}
  scenario: ${TRAINING_SCENARIO}
  platform API: ${PLATFORM_API_URL}
  support API: ${SUPPORT_API_URL}
  runtime-agent API: ${RUNTIME_AGENT_API_URL}
  dataset host root: ${DATASETS_HOST_ROOT}
  runtime-engine host root: ${RUNTIME_ENGINE_HOST_ROOT}
  default participant count: ${DEFAULT_PARTICIPANT_COUNT}

Suggested k3d flow:
  1. ${0} recreate
  2. eval "\$(${0} kubeconfig)"
  3. ${0} apply
  4. ${0} wait-core
  5. ${0} seed-platform-all
  6. ${0} label-runtime-node-all
  7. ${0} register-runtime-all
  8. ${0} wait-runtime-agent
  9. ${0} runtime-agent-urls
 10. ${0} seed-runtime-agent-all
 11. ${0} seed-training-job
STATUS
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

case "${COMMAND}" in
    seed-status)
        seed_status
        exit
        ;;
    wait-core)
        wait_core_apps
        exit
        ;;
    wait-runtime-agent)
        wait_managed_runtime_agent
        exit
        ;;
    runtime-agent-urls)
        shift || true
        runtime_agent_url_status "${1:-${DEFAULT_PARTICIPANT_COUNT}}"
        exit
        ;;
    seed-platform)
        shift || true
        run_init_training_for_slot 1 platform "$@"
        exit
        ;;
    seed-platform-2)
        shift || true
        run_init_training_for_slot 2 platform "$@"
        exit
        ;;
    seed-platform-all)
        shift || true
        for ((slot = 1; slot <= DEFAULT_PARTICIPANT_COUNT; slot += 1)); do
            run_init_training_for_slot "${slot}" platform "$@"
        done
        exit
        ;;
    label-runtime-node)
        shift || true
        label_runtime_node 1 "$@"
        exit
        ;;
    label-runtime-node-2)
        shift || true
        label_runtime_node 2 "$@"
        exit
        ;;
    label-runtime-node-all)
        shift || true
        for ((slot = 1; slot <= DEFAULT_PARTICIPANT_COUNT; slot += 1)); do
            label_runtime_node "${slot}" "$@"
        done
        exit
        ;;
    register-runtime)
        shift || true
        register_runtime_for_slot 1 "$@"
        exit
        ;;
    register-runtime-2)
        shift || true
        register_runtime_for_slot 2 "$@"
        exit
        ;;
    register-runtime-all)
        shift || true
        for ((slot = 1; slot <= DEFAULT_PARTICIPANT_COUNT; slot += 1)); do
            register_runtime_for_slot "${slot}" "$@"
        done
        exit
        ;;
    seed-runtime-agent)
        shift || true
        run_init_training_for_slot 1 runtime-agent "$@"
        exit
        ;;
    seed-runtime-agent-2)
        shift || true
        run_init_training_for_slot 2 runtime-agent "$@"
        exit
        ;;
    seed-runtime-agent-all)
        shift || true
        for ((slot = 1; slot <= DEFAULT_PARTICIPANT_COUNT; slot += 1)); do
            run_init_training_for_slot "${slot}" runtime-agent "$@"
        done
        exit
        ;;
    seed-training-job)
        shift || true
        run_init_training training-job "$@"
        exit
        ;;
esac

if [[ "${COMMAND}" == "apply" ]]; then
    "${SCRIPT_DIR}/k3d-dev.sh" "$@"
    apply_managed_runtime_agent_secret_patch
    exit
fi

exec "${SCRIPT_DIR}/k3d-dev.sh" "$@"
