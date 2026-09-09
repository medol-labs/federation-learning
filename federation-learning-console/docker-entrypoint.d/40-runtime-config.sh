#!/bin/sh
set -eu

js_escape() {
  printf '%s' "$1" | sed 's/\\/\\\\/g; s/"/\\"/g'
}

auth_api_url="${VITE_AUTH_API_URL:-}"
if [ -z "$auth_api_url" ]; then
  auth_api_url="${VITE_FEDERATION_LEARNING_SUPPORT_API_URL:-${VITE_AXON_API_URL:-}}"
fi

cat >/usr/share/nginx/html/runtime-config.js <<EOF
window.__APP_CONFIG__ = {
  VITE_API_URL: "$(js_escape "${VITE_API_URL:-}")",
  VITE_SUPABASE_API_KEY: "$(js_escape "${VITE_SUPABASE_API_KEY:-}")",
  VITE_AUTH_PROVIDER: "$(js_escape "${VITE_AUTH_PROVIDER:-}")",
  VITE_ACCESS_CONTROL_MODE: "$(js_escape "${VITE_ACCESS_CONTROL_MODE:-}")",
  VITE_AUTH_API_URL: "$(js_escape "$auth_api_url")",
  VITE_AXON_API_URL: "$(js_escape "${VITE_AXON_API_URL:-}")",
  VITE_FEDERATION_LEARNING_SUPPORT_API_URL: "$(js_escape "${VITE_FEDERATION_LEARNING_SUPPORT_API_URL:-}")",
  VITE_FEDERATION_LEARNING_PLATFORM_API_URL: "$(js_escape "${VITE_FEDERATION_LEARNING_PLATFORM_API_URL:-}")",
  VITE_FEDERATION_LEARNING_RUNTIME_AGENT_API_URL: "$(js_escape "${VITE_FEDERATION_LEARNING_RUNTIME_AGENT_API_URL:-}")"};
EOF
