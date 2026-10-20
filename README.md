# Federation Learning

Federation Learning is a MEDOL-modeled business system workspace for Federation Learning Platform. The MEDOL source model is transformed into a CodegenModel and materialized by medol-codegen into backend, frontend, operations, and optional runtime artifacts.

The generated project is intended to be open and reviewable: keep the model,
generated artifacts, and hand-written business extensions in clear boundaries so
the system can be regenerated without losing domain-specific code.

## Layout

- `.medol/source.medol` is the MEDOL source snapshot that captures the business
  domain model.
- `.medol/codegen-model.json` is the generated CodegenModel consumed by
  `medol-codegen`.
- `.medol/medol.yml` configures generator output directories, frontend apps,
  operations targets, and project-level defaults.
- `.medol/medol.local.yml` may override local machine settings such as
  `operations.registry`; it is intentionally ignored by git.
- `federation-learning-platform/` contains generated command, event, query, aggregate,
  projection, and API code derived from the business model.
- `federation-learning-console/` contains generated application shells and resource-oriented
  screens derived from the same model.
- `operations/` contains generated deployment and operations assets.
- Hand-written business integrations should live in stable extension points such
  as `domain/...`, `infrastructure/...`, runtime modules, or other directories
  explicitly owned by the project.

## Business Domain

The business domain should be read from `.medol/source.medol` first. This
workspace is generated from these modeled domains:

- Federation Learning Platform: Organization Management, Federation Management, Dictionary Maintenance, File Upload, Runtime Provisioning, Dataset Governance, Model Repository, Training Orchestration, Runtime Monitoring, Runtime Governance, Secure Aggregation, Runtime Agent Operations.
- Identity Access Management: Identity Access Management.

When changing behavior, update the MEDOL model before regenerating framework
artifacts. Use hand-written extension points for implementation details that are
not part of the model itself.

## Generated Applications

Backend deployments:

- Federation Learning Support: Dictionary Maintenance, File Upload, Identity Access Management.
- Federation Learning Platform: Organization Management, Federation Management, Dataset Governance, Model Repository, Training Orchestration, Runtime Monitoring, Runtime Governance, Secure Aggregation, Runtime Provisioning.
- Federation Learning Runtime Agent: Runtime Agent Operations, Identity Access Management.

Frontend applications:

- Federation Learning Console: Organization Management, Federation Management, Dictionary Maintenance, File Upload, Runtime Provisioning, Dataset Governance, Model Repository, Training Orchestration, Runtime Monitoring, Runtime Governance, Secure Aggregation, Identity Access Management.
- Federation Learning Participant Console: Runtime Agent Operations, Identity Access Management.

## Generation

Run generators from this repository root. A typical flow is:

```bash
# 1. Export or refresh .medol/codegen-model.json from the MEDOL source model.
# 2. Run the required medol-codegen targets from this workspace root.
gen /opt/codegen/.generator/app/ --generator axon5 --generator-type all
gen /opt/codegen/.generator/app/ --generator refine --generator-type all --frontend-app FederationLearningConsole
gen /opt/codegen/.generator/app/ --generator refine --generator-type all --frontend-app FederationLearningParticipantConsole
gen /opt/codegen/.generator/app/ --generator operations --generator-type all --environment dev
```

Generated code may be overwritten during regeneration. Keep custom behavior in
the extension locations documented by each generated module.

## Project Documents

The project-specific documentation pack lives in
`docs/document-pack/federation-learning/`. It uses the MEDOL model as the
source of facts and formats the content to match the project Word documents in
the workspace-level `项目文档/` directory.

```bash
node scripts/generate-project-docs.mjs
```

The generated Markdown and Word files are written to `docs/generated/` and are
intentionally ignored by git. The output contains both standalone subsystem
documents and merged drafts that can be folded into the platform-level
documents.

The 438C delivery-document adapter lives in `docs/438c/`. It keeps the 438C
mapping, project metadata, and manual section overrides in this repository as
project-specific delivery material:

```bash
node scripts/export-438c-source-docs.mjs
node scripts/generate-438c-docs.mjs
node scripts/generate-438c-docs.mjs --format=markdown
node scripts/generate-438c-docs.mjs --only=software-requirements
```

Generated 438C Markdown and Word files are written to `docs/generated/438c/`.
Manual content that should survive regeneration belongs under
`docs/438c/manual/<document-id>/`.
Deployment units, images, ports, environment variables, health checks, gateway
routes, and install/test commands are collected from `operations/` through
`docs/document-support/config/engineering-facts-sources.json`.

## Local Development

Use the generated module README files and scripts for concrete commands. Common
workflows include:

```bash
# Backend
./mvnw test

# Frontend
npm install
npm run dev
```

Not every generated workspace contains every stack. Follow the directories
present in this repository.

### Modeling Verification

Use the repository verification script after changing `.medol/source.medol`,
dictionary seeds, generated CodegenModel snapshots, or generated frontend/backend
artifacts:

```bash
node scripts/verify-modeling-consistency.mjs
node scripts/verify-modeling-consistency.mjs --full
```

The default run checks MEDOL parsing, example-model parsing, CodegenModel
synchronization, dictionary seed validity, personalized federation storage rules,
runtime monitoring/governance regressions, and removed lifecycle residue. `--full`
also runs the platform console typecheck and the backend Maven reactor
`test-compile`.

### K3D Training Flow

The local K3D smoke flow is a repository-level workflow because it coordinates
operations manifests, platform APIs, managed runtime-agent deployment,
runtime-agent dataset bootstrap, and runtime-engine images.

