# Runtime Engine

Runtime Engine is the training executor used by the Federation Learning platform.
The platform owns business logic, scheduling intent, approvals, audit, lifecycle
state, model versioning, and model-parameter transport. This project only runs a
single platform-dispatched training or aggregation step inside one runtime
container.

## Runtime Boundary

The Federation Learning platform handles:

- training job creation and cancellation
- participant and node selection
- job, round, and model lifecycle state
- audit records and operational dashboards
- k3s orchestration inputs for dynamic runtime containers
- model parameter transport between nodes and rounds
- training and aggregation artifact collection
- Paillier key generation, storage, rotation, and injection

Runtime Engine handles:

- starting one platform-dispatched job step
- local dataset training
- local aggregation over platform-provided update artifacts
- local metrics, progress, and artifact files

TaskManager, gemicli, and the old node registration server have been removed.
k3s is only the container orchestration layer. It starts runtime containers and
provides service discovery, while the Federation Learning platform remains the
business control plane.

## Install

```bash
cd python
pip install -r requirements.txt
pip install -e .
```

## Entrypoints

Start a platform-controlled runtime node:

```bash
runtime-engine-node --host 0.0.0.0 --port 8080 --node-name Alice --runtime-root ./tmp/runtime-engine
```

The runtime node exposes a small HTTP control API:

```text
GET  /healthz
POST /jobs
GET  /jobs/{jobId}
GET  /jobs/{jobId}/artifacts/{artifactName}
POST /jobs/{jobId}/cancel
```

The request body for `POST /jobs` is one runtime step JSON. The platform should
send a node-local payload containing only the inputs and outputs visible to that
runtime container. Runtime payloads do not include other node addresses.

Run one job from a config file is only a local debugging path:

```bash
runtime-engine-run-job --config python/gemifl/model_config/linear/HorizontalLogisticRegression.json --node-name Alice
```

In platform mode, the Federation Learning platform should not exec
`runtime-engine-run-job` directly. It should create or reuse a runtime container through
k3s, then call `POST /jobs` on that runtime node. The runtime persists the
payload under `runtimeRoot` and invokes the executor internally.

## Platform Dispatch

The platform dispatch flow is:

1. Build one node-local step payload from business state, participant selection,
   model parameters, dataset bindings, artifact paths, and
   KeyAndAttestationManagement outputs.
2. Start or select one runtime container for that step through k3s.
3. Mount platform-managed resources, such as datasets, model input artifacts,
   output paths, and Paillier key Secrets.
4. POST the node-local payload to that runtime node.
5. Collect the output artifacts and decide the next train or aggregate step.
6. Poll `GET /jobs/{jobId}` or cancel through `POST /jobs/{jobId}/cancel`.

The runtime accepts platform-owned parameters in the job payload. It does not
derive business decisions locally, discover peer nodes, or transfer parameters
to other runtime containers.

## Job Config Shape

```json
{
  "jobId": "training-job-001-round-3-alice",
  "roundId": 3,
  "nodeName": "Alice",
  "role": "trainer",
  "operation": "train",
  "input": {
    "globalModel": "/workspace/inputs/global_model_round_2.json",
    "dataset": {
      "path": "/workspace/datasets/alice.csv",
      "labelColumn": "y",
      "id": "id"
    }
  },
  "output": {
    "localUpdate": "/workspace/outputs/alice_round_3_update.json",
    "metrics": "/workspace/outputs/alice_round_3_metrics.json"
  },
  "modelParameter": {
    "modelPlugin": "SKLEARN_LOGISTIC_REGRESSION",
    "engine": "python",
    "process": "train",
    "epoch": "10",
    "learningRate": 0.1
  },
  "jobParameter": {
    "encryptMethod": "plain",
    "tryLimit": 100000,
    "tryTimeSleep": 5,
    "loggerLevel": "INFO",
    "paillierPublicKey": "/workspace/secrets/paillier/public_key.pkl",
    "paillierPrivateKey": "/workspace/secrets/paillier/private_key.pkl"
  }
}
```

Aggregation is also a node-local step. The platform supplies all update artifact
references collected from training nodes. A reference may be a local path or an
HTTP(S) URL returned by Support FileUpload storage:

```json
{
  "jobId": "training-job-001-round-3-aggregate",
  "roundId": 3,
  "nodeName": "Aggregator",
  "role": "aggregator",
  "operation": "aggregate",
  "input": {
    "updates": [
      "http://support:8080/api/files/11111111-1111-4111-8111-111111111111/content",
      "http://support:8080/api/files/22222222-2222-4222-8222-222222222222/content"
    ]
  },
  "output": {
    "globalModel": "/workspace/outputs/global_model_round_3.json"
  },
  "modelParameter": {
    "modelPlugin": "SKLEARN_LOGISTIC_REGRESSION",
    "aggregationAlgorithm": "FED_AVG",
    "engine": "python"
  }
}
```

When an update reference is HTTP(S), Runtime Engine sends
`X-MEDOL-INTERNAL-TOKEN` using `MEDOL_SECURITY_INTERNAL_TOKEN`. After an
aggregation job completes, the platform downloads `globalModel` from
`GET /jobs/{jobId}/artifacts/globalModel`, uploads it to Support, and records the
returned immutable artifact URI and digest in the model artifact catalog.

`RUNTIME_ENGINE_FILE_ACCESS_BASE_URL` can replace the origin of HTTP(S) file
references while preserving their path and query string. The local Docker
Compose configuration defaults it to `http://host.docker.internal:8080`, because
`localhost:8080` inside the runtime-engine container does not address a Support
service running on the host. Set it to the routable Support endpoint in other
deployments, or leave it empty when stored file URIs are already directly
reachable from Runtime Engine.

