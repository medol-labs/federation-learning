# Deploy Before Registration

An external deployment can start before its platform identity exists. It still
requires its database, event store, network and normal application dependencies.
This feature does not make the agent a standalone process without persistence.

## Waiting Deployment

Leave RUNTIME_AGENT_ID and RUNTIME_INFRASTRUCTURE_ID unset. The process starts
without dispatching bootstrap commands. Startup logs report
WAITING_FOR_CONFIGURATION. HTTP business requests return 503 with that code;
health and preflight requests continue through normal security handling.
Health UP indicates a running process, not permission to participate in training.
No enrollment credentials or identity are generated automatically.

Use a fresh, dedicated event store for a new agent. The HTTP guard is not an Axon
processor pause mechanism: do not attach an unconfigured agent to another
agent's historical event store.

## Register And Configure

Use the existing platform installation plan and infrastructure registration flow
with MANUAL_GUIDED. Platform-managed deployment remains unchanged. The current
registration API still requires an installation plan; plan-free registration and
automatic discovery/claiming are not implemented.

Read the stable runtimeAgentId and runtimeInfrastructureId from the platform
infrastructure projection. Set these deployment variables:

```dotenv
RUNTIME_AGENT_BOOTSTRAP_ENABLED=true
RUNTIME_AGENT_ID=<registered-agent-uuid>
RUNTIME_INFRASTRUCTURE_ID=<registered-infrastructure-uuid>
RUNTIME_AGENT_VERSION=<installed-version>
RUNTIME_AGENT_ENDPOINT=https://<platform-reachable-agent-address>
RUNTIME_AGENT_ENDPOINT_SCOPE=EXTERNAL
FEDERATION_LEARNING_PLATFORM_URL=https://<agent-reachable-platform-address>
MEDOL_SECURITY_INTERNAL_TOKEN=<privately-provisioned-service-credential>
RUNTIME_AGENT_INSTALL_MODE=MANUAL_GUIDED
```

Use an endpoint scope supported by the installed dictionary. Never commit real
credentials. The current integration uses the shared internal service token;
per-agent credential issuance is not introduced by this change. Replace the
development default before deploying outside an isolated development environment.

Restart the agent after updating the environment/Secret. Configuration completeness
does not prove that credentials or connectivity are valid. Existing platform
authentication and connection-reporting checks still apply.

Explicitly invoke the authenticated command:

```text
POST /runtimeagentlifecycle/loadruntimeagentbootstrapconfiguration
Content-Type: application/json

{}
```

The existing automations then load configuration, report startup, self-check and
report connectivity to the platform. No automatic startup reporter was added,
so ordinary restarts do not automatically issue duplicate bootstrap commands.
Check the platform endpoint catalog and runtime identity before submitting work.
Retain the registered identity and dedicated persistence across redeployments.
