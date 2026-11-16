# Local Compose Training

Run from the repository root. This example uses a native AMD64 Docker daemon,
two independent participants and prestarted sklearn engines. It does not invoke
the K3d training-flow script. Business enrollment remains in the project extension;
Compose lifecycle and instance/run binding use the generic Simulation runtime.

## Prepare And Deploy

Review `environments/compose-local.json`, `deployments/compose-two.json`,
`pipelines/compose-deployment.json` and
`operations/extensions/compose-training/docker-compose.yml` before running.
Use a unique deployment ID, Compose project and host ports for each instance.
The example image tags must exist locally with the configured architecture.
`PUBLIC_SUPPORT_URL` in the private environment must be reachable both from the
host and containers: model artifact URLs use this origin.

```bash
node operations/images/images.mjs build --environment local --platform linux/amd64 --version compose-20261116 --prefix fl-local --skip-console
# From federation-learning-runtime-engine/:
node scripts/build-images.mjs --platform linux/amd64 --version compose-20261116 --plugin-profile sklearn --prefix fl-local
# From federation-learning-platform/, build the corrected platform revision:
node scripts/image-bundle.mjs build --module federation-learning-platform --platform linux/amd64 --version compose-20261116-fixed --prefix fl-local
```

Return to the repository root. Dependency image references in the Compose file
must also exist locally. Deployment uses `--pull never`, not implicit downloads.

```bash
export MEDOL_PIPELINE_RUN_ID=compose-provision-20261116
node simulation/src/task.mjs simulation/pipelines/compose-tasks.json secrets
node simulation/src/prepare-environment.mjs simulation/environments/compose-local.json
node simulation/src/instance-registry.mjs register-environment simulation/environments/compose-local.json simulation/reports/environment-compose-local.json
node simulation/src/instance-registry.mjs resolve-deployment simulation/deployments/compose-two.json simulation/reports/compose-resolved.json
MEDOL_DEPLOYMENT_CONFIG=simulation/reports/compose-resolved.json node simulation/src/task.mjs simulation/pipelines/compose-tasks.json deploy
node simulation/src/instance-registry.mjs register-deployment simulation/deployments/compose-two.json simulation/reports/workflows/compose-provision-20261116/deployment.json
```

Registration and result files are create-only. Do not rerun registration for an
existing instance; select it for a new test run instead. Credentials and enrollment
overrides live in ignored `simulation/compose/` files. Do not commit them.

## Execute Selected Cases

```bash
node simulation/src/instance-registry.mjs create-run compose-training-20261116 compose-two-20261116
export MEDOL_SIMULATION_RUN_ID=compose-training-20261116
node simulation/src/task.mjs simulation/pipelines/compose-tasks.json select-cases
node simulation/src/task.mjs simulation/pipelines/compose-tasks.json prepare-baseline
node simulation/src/task.mjs simulation/pipelines/compose-tasks.json prepare-case
node simulation/src/task.mjs simulation/pipelines/compose-tasks.json test
```

The baseline initializes authentication and dictionaries. Case preparation creates
organizations, a common federation/schema/configuration, enrolls the already
deployed agents and declares their CSV datasets. `extensions/fixtures/test.csv`
is mounted read-only into each participant and engine at `/workspace/datasets`.
Each participant has a separate database, event store and engine work volume.

The approved `participants-complete-training` case checks two distinct runtime
contributions, completed training, aggregation and the downloaded model artifact
digest. Results and instance binding evidence are under
`simulation/reports/workflows/<run-id>/`. Failed runs are not successful evidence.
Use a new run ID to rerun a completed task; baseline/data/plan snapshots can be
reused through the registry's `create-run` options.

This particular training case consumes its Draft training configuration: submitting
a job locks it. For another training submission, reuse `--baseline` and `--plan`
but run `prepare-case` again with a new run ID to create a fresh configuration.
Do not reuse `--data` from a previously submitted training case. The executor
checks this precondition before submitting a command.

API ports: support 28080, platform 28081, participants 28082 and 28083. This test
deployment does not include frontend consoles. Volumes persist until explicitly
removed; stopping a run does not destroy the deployment.

## Deployment Revisions

`compose-two-fixed.json` identifies the corrected platform deployment in the same
Compose project. Resolve, deploy and register it using a new provisioning run ID;
then create a new test run against `compose-two-20261116-fixed`. Do not overwrite
old deployment snapshots or claim that old runs used the new image. Existing
participant enrollment overrides are retained on redeployment. Execute baseline
and case preparation against the new snapshot to revalidate existing business
data instead of copying manifests from a different deployment ID.
