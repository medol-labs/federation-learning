# Single-machine K3d training pipeline

## Execution location

From the project root, use the same launcher for both execution locations:

```bash
node simulation/src/run.mjs --mode local --plan
node simulation/src/run.mjs --mode local
node simulation/src/run.mjs --mode remote --plan
node simulation/src/run.mjs --mode remote
```

Local mode reads `pipelines/pipeline.json`; remote mode reads `pipelines/remote.json`.
Use `--pipeline <path>` for an alternative config. Local mode rejects declared SSH
steps; remote mode requires one. Configured commands remain trusted code, not a
security sandbox. Both modes share the same training scenario and adapter.
`--mode` is execution location, not the Operations environment (`local`, `test`, etc.).
In remote mode build/publish steps without a host still run locally. Data paths,
kubeconfig and localhost endpoints belong to the machine executing the stage.
Local mode reuses your existing local environment and does not automatically
overwrite `.env` or secrets. Remote auto-setup is an explicit remote pipeline step.

This hand-maintained pipeline uses Simulation-owned stages for a CSV,
global-federation smoke test with two independent participants. Regeneration only
writes `pipeline.json-example`, not `pipeline.json` or this file.

Run from the Federation Learning project root:

```bash
node simulation/src/pipeline.mjs plan simulation/pipelines/pipeline.json
node simulation/src/pipeline.mjs run simulation/pipelines/pipeline.json
```

`plan` validates the pipeline structure only and has no deployment side effects.
`run` first invokes the scenario configuration check, then executes Simulation stages:
end-to-end flow: cluster creation/reuse, image preparation, deployment readiness,
authentication, dictionaries, participant registration/deployment, dataset setup,
training submission and completion checks. Stage configuration lives in
`simulation/extensions/scenarios/two-participant-training.json`; the executor
records each stage and participant separately while the adapter retains login
tokens in memory. No invocation of the old k3d training flow is involved.
The adapter currently consolidates migrated helpers and still uses existing
initialization utilities; further decomposition and strict contribution assertions
remain necessary. Scenario reports are stored alongside pipeline reports.

## Prerequisites and configuration

- Node.js 20+, Docker, k3d and kubectl must be available.
- Generate Operations for `local` first. Configure image acquisition and registry
  settings there. This pipeline does not build or publish images; required images
  must already exist locally or in the configured registry, including the compatible
  runtime engine and aggregation engine.
- Prepare the CSV dataset and host mounts required by the existing initialization
  scripts. This pipeline does not download or fabricate training files.
- The flow reads the project `.env`, backend `.env` and inherited environment.
  Use `FL_API_TOKEN` or the existing test administrator bootstrap/login settings;
  configure service-to-service credentials consistently. Never commit credentials.
- Set `FL_TRAINING_RUN_KEY` to a new value when a fresh training configuration/job
  is required. Without it, the initializer may reuse prior records.
- Cluster recreation and image rebuilding are explicitly disabled. Existing data
  is preserved; the default kubeconfig is not modified. The cluster and resources
  remain available after completion or failure.
- Each flow wait allows 10 minutes; the whole flow step allows two hours.
  Reports are under `simulation/reports/<run-id>/report.json`; child logs appear
  in the terminal/CI output rather than being embedded in the JSON report.

## Current verification boundary

The scenario passes participant count as `minimumNodesPerRound` and generates a
fresh training run key unless explicitly overridden. Completion requires the exact
expected distinct runtime selection, quorum matching participant count, sufficient
accepted updates, an aggregated model ID and a completed job. Each participant must
also expose a submitted execution with an update artifact ID for that round.
The verifier downloads the catalog's final model artifact and checks its SHA-256.
Only HTTP(S) URLs within the configured platform/support endpoints are accepted;
internal cluster URLs or local filesystem URIs fail explicitly, rather than leaking
credentials to arbitrary endpoints. Configure publicly reachable artifact URLs.
Agent submission records plus platform counters are checked; the system does not
currently expose a platform per-runtime acceptance catalog to independently prove
each individual acceptance.
No actual cluster execution has been performed as part of adding this config.
