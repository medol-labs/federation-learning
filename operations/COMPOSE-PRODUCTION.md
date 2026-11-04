# Docker Compose Production Deployment

This runbook deploys each modeled deployment site to its own Docker host/project.
Platform and participant Compose files are isolated using networkTopology.sites,
the same deployment boundaries used by the Kubernetes/K3s outputs. It is not an HA solution.
Use Linux and Docker Compose v2. Verify
the CPU architecture and reserve memory for three JVMs, databases and any engines.

## Prepare the release

Generate operations for the selected environment with the current code generator:

```bash
gen --generator operations --generator-type docker-compose --environment prod --force
node operations/images/images.mjs package --environment prod --platform linux/amd64 --skip-archive
```

Use linux/arm64 for ARM hosts. Select immutable release tags/digests for a real
release; 0.0.1-SNAPSHOT is a development default. Record the source revision and
image archive manifests. Do not bypass provenance checks to export old images.
The application package does not include Runtime Engine profile images.

Transfer only operations/docker-compose/prod/sites/<site> and the matching site's
verified image archives to its intended host. Each site directory is self-contained,
including gateway/ and its own database initialization SQL. For offline delivery,
load every application and dependency tar with docker load and verify its sidecar
and image identity through the image import tools. For Registry delivery, publish
images first and set the exact Registry image references in the target .env.
Do not build on the production host or distribute real credentials with archives.

## Configure and start

Run from the delivered site directory on its target. The environment root is only
an index; it no longer contains a combined docker-compose.yml. In this project:

- sites/platform: platform console, platform backend, support, Postgres, UmaDB, APISIX.
- sites/participant: participant console, standalone agent, Postgres, UmaDB, APISIX.

Each project has independently scoped volumes and networks. Do not point a site's
database service at another site's volume. If migrating from the previous combined
stack, back up and restore the required databases/events explicitly; changing the
project name does not migrate data. Keep the old volumes until restoration is verified.

```bash
cp -n .env-example .env
chmod 600 .env
# Edit .env before executing the following commands.
docker compose --env-file .env config --quiet
# Connected Registry delivery only; omit for offline preloaded images:
docker compose --env-file .env pull
docker compose --env-file .env up -d --wait --wait-timeout 300
docker compose --env-file .env ps
docker compose --env-file .env logs --tail 100
```

Set a strong POSTGRES_PASSWORD, shared MEDOL_SECURITY_INTERNAL_TOKEN and a JWT
signing key of sufficient length (at least 32 random bytes for HS256). Keep the
internal token/JWT values consistent across communicating applications. Values
are visible to Docker administrators; .env permissions are not a secret vault.
Set every required remote URL in the site's .env to an address reachable from its
containers. For example, participant FEDERATION_LEARNING_PLATFORM_URL and
MEDOL_SYNC_SOURCE_BASE_URL can use https://platform.example/api/federation-learning-platform;
support/dictionary sync URLs can use https://platform.example/api/federation-learning-support.
The platform's FEDERATION_LEARNING_RUNTIME_AGENT_URL must use the participant
gateway's runtime-agent route. Verify the exact route prefixes in gateway/apisix.yaml.
Do not use remote Docker service names or localhost for these cross-host calls.
Configure TLS trust, firewall rules and shared internal authentication on both sites.
Do not paste expanded docker compose config output into logs or support tickets.

Only the gateway port is published by default (9080). Terminate HTTPS with a
reviewed reverse proxy and restrict host firewall access. Configure browser
origins to the actual HTTPS origin when cross-origin requests are necessary.
The generated frontend uses local authentication and same-gateway API routes.
Verify runtime frontend configuration in the delivered image; changing .env
cannot change values compiled into an image that has no runtime-config support.

For initial administrator creation, temporarily set
MEDOL_SECURITY_ADMIN_BOOTSTRAP_ENABLED=true and a private setup token. Recreate
affected services, complete administrator setup through the application's setup
flow, then set it back to false and recreate those services. Ordinary restart
does not apply changed container environment variables.

Project names scope networks, containers and volumes by environment. Multiple
environments on one host require different GATEWAY_PORT values. Changing the
project name can create new empty database volumes; keep it fixed for upgrades.

## Add training engines

The project owns the optional overlays in operations/docker-compose/runtime/.
Transfer the matching platform.yml or participant.yml alongside the generated
site Compose file as runtime.yml. These are separate from generated files and
must be delivered explicitly; the image packaging command does not include them.
Use the overlay on every config, pull, up, ps and down command for that project:

```bash
docker compose --env-file .env -f docker-compose.yml -f runtime.yml config --quiet
docker compose --env-file .env -f docker-compose.yml -f runtime.yml up -d --wait --wait-timeout 300
```

- Platform: set AGGREGATION_ENGINE_IMAGE to the delivered engine image and
  AGGREGATION_ENGINE_PLUGIN_PROFILE to its profile (default pytorch-vision).
  Platform calls the private aggregation-engine service.
- Participant: rebuild the runtime-agent image with prestarted mode support.
  Set PARTICIPANT_ENGINE_IMAGE to the exact image reference used by the
  RuntimeEngineProfile, including digest when the execution plan pins one.
  Set PARTICIPANT_ENGINE_PLUGIN_PROFILE to the matching image profile and
  PARTICIPANT_DATASETS_DIR to an existing absolute directory on the Docker host.
  Register dataset paths under /workspace/datasets. The agent and engine share a
  read-only dataset mount and a writable project-scoped training-work volume.
  Set PARTICIPANT_ENGINE_NODE_NAME if needed; both services use the same value.
- Deliver engine images separately from application/dependency archives.
  Verify that the chosen profiles support the selected model and aggregation
  algorithm. For encrypted jobs prepare key material and verify the encrypted
  workflow separately; these overlays do not provision encryption keys.

The participant overlay starts one engine with Compose. The agent uses HTTP to
that engine and rejects a different image in the execution plan; it does not
launch or replace containers per job. No Docker CLI/socket is required inside
the agent. Start with one training job and verify a complete round before
production use. K3s-managed runtime-agent installation is not available in a
Compose-only deployment; register the standalone agent manually.

## Upgrade, backup and rollback

Before deployment record current image digests and take a restorable backup:

```bash
docker compose --env-file .env exec -T postgres sh -c 'pg_dumpall -U "$POSTGRES_USER"' > postgres-backup.sql
```

Protect the backup as secret data. Back up UmaDB with a version-supported procedure;
for raw volume copies stop all writers and the database first. Test restoration
in a separate project before relying on a backup. Initial PostgreSQL scripts only
run when its data directory is empty; new initialization SQL is not a migration.

Load/pull the reviewed new images, update .env image references, then run up --wait
again. Check application health, login and event-store access. If rollback is
needed, restore prior image references and recreate services only when database
schema compatibility permits; restoring data may also be required. Never run
docker compose down -v as an upgrade or rollback step.

Log rotation is generated for all services. CPU/memory limits follow the operations
model. The generated HTTP health checks require wget in the delivered application
images. Unhealthy status alone does not restart a running process. Disk usage,
backups, restart alerts and capacity monitoring remain host operations tasks.