## Runtime Plugins

Platform-orchestrated jobs use code-level runtime plugins. The platform stores
and dispatches plugin codes such as `SKLEARN_LOGISTIC_REGRESSION` or `FED_AVG`;
the runtime engine resolves those codes through local registries under
`python/gemifl/runtime/plugins`.

The current built-in plugins are:

```text
Model plugins:
  SKLEARN_LOGISTIC_REGRESSION

Aggregation plugins:
  FED_AVG
```

`GET /capabilities` returns the registered model and aggregation plugins for the
running node. New plugins are added by implementing a plugin class and
registering it in `gemifl.runtime.plugins.registry`.

### Registering Plugins

Runtime plugins are registered in code. There is no dynamic plugin installation
or plugin management API yet.

To add a model plugin:

1. Create a plugin implementation under `python/gemifl/runtime/plugins`.
   The class should expose metadata and implement `train(config)`.

   ```python
   class SklearnLogisticRegressionPlugin:
       code = "SKLEARN_LOGISTIC_REGRESSION"
       aliases = ("linear.LogisticRegression", "SKLEARN_LOGISTIC_REGRESSION")
       supported_formats = ("JSON",)
       supported_tasks = ("BINARY_CLASSIFICATION",)

       def train(self, config: dict) -> None:
           ...
   ```

2. Register it in `MODEL_PLUGIN_REGISTRY` in
   `python/gemifl/runtime/plugins/registry.py`.

   ```python
   PluginRegistration(
       code="SKLEARN_LOGISTIC_REGRESSION",
       aliases=("linear.LogisticRegression", "SKLEARN_LOGISTIC_REGRESSION"),
       module="gemifl.runtime.plugins.sklearn_logistic_regression",
       class_name="SklearnLogisticRegressionPlugin",
       supported_formats=("JSON",),
       supported_tasks=("BINARY_CLASSIFICATION",),
   )
   ```

3. Dispatch training jobs with `modelParameter.modelPlugin`.

   ```json
   {
     "operation": "train",
     "modelParameter": {
       "modelPlugin": "SKLEARN_LOGISTIC_REGRESSION"
     }
   }
   ```

To add an aggregation plugin, follow the same pattern with
`AGGREGATION_PLUGIN_REGISTRY` and implement `aggregate(config)`. Dispatch
aggregation jobs with `aggregation.algorithm` or
`modelParameter.aggregationAlgorithm`.

```json
{
  "operation": "aggregate",
  "aggregation": {
    "algorithm": "FED_AVG"
  }
}
```

The registry is intentionally explicit: runtime requests can select a registered
plugin by code, but they cannot import arbitrary Python modules.

Paillier keys are not generated by the runtime image. The platform should
generate and manage them, then either pass key paths in `jobParameter`
(`paillierPublicKey`, `paillierPrivateKey`) or mount them and set
`RUNTIME_ENGINE_PAILLIER_PUBLIC_KEY` / `RUNTIME_ENGINE_PAILLIER_PRIVATE_KEY`.
Legacy `GEMIFL_*` variables are still accepted as fallbacks. If only
`RUNTIME_ENGINE_PAILLIER_KEY_DIR` is set, the runtime looks for `public_key.pkl` and
`private_key.pkl` in that directory.

For KeyAndAttestationManagement integration, the platform should translate its
key allocation into runtime-visible file paths before dispatch. For example:

```json
{
  "jobId": "training-job-001",
  "nodeName": "Alice",
  "jobParameter": {
    "encryptMethod": "pailler",
    "paillierPublicKey": "/workspace/secrets/paillier/job-001/public_key.pkl",
    "paillierPrivateKey": "/workspace/secrets/paillier/job-001/private_key.pkl"
  }
}
```

The payload can also include platform correlation identifiers such as
`federationId`, `experimentId`, or `roundId`; the runtime preserves unknown
fields in its persisted config but only acts on the fields needed by the
algorithm.

## Container

The default image is CPU-only. GPU support can be added later with a separate
base image and scheduling policy.

Build the runtime image:

```bash
node scripts/build-images.mjs
```

Images default to `linux/amd64`. Override the target CPU architecture when needed:

```bash
node scripts/build-images.mjs --platform linux/arm64
```

Export and import the image for an offline environment:

```bash
node scripts/export-images.mjs --output federation-learning-runtime-engine-images.tar
node scripts/import-images.mjs --output federation-learning-runtime-engine-images.tar
```

Build and export in one command:

```bash
node scripts/image-bundle.mjs all
```

The generated image is `medol/federation-learning-runtime-engine:0.0.1-SNAPSHOT`.
Override the image name with `--prefix`, `--image`, and `--version`, or set
`RUNTIME_ENGINE_IMAGE` before running Docker Compose.

Start the local runtime engine:

```bash
docker compose up -d runtime-engine
curl http://localhost:18080/healthz
```

Copy `.env-example` to `.env` to override image name, node name, host port, or
mounted host directories. Runtime artifacts are written under
`../volumes/tmp/runtime-engine` and `../volumes/models/runtime-engine`; datasets
are read from `../volumes/datasets`.

For k3s, run one runtime container per step and expose the HTTP control port
`8080`. k3s dynamically starts and places containers; it does not own FL
business decisions. Parameter transfer happens in the Federation Learning
platform by moving input and output artifacts between train and aggregate steps.
