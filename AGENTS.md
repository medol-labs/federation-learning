# dictionary-init — AGENTS.md

This repo contains the Node CLI used to bootstrap dictionary values into the support/backend API.

## Rules

- `dictionaries.json` is the source data for bootstrap dictionary entries.
- Keep command paths and payloads aligned with the generated support service API.
- Do not hard-code local-only backend URLs in committed files; pass `--base-url` when running locally.
- Prefer dry runs before applying changes.

## Commands

```bash
node init-dictionaries.mjs --base-url http://localhost:8080 --dry-run
node init-dictionaries.mjs --base-url http://localhost:8080
```
