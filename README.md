# Medol Generated System

This repository is a Medol generated system workspace.

## Layout

- `.medol/codegen-model.json` is exported from Medol and consumed by generators.
- `.medol/source.medol` is the downloaded MEDOL source snapshot for review and traceability.
- `.medol/medol.yml` configures generator output directories and operations settings.
- `.medol/medol.local.yml` may override local machine settings such as `operations.registry`; it is intentionally ignored by git.
- `federation-learning-platform/` contains the generated Axon 5 / Spring Boot backend.
- `federation-learning-console/` contains the generated Refine / React frontend.
- `federation-learning-runtime-engine/` contains the Python runtime engine for local training and aggregation.
- `dictionary-init/` contains dictionary bootstrap and offline packaging scripts.
- `operations/` contains generated deployment and operations assets when rendered.

## Generation

Run generators from this repository root.

## Local Registry

Keep machine-specific registry settings in `.medol/medol.local.yml` and regenerate operations files when needed:

```yaml
operations:
  registry:
    host: 192.168.50.2:5000
    namespace: fl
    insecure: true
```

Generated registry overlays and `k3s/cluster/registries.yaml` are local artifacts and should not be committed.
