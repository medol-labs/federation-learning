## Selecting Cases For A Run

Case libraries are not execution plans. Add optional `tags` and `suites` arrays
to cases before approval; editing these fields invalidates approval.

Create a selection JSON, for example `{"tags":["smoke"],"excludeIds":["slow-case"]}`.
Supported keys: `ids`, `tags`, `suites`, `excludeIds`, `failedReport`.
Values within tags/suites are OR; different selectors are AND. Exclusions win,
and library order is retained. Empty arrays, unknown IDs, unknown selector keys
and zero matches fail. An empty object explicitly selects the whole library.

```bash
node simulation/src/cases.mjs select simulation/cases/approved/cases.json simulation/cases/runs/run-001.json simulation/pipelines/case-selection.json
```

The output is create-only: choose a new path for each plan. It contains approved
case snapshots, selection criteria and SHA-256 fingerprints. Data preparation
uses this same file via `--cases`; execution uses it as the first argument to
`execute-cases.mjs`. Selected plans automatically determine data-plan caseIds;
project data operations and requirement fulfillments still need explicit configuration.
Selecting fewer cases does not automatically split a shared project fixture operation.

For retry, use `{"failedReport":"../reports/previous.json"}`. The CLI resolves this
path relative to the selection JSON. Only failed cases are selected, not skipped
cases; changed or missing case versions are rejected. Reports from older executors
without case fingerprints cannot be used. Create a fresh plan and prepare its data
before retrying; existing environments and baseline data may be reused.
Tests stop at the first failure; remaining selected cases are skipped.
Prerequisites remain explicit data/business preconditions, not automatic execution
dependencies between cases.

## Project Workflow

Edit `simulation/pipelines/case-selection.json`, or set `MEDOL_CASE_SELECTION`
to another selection JSON. The project task resolves failedReport from the
project root (unlike the standalone CLI described above).

```bash
export MEDOL_PIPELINE_RUN_ID=selected-test-001
node simulation/src/task.mjs simulation/pipelines/tasks.json select-cases
node simulation/src/task.mjs simulation/pipelines/tasks.json prepare-case
node simulation/src/task.mjs simulation/pipelines/tasks.json test
```

These commands expect ready deployment and baseline artifacts for that run.
Reuse existing ones via `MEDOL_ENVIRONMENT_MANIFEST` and
`MEDOL_BASELINE_DATA_MANIFEST`. Selection is written to
`simulation/reports/workflows/<runId>/execution-plan.json`.
Both prepare-case and test consume it. `MEDOL_EXECUTION_PLAN` can point to an
existing immutable selected plan. Full local/remote pipelines select automatically
in their case-design step. Remote workers need the selection and retry report
available on that host.
