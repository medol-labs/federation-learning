# Dictionary Init

Standalone dictionary initializer. It does not read `codegen-model.json` and is not wired into the code generator.

Run a dry run:

```bash
node init-dictionaries.mjs --base-url http://localhost:8080 --dry-run
```

Initialize through the dictionary backend API:

```bash
node init-dictionaries.mjs --base-url http://localhost:8080
```

Useful options:

```bash
node init-dictionaries.mjs --data dictionaries.json
node init-dictionaries.mjs --force
node init-dictionaries.mjs --header "Authorization: Bearer <token>"
node init-dictionaries.mjs --register-path /dictionary/registerdictionary
node init-dictionaries.mjs --add-value-path /dictionaryvalue/adddictionaryvalue
```

## Offline Deployment Package

Build and package backend, console, runtime-engine, dependency images, and
deployment compose files into one compressed directory:

```bash
node package-offline-deployment.mjs --platform linux/amd64
```

The script writes:

```text
dist/offline-deployment-package/
dist/offline-deployment-package.tar.gz
```

Useful options:

```bash
node package-offline-deployment.mjs --platform linux/arm64
node package-offline-deployment.mjs --output dist/fl-offline --archive dist/fl-offline.tar.gz
node package-offline-deployment.mjs --dry-run
node package-offline-deployment.mjs --skip-runtime-engine
node package-offline-deployment.mjs --skip-dependency-images
node package-offline-deployment.mjs --skip-archive
```

The package contains image tar files under `images/`, Docker Compose and
`.env-example` files under `compose/`, and a `manifest.json` for traceability.
