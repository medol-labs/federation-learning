# federation-learning-runtime-engine — AGENTS.md

This repo contains the Python training runtime used by the runtime agent to run local training and aggregation support.

## Role

- The platform and runtime-agent own orchestration.
- This runtime engine owns local model training, model parameter artifacts, and runtime training APIs.
- Current integration focus is running the local/container flow with `linear.LogisticRegression` first.

## Rules

- Do not commit `.venv`, local datasets, generated model outputs, secrets, or temporary runtime files.
- Keep Docker compose and Dockerfile changes aligned with how the runtime-agent starts the engine.
- Prefer small, explicit APIs for train/aggregate/status flows.
- Model-specific behavior should be expressed through configuration and adapters where possible.

## Validation

Use the narrowest local command that exercises the changed runtime path. Typical checks include starting the runtime API locally or via Docker compose and calling it with `curl`.

Python dependencies should be isolated in a virtual environment, but the environment directory itself must remain untracked.
