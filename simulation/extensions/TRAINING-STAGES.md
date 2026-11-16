# Existing Deployment Training Tests

## Registration Only

After deployment and baseline preparation, run on the worker from the project root:

```sh
export MEDOL_PIPELINE_RUN_ID=baseline-redeploy-20261116
node simulation/src/task.mjs simulation/pipelines/tasks.json enroll-participants
```

This consumes deployment.json and baseline.json from that run's workflow directory
(or MEDOL_ENVIRONMENT_MANIFEST / MEDOL_BASELINE_DATA_MANIFEST overrides). It creates
organizations, federation memberships and MANUAL_GUIDED infrastructure records,
enrolls existing Agent slots and waits for actual endpoint reports. It does not
create schemas, datasets, models, training configurations or training jobs.
It outputs enrollment.json with mode=enrollment, not a ready training-data manifest.
Use a new run ID with manifest overrides on a subsequent attempt; outputs are create-only.

The local pipeline now uses independent data preparation and approved case
execution. The old run-training.mjs remains a legacy full-lifecycle entry point;
remote.json has not been migrated and must not be assumed to use these stages.

1. Prepare infrastructure and deploy services separately. Place a real ready
   deployment manifest at simulation/instances/local.json, including deploymentId,
   environment, target, kubeconfigFile, namespace, endpoints.platform and endpoints.support.
   These must be reachable from the machine executing the stages.
2. Review cases/manual/training.json. Resolve reviewIssues and
   approve it into simulation/cases/approved/training.json using cases.mjs approve.
   The pipeline references this approved file. Do not label a draft approved manually.
3. Run prepare-baseline for shared authentication/dictionaries and system health,
   then prepare-case for the approved cases' business fixtures. They are independent
   commands with separate output manifests; case preparation consumes the baseline.
4. Run test using the existing data manifest and approved case plan. For stage-only
   pipeline execution explicitly reuse review-cases and prepare-case after checking
   their artifacts. Use a new report path for each test run.

From the project root:
```bash
export MEDOL_PIPELINE_RUN_ID=training-001
export MEDOL_ENVIRONMENT_MANIFEST="$PWD/simulation/instances/local.json"
node simulation/src/task.mjs simulation/pipelines/tasks.json prepare-baseline
node simulation/src/task.mjs simulation/pipelines/tasks.json prepare-case
node simulation/src/task.mjs simulation/pipelines/tasks.json test
```

Generic task configuration is pipelines/tasks.json; it declares input manifests,
optional environment overrides and output names. Results are written under
simulation/reports/workflows/$MEDOL_PIPELINE_RUN_ID/. Project exports live in
extensions/adapters/workflow-operations.mjs. The former workflow-stage.mjs and
sync-worker.mjs entries have been removed; use src/task.mjs and src/worker.mjs.

Preparation does not create a cluster, build images, or deploy platform services.
Baseline preparation does not need participants or cases: it initializes shared
admin access and dictionaries, then checks support/platform health and authenticated
dictionary access. It does not create organizations, datasets or training jobs.
Agents may still be waiting for enrollment; baseline readiness does not promise
every domain workflow is usable.

Case preparation requires an approved case plan and a ready baseline from the same
deployment. It validates baseline availability without repeating admin/dictionary
initialization, then creates participant infrastructure records, labels nodes and
enrolls already-deployed MANUAL_GUIDED Agents, schemas, datasets and training
configuration. It does not deploy new Agents or submit a training job. Existing worker
capacity and images must already be available. The current adapter remains
Kubernetes-specific, while the generic case executor is infrastructure-neutral.

Participant fixtures omit temporary localhost port-forward URLs. On test execution
the adapter checks runtime identity and recreates access tunnels. Credentials are
loaded at execution time through the existing project authentication configuration.
Do not put tokens or passwords in manifests. Tests submit a new training job;
they do not regenerate participants. Result assertions reuse verify-training.mjs
for contributions, completed job, submitted updates and model artifact verification.

For an existing remote deployment use pipelines/training-remote.json-example.
It runs only case review, data preparation and testing over SSH; it does not build,
sync, provision or deploy. Sync code, the approved case and deployment manifest
explicitly beforehand. The local and remote manifest must describe the same
deployment; kubeconfig and endpoints are interpreted on the remote machine.
Existing pipelines/remote.json remains the legacy build/full-flow pipeline.

No remote cluster is created or tested by the automated unit tests. They use fake
adapters to verify stage boundaries. End-to-end verification still requires an
actual deployment and reviewed case. Live fixture checks verify runtime identity,
connectivity, dataset metadata/contract readiness and configuration existence.
The fixture retains trainingRunKey so configuration IDs survive separate runs;
each submission supplies a fresh training-job-key. Old fixtures lacking these
identifiers must be prepared again. This does not fingerprint every configuration
field or inspect dataset contents; those remain runtime/business validation.

New baseline manifests use mode=baseline; case fixtures use mode=case and retain
case IDs, the approved-plan fingerprint and baseline fingerprint. Baseline data is
not accepted as training-test data. Older combined manifests must be re-prepared
through these split commands (existing business records can be reused); do not
rewrite their mode by hand. Changed cases require refreshed case fixtures.

The workflow wrapper exposes prepare-baseline and prepare-case as well. Set
MEDOL_ENVIRONMENT_MANIFEST to reuse a deployment; set MEDOL_BASELINE_DATA_MANIFEST
to reuse another run's baseline. Otherwise it reads baseline.json from the current
run directory. Full pipelines have separate prepare-baseline and prepare-case steps
within stage data-preparation, so --stage data-preparation runs both selected steps;
use the direct commands above to execute only one operation.
