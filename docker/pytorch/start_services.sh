#!/bin/bash
set -e

exec runtime-engine-node \
  --host "${RUNTIME_ENGINE_HOST:-${GEMIFL_RUNTIME_HOST:-0.0.0.0}}" \
  --port "${RUNTIME_ENGINE_PORT:-${GEMIFL_RUNTIME_PORT:-8080}}" \
  --node-name "${RUNTIME_ENGINE_NODE_NAME:-${GEMIFL_NODE_NAME:-runtime-node}}" \
  --runtime-root "${RUNTIME_ENGINE_ROOT:-${GEMIFL_RUNTIME_ROOT:-/workspace/tmp/runtime-engine}}"
