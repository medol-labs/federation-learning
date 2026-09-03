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

## Container Plugin Profiles

Runtime Engine can be built as profile-specific images so offline deployments do
not have to carry every model dependency in one image. The source code is the
same; `RUNTIME_ENGINE_PLUGIN_PROFILE` controls which model and aggregation
plugin implementations are installed and reported by
`runtime-engine-capabilities`.

```text
profile           model plugins                         aggregation plugins
core              none                                  none
sklearn           SKLEARN_LOGISTIC_REGRESSION           FED_AVG_JSON
pytorch-vision    DenseNet, DenseNet121, ResNet, UNet   FED_AVG_PYTORCH_STATE_DICT
pytorch-tabular   TabNet                                FED_AVG_PYTORCH_STATE_DICT
pytorch-nlp       BERT and Clinical BERT                FED_AVG_PYTORCH_STATE_DICT
pytorch-graph     GCN                                   FED_AVG_PYTORCH_STATE_DICT
full              all built-in plugins                  all built-in plugins
```

Build a profile image:

```bash
docker build \
  -f docker/pytorch/Dockerfile \
  --build-arg PLUGIN_PROFILE=pytorch-vision \
  -t medol/federation-learning-runtime-engine:pytorch-vision .
```

Or use compose:

```bash
RUNTIME_ENGINE_PLUGIN_PROFILE=pytorch-vision \
RUNTIME_ENGINE_IMAGE=medol/federation-learning-runtime-engine:pytorch-vision \
docker compose -f docker-compose.yml -f docker-compose.build.yml build
```

`RUNTIME_ENGINE_ENABLED_MODEL_PLUGINS` can further restrict a built image to a
comma-separated allowlist of plugin codes or aliases. This only narrows the
reported runtime capabilities; it does not install missing dependencies.

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
and dispatches plugin codes such as `SKLEARN_LOGISTIC_REGRESSION`,
`PYTORCH_DENSENET_CLASSIFIER`, `PYTORCH_TABNET_CLASSIFIER`,
`FED_AVG_JSON`, or `FED_AVG_PYTORCH_STATE_DICT`;
the runtime engine resolves those codes through local registries under
`python/gemifl/runtime/plugins`.

The current built-in plugins are:

```text
Model plugins:
  SKLEARN_LOGISTIC_REGRESSION
  PYTORCH_DENSENET_CLASSIFIER
  PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER
  PYTORCH_RESNET_CLASSIFIER
  PYTORCH_TABNET_CLASSIFIER
  PYTORCH_UNET_2D_SEGMENTER
  PYTORCH_UNET_3D_SEGMENTER
  PYTORCH_BERT_CLASSIFIER
  PYTORCH_CLINICAL_BERT_CLASSIFIER
  PYTORCH_GCN_CLASSIFIER

Aggregation plugins:
  FED_AVG_JSON
  FED_AVG_PYTORCH_STATE_DICT
```

The shorter names `DENSENET`, `RESNET`, `TABNET`, `UNET`, `UNET_3D`, `BERT`,
`CLINICAL_BERT`, and `GCN` are aliases for compatibility. The canonical codes
include the runtime and task shape so the platform can safely present a single
`modelPlugin` selector without separately asking users for architecture,
runtime, and task fields.

`PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER` uses torchvision's DenseNet121
implementation. It accepts ImageFolder-compatible inputs through
`input.dataset.train` and `input.dataset.val`, supports `input.globalModel` as a
local/`file://` state-dict artifact or JSON descriptor, and can optionally load
torchvision's ImageNet weights when `modelParameter.pretrainedWeights` is
`DEFAULT` or `IMAGENET1K_V1`. Leave `pretrainedWeights` empty for offline
deployments unless the weight cache is already present in the image or mounted
runtime environment.

