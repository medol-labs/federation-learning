# Twenty-Participant Acceptance Run

## Scope

Twenty independently deployed Runtime Agents participate in one training job for
two federated rounds. Each round must include all twenty distinct runtime IDs,
twenty distinct accepted model-update artifacts and a downloadable aggregate with
a verified SHA-256. Every second-round execution must use the first aggregate as
its base model. Merely creating twenty pods does not pass this test.

This configuration uses one ARM64 physical host with twenty K3d workers, one server,
and twenty isolated participant namespaces/databases. It is a functional capacity
test, not proof of twenty physical machines or a throughput benchmark. The CSV
fixture is synthetic (32 records); the same fixture is copied to each participant.
The model is `linear.LogisticRegression`. The CSV configuration enables the
secure-aggregation workflow, while the development engine uses `FED_AVG_JSON`.
No clinical accuracy or cryptographic secure-aggregation claim is made.

## Environment And Deployment

Run on the configured SSH worker from `/home/fl-simulation/test`:

```sh
node simulation/src/prepare-environment.mjs simulation/environments/fl-test-twenty.json
node simulation/src/instance-registry.mjs register-environment simulation/environments/fl-test-twenty.json simulation/reports/environment-fl-test-twenty.json
node simulation/src/instance-registry.mjs resolve-deployment simulation/deployments/fl-test-twenty.json simulation/reports/twenty-deployment-resolved.json
export MEDOL_PIPELINE_RUN_ID=twenty-deploy-20261117
node simulation/src/task.mjs simulation/pipelines/twenty-tasks.json secrets
MEDOL_DEPLOYMENT_CONFIG=simulation/reports/twenty-deployment-resolved.json node simulation/src/task.mjs simulation/pipelines/twenty-tasks.json deploy
node simulation/src/instance-registry.mjs register-deployment simulation/deployments/fl-test-twenty.json simulation/reports/workflows/twenty-deploy-20261117/deployment.json
```

Use fresh registry IDs/output paths for a replacement deployment. The existing
two-participant cluster is stopped and retained, not deleted. Do not start both
clusters simultaneously: they use the same public ports. The native K3s service
is separate and must not be selected through the default kubeconfig.

The 2026-11-17 deployment reuses previously published ARM64 images and records
resolved digests. It does not claim a new build/publish run. The host's inotify
instance limit was raised temporarily from 128 to 8192 after initial cluster
creation failed; this setting is not persisted by these scripts.

Participant engine cold starts use `RUNTIME_AGENT_LOCAL_RUNTIME_ENGINE_HEALTH_TIMEOUT=PT5M`
in `operations/extensions/participant-package.json`. Thirty seconds was insufficient
when twenty fresh workers pulled/started engine images concurrently. This changes
readiness waiting, not the criteria for successful training. The first failed job
and its API snapshots are retained as `cold-start.failure.json`.

## Cases, Data And Execution

```sh
node simulation/src/instance-registry.mjs create-run twenty-training-20261117 fl-test-twenty-20261117
export MEDOL_SIMULATION_RUN_ID=twenty-training-20261117
export MEDOL_PIPELINE_RUN_ID=twenty-training-20261117
node simulation/src/task.mjs simulation/pipelines/twenty-tasks.json select-cases
node simulation/src/task.mjs simulation/pipelines/twenty-tasks.json prepare-baseline
node simulation/src/task.mjs simulation/pipelines/twenty-tasks.json prepare-case
node simulation/src/task.mjs simulation/pipelines/twenty-tasks.json prepare-participant-auth
node simulation/src/task.mjs simulation/pipelines/twenty-tasks.json test
node simulation/extensions/scenarios/collect-training-evidence.mjs twenty-training-20261117
```

Platform baseline authentication/dictionaries precede registration. Participant
authentication is initialized **after** registration: an unconfigured agent
deliberately returns 503 for login. Participant consoles authenticate against
their own agent IAM, not Support. Passwords come from private worker `.env` and
are not included in evidence.

Manual cases and their approved snapshot are separate files under
`simulation/cases/manual/` and `simulation/cases/approved/`. Review changes before
re-approval; never edit a fingerprint to force acceptance. `--max-rounds` controls
the seed configuration and verification's expected round count together.

## Evidence And Access

Evidence is under `simulation/reports/workflows/<runId>/evidence/`:
HTML report, JSON assertions and API snapshots, participant-by-round CSV matrix,
deployment/image identities, node/pod snapshots, both aggregate model files and
SHA-256 manifest. Collect screenshots separately from the real application; label
report renderings as reports, not application screenshots. A failed run must not
be presented as successful acceptance.

- Platform: `http://192.168.50.2:31080`
- Participants 1-20: ports `31081` through `31100`
- Kubeconfig: `operations/k3s/.work/kubeconfig-federation-learning-twenty.yaml`

Keep failed attempts and diagnostics when retrying. Test outputs are create-only;
completed outputs must not be overwritten to conceal failures.

## Multi-Round Corrections

The first twenty-party round completed after increasing the engine startup timeout.
Its second round failed because `TrainingRoundCompleted` omitted `federationId`.
The model now preserves that field from round state, and the regenerated next-round
automation copies it. `AggregatePlainModelUpdatesDecisionComponent` also implements
the modeled rule that an aggregate retains the configured model plugin; an empty
catalog plugin cannot identify the model in the next round. The same rule is
implemented by `CompleteHomomorphicAggregationSessionDecisionComponent` for the
secure-aggregation workflow; fixing only the plain branch does not cover this CSV
scenario. Both branches have regression tests.

The corrected deployment uses `simulation/deployments/fl-test-twenty-v3.json`,
the platform image `simulation-20261117-two-rounds-v2`, and the agent image
`simulation-20261117-artifact-url`. Register it as a separate
deployment record after applying the resolved configuration. Create a fresh test
run/configuration because submitting a job locks its run configuration. Prior
failures and screenshots are evidence, not acceptance of two-round execution.

The second-round model is downloaded from its original catalog URI. The agent
must not implicitly set `RUNTIME_ENGINE_FILE_ACCESS_BASE_URL` to its direct
Support endpoint: a public URI containing a gateway prefix cannot be rewritten
by replacing only its origin. The observed rewritten URL returned 404 while the
original authenticated URI returned 200. Engine authentication is still passed.
