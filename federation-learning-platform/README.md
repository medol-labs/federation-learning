# FederationLearningPlatform

Generated from Medol CodegenModel for Axon Framework 5.1.1.

## Requirements

- Java 21
- Docker with Compose support

## Run

Run this module from the generated multi-module root so Maven can include the sibling `shared-kernel` and `axon-event-storage-umadb` modules in the reactor. Spring Boot uses this module's own `docker-compose.yml`:

```bash
cd ..
cp federation-learning-platform/.env.example federation-learning-platform/.env
./mvnw -pl federation-learning-platform -am spring-boot:run
```

Health endpoint: `http://localhost:8081/actuator/health`

OpenAPI endpoints:

- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`

Default ports:

- Application: `8081`; override with `SERVER_PORT`
- PostgreSQL host port: `5433`; override with `DB_PORT` in this module's `.env`
- PostgreSQL database: `federation_learning_platform`; override the full connection with `DB_URL`
- UmaDB host port: `50052`; override with `UMADB_PORT` in this module's `.env`
- Axon Server UI: `http://localhost:8024`; override with `AXON_SERVER_HTTP_PORT`
- Axon Server gRPC: `localhost:8124`; override with `AXON_SERVER_SERVERS`

## Event Storage Mode

The default event storage is Axon Server, which supports multiple Axon event tags per event.

```bash
cd ..
./mvnw -pl federation-learning-platform -am spring-boot:run
```

Use the in-memory event store for local experiments or tests that should not connect to Axon Server:

```bash
cd ..
MEDOL_AXON_EVENT_STORAGE=inmemory AXON_SERVER_ENABLED=false ./mvnw -pl federation-learning-platform -am spring-boot:run
```

Use the generated UmaDB DCB event store adapter from the `axon-event-storage-umadb` module with this deployment module's `.env` file:

```bash
cd ..
cp federation-learning-platform/.env.example federation-learning-platform/.env
docker compose -f federation-learning-platform/docker-compose.yml up -d postgres umadb
./mvnw -pl federation-learning-platform -am spring-boot:run
```

To run the generated application as a container instead of `spring-boot:run`:

```bash
cd ..
docker compose -f federation-learning-platform/docker-compose.yml up -d federation-learning-platform
```

The UmaDB adapter implements Axon Framework's `EventStorageEngine` boundary over UmaDB's official `umadb.v1.DCB` gRPC service: events are stored with DCB tags, Axon event criteria are mapped to UmaDB queries, conditional append uses UmaDB's DCB conflict condition, and source/stream tokens use Axon's global next-position semantics. Axon processor checkpoints still use the generated `token_entry` table; UmaDB's optional `TrackingInfo` API is not used as an Axon token store.

## Seed Development Data

After the backend modules are running, seed demo data from the generated backend root:

```bash
node scripts/seed-dev-data.mjs
```

The seed tool reads `codegen-model.json`, calls generated command REST endpoints, and uses module-specific base URLs when deployments are present. Override URLs with environment variables named after deployment ids, for example `MY_BACKEND_URL=http://localhost:8080`.

Useful options:

```bash
node scripts/seed-dev-data.mjs --count 3
node scripts/seed-dev-data.mjs --mode workflow
node scripts/seed-dev-data.mjs --deployment MyBackend
node scripts/seed-dev-data.mjs --dry-run
```

## Training Prerequisites

Prepare the local medical federation, feature schema, model artifact, runtime
infrastructure package catalog, runtime installation plan, runtime identity,
runtime agent bootstrap configuration, runtime agent endpoint, dataset
declaration, runtime dataset binding, dataset access validation, and platform
runtime dataset metadata:

```bash
cd ..
node scripts/init-training-prerequisites.mjs
```

The script defaults to a manually managed Docker Compose runtime package:

```text
runtimeEnvironmentType=DOCKER_COMPOSE
runtimePackage=local-docker-compose-runtime-agent:0.0.1-SNAPSHOT
agentInstallMode=MANUAL
```