The PyTorch plugins are adapters around the existing legacy model classes. They
expect their training and validation inputs through `input.dataset.train` and
`input.dataset.val` or the equivalent node parameter data source, plus the
model-specific `modelParameter` values required by the underlying class, such as
image folders, CSV column settings, pretrained BERT paths, or graph data files.
They emit a local update descriptor that points at the saved model pickle and
PyTorch state-dict artifacts. Use `FED_AVG_PYTORCH_STATE_DICT` for those
updates; `FED_AVG_JSON` is for JSON weight updates such as the sklearn logistic
regression plugin output.

For legacy PyTorch plugins, `input.globalModel` may be either a parameter JSON
payload or a local/`file://` JSON descriptor containing `weightArtifact` or
`modelArtifact`. The wrapper maps that value to the legacy config
`load_model_path` and calls `set_weights(parameters)` when the artifact contains
named parameters.

Use `runtime-engine-capabilities` to print the registered model and aggregation
plugins for the selected runtime-engine profile:

```bash
RUNTIME_ENGINE_PLUGIN_PROFILE=pytorch-vision runtime-engine-capabilities --pretty
```

The Federation Learning platform does not call this runtime-engine command
directly. Use the output as an operator/developer reference and manually update
the platform dictionary entries that describe available model and aggregation
plugins. New plugins are added by implementing a plugin class and registering it
in `gemifl.runtime.plugins.registry`.

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
    "plugin": "FED_AVG_JSON"
  }
}
```

`FED_AVG` remains a generic compatibility alias. If a job uses that generic
code, the runtime resolves it to the profile default: `FED_AVG_JSON` for the
sklearn profile and `FED_AVG_PYTORCH_STATE_DICT` for PyTorch profiles. Prefer
dispatching the explicit aggregation plugin code once the platform knows the
model update format.

The registry is intentionally explicit: runtime requests can select a registered
plugin by code, but they cannot import arbitrary Python modules.

## Demo: Tiny ImageNet With DenseNet121

This demo validates the torchvision DenseNet121 plugin with a small
ImageFolder-compatible subset of Tiny ImageNet. It is meant for runtime-engine
smoke testing, not model quality evaluation.

Download Tiny ImageNet on a machine with network access:

```bash
mkdir -p /tmp/fl-demo-data
cd /tmp/fl-demo-data
curl -L -o tiny-imagenet-200.zip http://cs231n.stanford.edu/tiny-imagenet-200.zip
unzip tiny-imagenet-200.zip
```

Create a tiny two-class ImageFolder subset:

```bash
python - <<'PY'
from pathlib import Path
import shutil

source = Path("/tmp/fl-demo-data/tiny-imagenet-200")
target = Path("/tmp/fl-demo-data/tiny-imagenet-densenet-demo")
classes = ["n01443537", "n01629819"]

for split in ["train", "val"]:
    for class_id in classes:
        (target / split / class_id).mkdir(parents=True, exist_ok=True)

for class_id in classes:
    images = sorted((source / "train" / class_id / "images").glob("*.JPEG"))[:20]
    for image in images[:16]:
        shutil.copy2(image, target / "train" / class_id / image.name)
    for image in images[16:20]:
        shutil.copy2(image, target / "val" / class_id / image.name)

print(target)
PY
```

Run the runtime engine container and mount the demo dataset:

```bash
docker run --rm \
  -p 18080:8080 \
  -e RUNTIME_ENGINE_NODE_NAME=local-runtime \
  -e RUNTIME_ENGINE_PLUGIN_PROFILE=pytorch-vision \
  -e RUNTIME_ENGINE_ROOT=/workspace/tmp/runtime-engine \
  -v /tmp/fl-demo-data/tiny-imagenet-densenet-demo:/workspace/datasets/tiny-imagenet-demo:ro \
  -v /tmp/fl-demo-data/runtime-engine-output:/workspace/tmp/runtime-engine \
  medol/federation-learning-runtime-engine:pytorch-vision
