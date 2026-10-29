# Federation Learning Local Startup

Run commands below from the repository root. Applications run on the host;
only PostgreSQL and UmaDB run in Docker. No application images, APISIX,
Kubernetes cluster, or Registry are required for management-page development.

## Prerequisites

- Java 21, Docker with Compose v2, Node.js and pnpm (see packageManager in the frontend package.json).
- Free ports: 5432, 50051, 8080, 8081, 8082, 5173, and 5174.
- Stop other stacks or processes using those ports before starting.

## Prepare

```bash
cp -n operations/local/.env-example operations/local/.env
```

Set POSTGRES_PASSWORD in the untracked .env. This file is sourced as Bash by
start.sh: use shell-compatible KEY=value assignments and quote values containing
spaces or shell metacharacters. Do not commit real credentials.

Optional local-development settings in that file:

```dotenv
MEDOL_SECURITY_INTERNAL_TOKEN=local-dev-internal-token
MEDOL_SECURITY_ADMIN_BOOTSTRAP_ENABLED=true
MEDOL_SECURITY_ADMIN_BOOTSTRAP_SETUP_TOKEN=local-admin-setup
```

These development tokens are not production credentials. The internal token
must match across the services and initialization scripts. Existing IAM users
are retained in PostgreSQL; setup-admin is only for initial administrator creation.

## Start Infrastructure and Compile

```bash
bash operations/local/start.sh infra
docker compose -f operations/local/docker-compose.yml --env-file operations/local/.env ps
bash operations/local/start.sh build
(cd federation-learning-console && pnpm install)
(cd federation-learning-participant-console && pnpm install)
```

Wait until PostgreSQL is healthy and UmaDB is running before starting applications.
Build installs shared Maven dependencies and compiles tests without executing them.
Do not ignore compilation errors or use stale installed dependencies.

## Start Applications

Before starting the agent, prepare its scoped synchronization identity:

```bash
cp -n operations/local/runtime-agent.local.env-example operations/local/runtime-agent.local.env
```

Set RUNTIME_AGENT_ID and RUNTIME_AGENT_ORGANIZATION_ID to the actual agent and
organization UUIDs registered on the platform. Do not create random replacements.
The launcher defaults synchronization sources to platform on 8081 and dictionary
maintenance on support on 8080. Per-service overrides may replace those URLs.
Start support and platform before the agent. A missing source URL causes
`requires medol.sync.source-base-url`; missing IDs cause required-parameter errors.
For management-page-only work, MEDOL_SYNC_ENABLED=false temporarily disables
synchronization, but synchronized dropdowns will not receive new data.

Run each command in a separate terminal from the repository root:

```bash
bash operations/local/start.sh support
bash operations/local/start.sh platform
bash operations/local/start.sh runtime-agent
bash operations/local/start.sh console
bash operations/local/start.sh participant-console
```

| Application | URL |
| --- | --- |
| Platform console | http://localhost:5173 |
| Participant console | http://localhost:5174 |
| Support | http://localhost:8080 |
| Platform | http://localhost:8081 |
| Runtime Agent | http://localhost:8082 |

The launcher overrides generated backend default ports and points service calls
at the host. The platform console authenticates against support; the participant
console authenticates against the agent. Their users and tokens are independent.
Use the setup token above when creating the initial administrator in each console.
Existing frontend .env files are still loaded by Vite; inspect any unrelated
settings that affect login or system selection. Launcher-exported values take precedence.

To override a backend's local business configuration, create an ignored
operations/local/support.local.env, platform.local.env, or runtime-agent.local.env.
These files are sourced after launcher defaults. Keep the frontend URLs aligned
if overriding backend ports. After changing backend source, compile/install
affected dependencies again and restart the process; hot reload is not promised.

## Training Is a Separate Step

This module starts management services, not a complete training environment.
Before submitting training, prepare participant datasets, Runtime Engine image/profile,
engine startup configuration, platform aggregation endpoint, and file/artifact storage.
The agent runs on the host, so host dataset paths can be used; containers launched
for training still need explicit dataset and output mounts. Business-specific
engine settings belong in the per-service local.env overrides, not the generator.

## Stop and Preserve Data

Stop application terminals with Ctrl+C, then:

```bash
docker compose -f operations/local/docker-compose.yml --env-file operations/local/.env down
```

Do not add -v unless intentionally deleting all local PostgreSQL and UmaDB data.
Database initialization scripts execute only for new volumes. Changing the password
or initialization SQL in .env/source does not update an existing database volume.
