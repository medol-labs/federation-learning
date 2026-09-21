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

- Federation Learning Platform: Organization Management, Federation Management, Dictionary Maintenance, File Upload, Runtime Provisioning, Dataset Governance, Model Repository, Training Orchestration, Model Lifecycle, Runtime Monitoring, Runtime Governance, Secure Aggregation, Runtime Agent Operations.
- Identity Access Management: Identity Access Management.

When changing behavior, update the MEDOL model before regenerating framework
artifacts. Use hand-written extension points for implementation details that are
not part of the model itself.

## Generated Applications

Backend deployments:

- Federation Learning Support: Dictionary Maintenance, File Upload, Identity Access Management.
- Federation Learning Platform: Organization Management, Federation Management, Dataset Governance, Model Repository, Training Orchestration, Model Lifecycle, Runtime Monitoring, Runtime Governance, Secure Aggregation, Runtime Provisioning.
- Federation Learning Runtime Agent: Runtime Agent Operations, Identity Access Management.

Frontend applications:

- Federation Learning Console: Organization Management, Federation Management, Dictionary Maintenance, File Upload, Runtime Provisioning, Dataset Governance, Model Repository, Training Orchestration, Model Lifecycle, Runtime Monitoring, Runtime Governance, Secure Aggregation, Identity Access Management.
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
