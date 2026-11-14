# Existing Deployment Training Tests

The local pipeline now uses independent data preparation and approved case
execution. The old run-training.mjs remains a legacy full-lifecycle entry point;
remote.json has not been migrated and must not be assumed to use these stages.

1. Prepare infrastructure and deploy services separately. Place a real ready
   deployment manifest at simulation/instances/local.json, including deploymentId,
   environment, target, kubeconfigFile, namespace, endpoints.platform and endpoints.support.
   These must be reachable from the machine executing the stages.
2. Review extensions/scenarios/training-acceptance.json. Resolve reviewIssues and
   approve it into simulation/cases/approved-training.json using cases.mjs approve.
   The pipeline references this approved file. Do not label a draft approved manually.
3. Run the data-preparation stage, or invoke training-stages.mjs prepare directly.
4. Run test using the existing data manifest and approved case plan. For stage-only
   pipeline execution explicitly reuse review-cases and prepare-data after checking
   their artifacts. Use a new report path for each test run.

From the project root:
```bash
node simulation/src/run.mjs --mode local --stage data-preparation
node simulation/extensions/runtime/training-stages.mjs test simulation/instances/local.json simulation/extensions/scenarios/two-participant-training.json simulation/data/participants.json simulation/cases/approved-training.json simulation/reports/training-001.json
```

Preparation does not create a cluster, build images, or deploy platform services.
It still creates participant infrastructure records, labels nodes and deploys
managed agents: those are this project's business prerequisites. Existing worker
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