The default dataset is a synthetic hospital readmission risk CSV under
`../volumes/datasets/test.csv`. It uses medical-style columns such as
`age_years`, `systolic_bp_mm_hg`, `fasting_glucose_mg_dl`, and the binary label
column `readmission_risk`.

Preview payloads without calling services:

```bash
node scripts/init-training-prerequisites.mjs --dry-run
```

Useful options:

```bash
node scripts/init-training-prerequisites.mjs --runtime-environment-type K3S
node scripts/init-training-prerequisites.mjs --runtime-package-name local-k3s-runtime-agent
node scripts/init-training-prerequisites.mjs --runtime-package-version 0.0.1-SNAPSHOT
node scripts/init-training-prerequisites.mjs --agent-install-mode MANUAL
node scripts/init-training-prerequisites.mjs --expected-node-count 1
node scripts/init-training-prerequisites.mjs --register-runtime-infrastructure
node scripts/init-training-prerequisites.mjs --create-job
```

`--register-runtime-infrastructure` is optional because it can trigger the
runtime infrastructure verification flow. Keep it off for local bootstrap when
the runtime agent and runtime engine are already started manually.

If an older local database already contains the previous generic sample schema
(`x1`, `x2`, `y`), reset local dev data before rerunning the prerequisites so
the new medical feature schema and dataset capability projections are rebuilt.

## Clean Development Docker Data

To reset local Docker Compose databases and event-store volumes for selected generated deployment modules:

```bash
node scripts/clean-docker-compose-data.mjs --yes
```

The script discovers `docker-compose.yml` files under the generated backend root and module directories, then opens a checkbox list. Use arrow keys to move, space to select or clear a module, and enter to confirm. It runs `docker compose -f <file> down -v --remove-orphans` only for the selected modules. Preview the selected compose files without deleting data:

```bash
node scripts/clean-docker-compose-data.mjs --dry-run
```

## Build

```bash
cd ..
./mvnw -pl federation-learning-platform -am clean verify
```

## Container Image

Build all generated deployment images:

```bash
cd ..
node scripts/build-images.mjs --module federation-learning-platform
```

Images default to `linux/amd64`. Override the target CPU architecture when needed:

```bash
node scripts/build-images.mjs --platform linux/arm64
```

Export the generated images to a Docker archive for offline transfer:

```bash
cd ..
node scripts/export-images.mjs --module federation-learning-platform
```

Pull and export Docker Compose dependency images, such as databases and event stores, for the same target platform:

```bash
node scripts/export-dependency-images.mjs --platform linux/amd64 --output dependency-images.tar
```

Preview the discovered dependency images:

```bash
node scripts/dependency-images.mjs list
```

Collect deployment Docker Compose files and matching `.env.example` files into one folder:

```bash
node scripts/collect-deployment-compose-files.mjs --clean
```

Import the archive on another machine:

```bash
node scripts/import-images.mjs --file federation-learning-platform-images.tar
docker load -i dependency-images.tar
```

Build and export in one command:

```bash
cd ..
node scripts/image-bundle.mjs all --module federation-learning-platform
```

The build script uses Maven/Jib under the hood:

```bash
cd ..
./mvnw -pl federation-learning-platform -am -DskipTests install
./mvnw -pl federation-learning-platform -DskipTests -Djib.container.platform.os=linux -Djib.container.platform.architecture=amd64 com.google.cloud.tools:jib-maven-plugin:3.4.5:dockerBuild
```

The generated image is `medol/federation-learning-platform:0.0.1-SNAPSHOT` and exposes port `8081`.
The container disables Spring Boot docker-compose integration; pass `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for the runtime database.

Root package: `tech.medo`

Generated command-side slices use Axon 5 event-sourced entities, explicit event tags, and composite event criteria derived from Medol slice tags. Concept states generate Kotlin enums, and `Concept.State` fields are updated from matching `stateChange` definitions. Read models generate JPA entities, repositories, REST resources, and event-handling projectors. Add production projection-table definitions under `src/main/resources/db/migration` when replacing Hibernate's generated schema.