```

Submit a DenseNet121 training job from another shell:

```bash
curl -s -X POST http://localhost:18080/jobs \
  -H 'Content-Type: application/json' \
  -d '{
    "jobId": "tiny-imagenet-densenet121-demo",
    "roundId": 1,
    "nodeName": "local-runtime",
    "role": "trainer",
    "operation": "train",
    "input": {
      "dataset": {
        "train": "/workspace/datasets/tiny-imagenet-demo/train",
        "val": "/workspace/datasets/tiny-imagenet-demo/val"
      }
    },
    "output": {
      "localUpdate": "/workspace/tmp/runtime-engine/tiny-imagenet-densenet121-demo/local_update.json",
      "metrics": "/workspace/tmp/runtime-engine/tiny-imagenet-densenet121-demo/metrics.json",
      "weightArtifact": "/workspace/tmp/runtime-engine/tiny-imagenet-densenet121-demo/model_state_dict.pt",
      "modelArtifact": "/workspace/tmp/runtime-engine/tiny-imagenet-densenet121-demo/model.pt"
    },
    "modelParameter": {
      "modelPlugin": "PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER",
      "epochs": 1,
      "learningRate": 0.001,
      "trainBatchSize": 4,
      "validBatchSize": 4,
      "imageSize": 224
    },
    "runtimeRoot": "/workspace/tmp/runtime-engine"
  }'
```

Check job status and output artifacts:

```bash
curl -s http://localhost:18080/jobs/tiny-imagenet-densenet121-demo
cat /tmp/fl-demo-data/runtime-engine-output/tiny-imagenet-densenet121-demo/local_update.json
cat /tmp/fl-demo-data/runtime-engine-output/tiny-imagenet-densenet121-demo/metrics.json
```

For offline deployments, leave `modelParameter.pretrainedWeights` unset. If the
runtime image or mounted cache already contains torchvision's ImageNet weights,
you may set:

```json
{
  "modelParameter": {
    "pretrainedWeights": "IMAGENET1K_V1"
  }
}
```

The demo uses only the runtime-engine API. In the full Federation Learning flow,
the platform should register a model artifact with
`modelPlugin=PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER`, dispatch the execution
plan to the runtime agent, and let the runtime agent call this runtime-engine
job endpoint.

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

The default `docker-compose.yml` is intentionally runtime-only and does not
contain a `build` section. This keeps `docker compose up` usable in offline or
unstable-network environments when the image already exists locally.

If you want to build through Docker Compose instead of the image script, use the
build override explicitly:

```bash
docker compose -f docker-compose.yml -f docker-compose.build.yml build federation-learning-runtime-engine
```

Images default to the current host architecture (`linux/arm64` on Apple Silicon,
`linux/amd64` on x86_64). Override the target CPU architecture when needed:

```bash
node scripts/build-images.mjs --platform linux/arm64
```

Do not set `DOCKER_DEFAULT_PLATFORM=linux/amd64` in the runtime `.env` on an ARM
machine unless you intentionally want amd64 emulation. If Docker reports that a
local `linux/arm64` image does not match the specified `linux/amd64` platform,
remove that variable or run `unset DOCKER_DEFAULT_PLATFORM` before `docker compose up`.

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
docker image inspect medol/federation-learning-runtime-engine:0.0.1-SNAPSHOT
docker compose up -d federation-learning-runtime-engine
curl http://localhost:18080/healthz
```

Copy `.env.example` to `.env` to override image name, node name, host port, or
mounted host directories. Runtime artifacts are written under
`../volumes/tmp/runtime-engine` and `../volumes/models/runtime-engine`; datasets
are read from `../volumes/datasets`.

For k3s, run one runtime container per step and expose the HTTP control port
`8080`. k3s dynamically starts and places containers; it does not own FL
business decisions. Parameter transfer happens in the Federation Learning
platform by moving input and output artifacts between train and aggregate steps.
