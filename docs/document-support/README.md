# Document Support

This directory contains reusable technical-document support assets generated for the project.

## Engineering Facts

Collect deployment and operations facts from the current project:

```bash
node docs/document-support/collect-engineering-facts.mjs
```

The default output is `docs/generated/facts/engineering-facts.json`.
Backend and frontend technology stacks are read from generated Maven `pom.xml` and frontend `package.json` files when this command runs.

## Internationalization

Reusable document labels live under `docs/document-support/i18n/`.
Add a project language file such as `ja-JP.json` when another output language is required.
Missing keys fall back to `en-US`, then `base`, then the key itself.
