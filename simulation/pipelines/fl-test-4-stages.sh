#!/bin/sh
set -eu
# Run on the offline worker after importing the tool and all dependency images.
base=${FL_DELIVERY_ROOT:-/home/h3c/fl/k3d}
tool() { sh "$base/run-simulation.sh" "$@"; }
tasks=simulation/pipelines/twenty-tasks.json
deployment=fl-test-4-twenty-20261118
export MEDOL_PIPELINE_RUN_ID=${FL_DEPLOYMENT_RUN_ID:-deploy-fl-test-4-20261118}
export MEDOL_DEPLOYMENT_CONFIG=simulation/reports/fl-test-4-resolved.json
case "${1:-}" in
  environment)
    tool environment simulation/environments/fl-test-4-twenty.json
    tool registry register-environment simulation/environments/fl-test-4-twenty.json simulation/reports/environment-fl-test-4-twenty.json
    ;;
  resolve)
    tool registry resolve-deployment simulation/deployments/fl-test-4-twenty.json "$MEDOL_DEPLOYMENT_CONFIG"
    ;;
  secrets|deploy) tool task "$tasks" "$1" ;;
  register)
    tool registry register-deployment simulation/deployments/fl-test-4-twenty.json "simulation/reports/workflows/$MEDOL_PIPELINE_RUN_ID/deployment.json"
    ;;
  create-run)
    tool registry create-run "${FL_TEST_RUN_ID:-training-fl-test-4-20261118}" "$deployment"
    ;;
  select-cases|prepare-baseline|enroll-participants|prepare-participant-auth|prepare-case|test)
    export MEDOL_SIMULATION_RUN_ID=${FL_TEST_RUN_ID:-training-fl-test-4-20261118}
    export MEDOL_PIPELINE_RUN_ID=$MEDOL_SIMULATION_RUN_ID
    tool task "$tasks" "$1"
    ;;
  evidence)
    tool exec node simulation/extensions/scenarios/collect-training-evidence.mjs "${FL_TEST_RUN_ID:-training-fl-test-4-20261118}"
    ;;
  *) echo 'Stages: environment resolve secrets deploy register create-run select-cases prepare-baseline enroll-participants prepare-participant-auth prepare-case test evidence' >&2; exit 1 ;;
esac
