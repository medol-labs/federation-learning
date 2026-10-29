#!/usr/bin/env bash
set -euo pipefail
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT="$(cd "$HERE/../.." && pwd)"
[[ -f "$HERE/.env" ]] || { echo 'Create operations/local/.env from .env-example first.' >&2; exit 1; }
set -a
source "$HERE/.env"
set +a
export SPRING_DOCKER_COMPOSE_ENABLED=false
export DB_USERNAME="${POSTGRES_USER:-medol}"
export DB_PASSWORD="${POSTGRES_PASSWORD:?Set POSTGRES_PASSWORD}"
export MEDOL_AXON_EVENT_STORAGE=umadb
export UMADB_TARGET="localhost:${UMADB_PORT:-50051}"
export UMADB_PLAINTEXT=true
export FEDERATION_LEARNING_SUPPORT_URL=http://localhost:8080
export FEDERATION_LEARNING_PLATFORM_URL=http://localhost:8081
export FEDERATION_LEARNING_RUNTIME_AGENT_URL=http://localhost:8082
export MEDOL_SECURITY_INTERNAL_TOKEN="${MEDOL_SECURITY_INTERNAL_TOKEN:-local-dev-internal-token}"
export MEDOL_SECURITY_ADMIN_BOOTSTRAP_ENABLED="${MEDOL_SECURITY_ADMIN_BOOTSTRAP_ENABLED:-true}"
export MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN="${MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN:-local-admin-setup}"
export VITE_AUTH_PROVIDER=local

case "${1:-help}" in
  infra)
    cd "$HERE"
    exec docker compose --env-file .env up -d
    ;;
  build)
    cd "$ROOT/federation-learning-platform"
    exec ./mvnw -DskipTests install
    ;;
  support|platform|runtime-agent)
    module="federation-learning-$1"
    case "$1" in
      support) export SERVER_PORT=8080; database=federation_learning_support ;;
      platform) export SERVER_PORT=8081; database=federation_learning_platform ;;
      runtime-agent) export SERVER_PORT=8082; database=federation_learning_runtime_agent ;;
    esac
    if [[ "$1" == runtime-agent ]]; then
      export MEDOL_SYNC_SOURCE_BASE_URL="${MEDOL_SYNC_SOURCE_BASE_URL:-$FEDERATION_LEARNING_PLATFORM_URL}"
      export MEDOL_SYNC_SOURCE_BASE_URLS_DATASETGOVERNANCE="${MEDOL_SYNC_SOURCE_BASE_URLS_DATASETGOVERNANCE:-$FEDERATION_LEARNING_PLATFORM_URL}"
      export MEDOL_SYNC_SOURCE_BASE_URLS_ORGANIZATIONMANAGEMENT="${MEDOL_SYNC_SOURCE_BASE_URLS_ORGANIZATIONMANAGEMENT:-$FEDERATION_LEARNING_PLATFORM_URL}"
      export MEDOL_SYNC_SOURCE_BASE_URLS_RUNTIMEGOVERNANCE="${MEDOL_SYNC_SOURCE_BASE_URLS_RUNTIMEGOVERNANCE:-$FEDERATION_LEARNING_PLATFORM_URL}"
      export MEDOL_SYNC_SOURCE_BASE_URLS_DICTIONARYMAINTENANCE="${MEDOL_SYNC_SOURCE_BASE_URLS_DICTIONARYMAINTENANCE:-$FEDERATION_LEARNING_SUPPORT_URL}"
    fi
    export DB_URL="jdbc:postgresql://localhost:${POSTGRES_PORT:-5432}/$database"
    cd "$ROOT/federation-learning-platform"
    if [[ -f "$HERE/$1.local.env" ]]; then
      set -a; source "$HERE/$1.local.env"; set +a
    fi
    if [[ "$1" == runtime-agent && ( -z "${RUNTIME_AGENT_ID:-}" || -z "${RUNTIME_AGENT_ORGANIZATION_ID:-}" ) ]]; then
      echo 'Warning: set RUNTIME_AGENT_ID and RUNTIME_AGENT_ORGANIZATION_ID in .env or runtime-agent.local.env for scoped read-model synchronization.' >&2
    fi
    exec ./mvnw -pl "$module" spring-boot:run
    ;;
  console|participant-console)
    if [[ "$1" == console ]]; then
      directory=federation-learning-console; port=5173
      export VITE_FRONTEND_APP=FederationLearningConsole
      export VITE_AUTH_API_URL=http://localhost:8080
      export VITE_AXON_API_URL=http://localhost:8081
      export VITE_FEDERATION_LEARNING_PLATFORM_API_URL=http://localhost:8081
      export VITE_FEDERATION_LEARNING_SUPPORT_API_URL=http://localhost:8080
    else
      directory=federation-learning-participant-console; port=5174
      export VITE_FRONTEND_APP=FederationLearningParticipantConsole
      export VITE_AUTH_API_URL=http://localhost:8082
      export VITE_AXON_API_URL=http://localhost:8082
      export VITE_FEDERATION_LEARNING_RUNTIME_AGENT_API_URL=http://localhost:8082
    fi
    cd "$ROOT/$directory"
    exec pnpm exec vite --port "$port" --strictPort
    ;;
  *)
    echo 'Usage: bash operations/local/start.sh {infra|build|support|platform|runtime-agent|console|participant-console}'
    ;;
esac
