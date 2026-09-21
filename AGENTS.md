# AGENTS.md

- Treat `.medol/codegen-model.json`, `.medol/source.medol`, and `.medol/medol.yml` as system-level generation inputs.
- This is a monorepo: generated backend code lives under `federation-learning-platform/`, generated frontend code under `federation-learning-console/`, runtime execution code under `federation-learning-runtime-engine/`, and bootstrap scripts under `dictionary-init/`.
- Do not hand-edit generated backend `context/` code unless explicitly requested for an emergency local fix.
- Put hand-written backend adapters under `infrastructure/` and hand-written decision overrides under `domain/`.
- If a framework-level generated behavior is wrong, update `medol-codegen` templates instead of patching generated outputs.
- Do not put concrete business-system behavior into the generator; express business behavior in the Medol model or generated project extension points.
