# Registered Deployment Workflows

See [the generic registry guide](../ENVIRONMENTS.md) for the environment, instance
and run contracts. These project definitions describe the current remote worker;
they are not built into the generator. Another cluster uses another definition,
environment ID, dedicated kubeconfig and deployment declaration.

To adopt the existing test deployment without rebuilding or resetting data, first
sync source using the registered worker configuration from the local project root:

```bash
node simulation/src/worker.mjs sync simulation/pipelines/registered-worker.json
```

Then run on the remote worker, from `/home/fl-simulation/test`:

```bash
node simulation/src/prepare-environment.mjs simulation/environments/fl-test-k3d.json
node simulation/src/instance-registry.mjs register-environment simulation/environments/fl-test-k3d.json simulation/reports/environment-fl-test-k3d.json
node simulation/src/instance-registry.mjs register-deployment simulation/deployments/fl-test-01.json simulation/reports/workflows/clean-20261116-current/deployment.json
node simulation/src/instance-registry.mjs create-run two-participants-001 fl-test-01 --baseline simulation/reports/workflows/clean-20261116-current/baseline.json
```

The old report paths above identify the already verified deployment; substitute
the actual deployment/baseline artifacts when adopting another instance. All
registration records are create-only. Do not repeat registration if already done.
Use list/show to inspect it. Only create-run is needed for another test run.

From the local project root, execute against the selected remote instance:

```bash
node simulation/src/run.mjs --mode remote --pipeline simulation/pipelines/registered-run-remote.json --run two-participants-001
```

This execution-only pipeline selects cases, prepares case data, runs tests and
collects evidence. It does not rebuild images, deploy services or register nodes.
The example assumes baseline data and participant registration already exist.
For fresh instances, run the baseline and participant enrollment tasks first with
MEDOL_SIMULATION_RUN_ID set to that run. A run using an existing --plan should omit
the select-cases task. A run reusing --data may also omit prepare-case.

For local execution, use the same registry/tasks on the local worker and omit SSH:

```bash
export MEDOL_SIMULATION_RUN_ID=local-test-001
node simulation/src/task.mjs simulation/pipelines/tasks.json select-cases
node simulation/src/task.mjs simulation/pipelines/tasks.json prepare-case
node simulation/src/task.mjs simulation/pipelines/tasks.json test
```

The generic registry also supports Docker Compose. This project's current training
fixture/scheduling adapter still requires Kubernetes; registering a Compose instance
does not implement a Compose training adapter. HTTP-only business tests can use
Compose endpoints through an appropriate executor without Kubernetes assumptions.
