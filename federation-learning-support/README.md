# FederationLearningSupport

Generated from Medol CodegenModel for Axon Framework 5.1.1.

## Requirements

- Java 21
- Docker with Compose support

## Run

Run this module from the generated multi-module root so Maven can include the sibling `shared-kernel` and `axon-event-storage-umadb` modules in the reactor. Spring Boot uses this module's own `docker-compose.yml`:

```bash
cd ..
cp federation-learning-support/.env.example federation-learning-support/.env
./mvnw -pl federation-learning-support -am spring-boot:run
```

Health endpoint: `http://localhost:8080/actuator/health`

OpenAPI endpoints:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Default ports:

- Application: `8080`; override with `SERVER_PORT`
- PostgreSQL host port: `5432`; override with `DB_PORT` in this module's `.env`
- PostgreSQL database: `federation_learning_support`; override the full connection with `DB_URL`
- UmaDB host port: `50051`; override with `UMADB_PORT` in this module's `.env`
- Axon Server UI: `http://localhost:8024`; override with `AXON_SERVER_HTTP_PORT`
- Axon Server gRPC: `localhost:8124`; override with `AXON_SERVER_SERVERS`

## Event Storage Mode

The default event storage is Axon Server, which supports multiple Axon event tags per event.

```bash
cd ..
./mvnw -pl federation-learning-support -am spring-boot:run
```

Use the in-memory event store for local experiments or tests that should not connect to Axon Server:

```bash
cd ..
MEDOL_AXON_EVENT_STORAGE=inmemory AXON_SERVER_ENABLED=false ./mvnw -pl federation-learning-support -am spring-boot:run
```

Use the generated UmaDB DCB event store adapter from the `axon-event-storage-umadb` module with this deployment module's `.env` file:

```bash
cd ..
cp federation-learning-support/.env.example federation-learning-support/.env
docker compose -f federation-learning-support/docker-compose.yml up -d postgres umadb
./mvnw -pl federation-learning-support -am spring-boot:run
```

To run the generated application as a container instead of `spring-boot:run`:

```bash
cd ..
docker compose -f federation-learning-support/docker-compose.yml up -d federation-learning-support
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
cd ..
./mvnw -pl federation-learning-support -am clean verify
```

## Container Image

Build a Docker image directly from Maven:

```bash
cd ..
./mvnw -pl axon-event-storage-umadb -DskipTests install
./mvnw -pl federation-learning-support jib:dockerBuild
```

The generated image is `medol/federation-learning-support:0.0.1-SNAPSHOT` and exposes port `8080`.
The container disables Spring Boot docker-compose integration; pass `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for the runtime database.

Root package: `tech.medo`

Generated command-side slices use Axon 5 event-sourced entities, explicit event tags, and composite event criteria derived from Medol slice tags. Concept states generate Kotlin enums, and `Concept.State` fields are updated from matching `stateChange` definitions. Read models generate JPA entities, repositories, REST resources, and event-handling projectors. Add production projection-table definitions under `src/main/resources/db/migration` when replacing Hibernate's generated schema.
