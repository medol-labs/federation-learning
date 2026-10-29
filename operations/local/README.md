# Local Host Development

Only infrastructure runs in Docker. Run backend and frontend source code on the host.
This is separate from operations/docker-compose/local, which containerizes applications too.

## Start infrastructure

```bash
cd operations/local
cp -n .env-example .env
# Set POSTGRES_PASSWORD and check published ports in .env.
docker compose --env-file .env config --quiet
docker compose --env-file .env up -d
docker compose ps
```

Ports bind to loopback only. Do not run another stack on the same ports.
PostgreSQL initialization scripts execute only for a new data volume.

## Run applications

Use the project-owned host launcher when provided. Otherwise run Maven Wrapper
and the frontend development server from their source directories.
Disable Spring Docker Compose integration with SPRING_DOCKER_COMPOSE_ENABLED=false.
Use localhost database/event-store addresses and explicit SERVER_PORT values.
A Compose .env file is not automatically loaded into host application processes.

| Backend | Host port |
| --- | --- |
| federation-learning-platform | 8081 |
| federation-learning-runtime-agent | 8082 |
| federation-learning-support | 8080 |

## Stop and diagnose

```bash
docker compose logs --tail=100
docker compose down
```

down preserves data. down -v permanently deletes this stack's database and event volumes.
Training runtimes and other business-specific processes require project-owned configuration.
