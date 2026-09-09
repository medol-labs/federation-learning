# Medol Generated System

This repository is a Medol generated system workspace.

## Layout

- `.medol/codegen-model.json` is exported from Medol and consumed by generators.
- `.medol/source.medol` is the downloaded MEDOL source snapshot for review and traceability.
- `.medol/medol.yml` configures generator output directories and operations settings.
- `federation-learning-platform/` contains the generated Axon 5 / Spring Boot backend.
- `federation-learning-console/` contains the generated Refine / React frontend.
- `federation-learning-runtime-engine/` contains the Python runtime engine for local training and aggregation.
- `dictionary-init/` contains dictionary bootstrap and offline packaging scripts.
- `operations/` contains generated deployment and operations assets when rendered.

## Generation

Run generators from this repository root.