```bash
node scripts/k3d-training-flow.mjs
```

Useful options:

```bash
node scripts/k3d-training-flow.mjs --recreate
node scripts/k3d-training-flow.mjs --scenario csv
node scripts/k3d-training-flow.mjs --federation-mode personalized
node scripts/k3d-training-flow.mjs --participant-count 2
node scripts/k3d-training-flow.mjs --scenario densenet --participant-count 2 --federation-mode personalized --training-run-key personal-test-1 --wait-for-training
node scripts/k3d-training-flow.mjs --skip-cluster --skip-apply
```

The DenseNet121 runtime plugin supports participant-scoped `LOCAL_ADAPTATION`
with `REGISTER_RUNTIME_LOCAL` storage. Each runtime saves the ordinary local
update first, then fine-tunes and validates a separate personalized model.
Global aggregation continues to consume the ordinary local updates. The platform
stores personalized model references, SHA-256 content digests, target lineage,
and measured accuracy/lift; the personalized model bytes remain on the runtime.
Unsupported personalization modes, plugins, scopes, and storage policies fail
explicitly.

`--training-run-key` creates an independent configuration and job while reusing
the participants. Reusing the same key reuses those identifiers.
`--wait-for-training` waits for the asynchronous job to complete and, in personalized
mode, checks that every participant has a runtime-local model catalog record.
Without that flag, the flow stops after successful submission.
If every participant fails, the flow reports their execution failure reasons
instead of waiting only for the platform job to finish. Other training timeouts
include the last observed job, round, and participant states.

Image build and flow use the same generated `operations/dev/image-config.mjs`.
Machine-specific settings belong in the ignored `.medol/medol.local.yml`:

```yaml
operations:
  registry:
    host: registry.internal.example:5000
    pushHost: registry-push.example:5000
    namespace: fl
    insecure: true
```

`host` is reachable from cluster nodes. Optional `pushHost` is the host-side
address of the same registry (for example, a locally published Zot port); omit it
when both addresses are identical. Regenerate operations after changing this
configuration so `operations/dev/operations-model.local.json` and registry mirrors
are updated. Recreate k3d if its registry mirror configuration changes.

Both `node operations/dev/images.mjs list` and flow `--dry-run` show the resolved
build/deployment prefixes. Explicit CLI prefixes take precedence, followed by
environment variables and the generated environment configuration.
The runtime-engine image helper also reads this configuration when used inside
this workspace. Build and push it with the same plugin profile selected by flow.

#### Build And Run

Run these commands from the repository root after configuring and generating
operations for the local registry. No explicit `--prefix` is needed: build and
push use `registry.pushHost` (or `registry.host` when omitted), while deployment
uses `registry.host`, both with the configured namespace.

Build and publish the three backends (platform, runtime-agent, support):

```bash
node operations/dev/images.mjs build --skip-console --platform linux/amd64
node operations/dev/images.mjs push --skip-console
```

`--skip-console` skips both frontend images: `federation-learning-console` and
`federation-learning-participant-console`. Remove it from both commands when
frontend changes also need to be built and published. Set the backend platform
to match the cluster architecture, or use amd64 only where emulation is available.

The runtime engine is separate from these application images. Build and publish
the DenseNet profile explicitly (this example targets ARM64 runtime nodes):

```bash
cd federation-learning-runtime-engine
node scripts/image-bundle.mjs build --plugin-profile pytorch-vision --platform linux/arm64
node scripts/image-bundle.mjs push --plugin-profile pytorch-vision
cd ..
```

Use `linux/amd64` instead for x86_64 runtime nodes. Rebuilding backend images alone
does not update the Python training engine. Kubernetes training engines use the
image and optional digest recorded in each execution plan; an agent environment
default must not override that snapshot.

Run and verify two-participant personalized training:

```bash
node scripts/k3d-training-flow.mjs \
  --scenario densenet \
  --participant-count 2 \
  --federation-mode personalized \
  --training-run-key "personal-$(date +%Y%m%d-%H%M%S)" \
  --wait-for-training \
  --timeout 600000
```

For ordinary federation, use `--federation-mode global`. Use a new run key after
changing the engine image so the flow creates a new configuration snapshot and
job instead of reusing an old one. This command preserves existing cluster data;
`--recreate` is not required for an image update and deletes cluster-local data.
The timeout is a per-wait limit in milliseconds, not the overall flow duration.

Flow `--image-source registry` (the default when a registry is configured) pulls
the exact configured images and deploys immutable registry digests. It never
pushes an arbitrary local image over a registry tag. `--image-source import`
imports the exact configured local images into every k3d node and refreshes
deployments by image ID. Missing images are errors, not a reason to try a different
prefix. `MEDOL_IMAGE_SOURCE` can also select the policy. Use
`--rebuild-app-images` to rebuild backend images first; only registry mode pushes
them. That option does not rebuild the runtime-engine image. Without registry
settings, the default policy is local import.

To run the phases manually, use:

```bash
node scripts/init-training-prerequisites.mjs --target platform --scenario densenet
node scripts/init-training-prerequisites.mjs --target runtime-agent --scenario densenet
node scripts/init-training-prerequisites.mjs --target training-job --scenario densenet
```

## Local Registry

Keep machine-specific registry settings in `.medol/medol.local.yml` and
regenerate operations files when needed:

```yaml
operations:
  registry:
    host: registry.example.com
    namespace: team
    insecure: true
```

Generated registry overlays and `operations/<environment>/k3s/cluster/registries.yaml` are machine-local artifacts
and should not be committed.
