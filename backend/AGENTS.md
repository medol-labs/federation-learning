# federation-learning-platform — AGENTS.md

This repo is the generated Axon 5 / Spring Boot backend for the Federation Learning platform. It is a multi-module Maven project with platform, support, runtime-agent, shared-kernel, and UmaDB event-store modules.

## Source Of Truth

- The domain source is `/Users/bryce/codes/medo/event-modeling/medol/examples/fl/federation-learning.medol`.
- Generated backend code is produced by `/Users/bryce/codes/medo/event-modeling/es-code-generator`.
- Do not hand-edit generated `context/...` code unless the user explicitly asks for a temporary local fix.
- If a generated behavior is wrong for all future generations, fix the MEDOL model or the generator first.

## Generated Vs Hand-Written Code

- `*/src/main/kotlin/tech/medo/context/...` is generated application/context code and may be overwritten.
- `*/src/test/kotlin/tech/medo/context/...` is generated test code and may be overwritten.
- `*/src/main/kotlin/tech/medo/infrastructure/...` is for hand-written adapters, Feign clients, persistence helpers, Docker/K3s integration, local file storage, and runtime-engine integration.
- `*/src/test/kotlin/tech/medo/infrastructure/...` is for hand-written adapter tests.
- `*/src/main/kotlin/tech/medo/domain/...` is for hand-written Spring decision override components when generated default decision logic is insufficient.
- Prefer adding Spring components in `domain/...` or adapter implementations in `infrastructure/...` over editing generated decision/processor/handler files.

## Design Rules

- User commands may contain only user-provided identifiers and inputs.
- Events should contain full snapshots required by downstream consumers and replay.
- If generated code contains random UUIDs or empty strings for real business data, treat it as a model/generator gap.
- Runtime-agent communication should go through explicit adapters and Feign clients in `infrastructure/...`.
- Adapter code that needs database data must use Spring Data JPA query methods, `JpaSpecificationExecutor`, `Specification`, or explicit repository methods to query by conditions. Do not call `findAll()` and then filter in memory unless the dataset is intentionally tiny and the user explicitly accepts it.
- Keep per-module Docker compose files aligned with generated module/service names.
- Do not commit `.env`, local volumes, secrets, `.venv`, or generated runtime artifacts.

## Validation

From this repo root:

```bash
./mvnw -q -pl federation-learning-platform -DskipTests test
./mvnw -q -pl federation-learning-runtime-agent -DskipTests test
./mvnw -q -pl federation-learning-support -DskipTests test
```

Run the narrowest module validation that covers the changed code. If Kotlin daemon issues block validation, report that explicitly and include the command that was attempted.

## Logging

- Axon flow logging is controlled by `MEDOL_AXON_FLOW_LOGGING_ENABLED` and `MEDOL_AXON_FLOW_LOG_LEVEL`.
- Hibernate SQL logging is controlled by `HIBERNATE_SQL_LOG_LEVEL` and `HIBERNATE_BIND_LOG_LEVEL`.
- Generated Logback config suppresses noisy scheduled-task and Axon `token_entry` SQL/bind logs while keeping useful request/query bind parameters.
