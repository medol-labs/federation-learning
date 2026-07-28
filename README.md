# FederationLearningPlatform

Generated from Medol CodegenModel for Axon Framework 5.1.1.

## Requirements

- Java 21
- Docker with Compose support

## Run

Run a backend module from this generated multi-module root. Each deployment module owns its own `docker-compose.yml`:

```bash
cp <module-name>/.env.example <module-name>/.env
./mvnw -pl <module-name> -am spring-boot:run
```

Health endpoint: `http://localhost:8080/actuator/health`

OpenAPI endpoints:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Default ports:

- Application: `8080`; override with `SERVER_PORT`
- PostgreSQL host port: `5432`; override with `DB_PORT` in this module's `.env`
- PostgreSQL database: `federation_learning_platform_parent`; override the full connection with `DB_URL`
- UmaDB host port: `50051`; override with `UMADB_PORT` in this module's `.env`
- Axon Server UI: `http://localhost:8024`; override with `AXON_SERVER_HTTP_PORT`
- Axon Server gRPC: `localhost:8124`; override with `AXON_SERVER_SERVERS`

## Event Storage Mode

The default event storage is Axon Server, which supports multiple Axon event tags per event.

```bash
./mvnw -pl <module-name> -am spring-boot:run
```

Use the in-memory event store for local experiments or tests that should not connect to Axon Server:

```bash
MEDOL_AXON_EVENT_STORAGE=inmemory AXON_SERVER_ENABLED=false ./mvnw -pl <module-name> -am spring-boot:run
```

Use the generated UmaDB DCB event store adapter from the `axon-event-storage-umadb` module with this deployment module's `.env` file:

```bash
cp <module-name>/.env.example <module-name>/.env
docker compose -f <module-name>/docker-compose.yml up -d postgres umadb
./mvnw -pl <module-name> -am spring-boot:run
```

To run the generated application as a container instead of `spring-boot:run`:

```bash
docker compose -f <module-name>/docker-compose.yml up -d <module-name>
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

## Build

```bash
./mvnw -pl <module-name> -am clean verify
```

## Container Image

Build a Docker image directly from Maven:

```bash
./mvnw -pl axon-event-storage-umadb -DskipTests install
./mvnw -pl <module-name> jib:dockerBuild
```

The generated image is `medol/<module-name>:0.0.1-SNAPSHOT`.
The container disables Spring Boot docker-compose integration; pass `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for the runtime database.

Root package: `tech.medo`

Generated command-side slices use Axon 5 event-sourced entities, explicit event tags, and composite event criteria derived from Medol slice tags. Concept states generate Kotlin enums, and `Concept.State` fields are updated from matching `stateChange` definitions. Read models generate JPA entities, repositories, REST resources, and event-handling projectors. Add production projection-table definitions under `src/main/resources/db/migration` when replacing Hibernate's generated schema.
