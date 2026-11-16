# Deploy Participants Before Registration

The project K3d workflow uses `MANUAL_GUIDED`. Simulation deploys the processes;
platform business operations register their identity, not create their workloads.
This is Federation Learning extension logic, not a generic generator policy.

## Deployment

Set `participantSlots` in `simulation/pipelines/deployment.json-example` (currently 2).
Use 0 for a platform-only deployment. Do not add a standalone participant console
to the platform `instances` list.

Each slot has a stable namespace `<platform-namespace>-p<number>` with its own
Agent, participant console, gateway, PostgreSQL and UmaDB PVCs. Before registration
the Agent reports healthy but waits for enrollment configuration. The operations
participant assets are reused, with project-owned Kustomize overlays in `.work`.
Application images are verified by the existing image-resolution pipeline.

The deployment report includes `participantDeployments`. Older platform-only
reports cannot be used for this data preparation flow; run deployment again.
Reapplying deployment preserves the separate enrollment ConfigMap and PVCs.

Worker-node capacity remains a separate environment-preparation setting.
The current business scheduling helper assigns one worker per participant, so
data preparation needs at least as many workers as participating slots. Setting
`participantSlots` does not create workers or business records.

## Data Preparation

1. Reuse a ready deployment report; initialize support authentication/dictionaries.
2. Register each organization and infrastructure with `MANUAL_GUIDED`.
3. Apply the project's node labels/taints for engine scheduling.
4. Write identity into the slot's `participant-enrollment` ConfigMap and restart
   the existing Agent. Shared authentication secrets come from the deployment.
5. Invoke `LoadRuntimeAgentBootstrapConfiguration`; wait for the Agent's actual
   connection report and platform runtime identity before preparing its dataset.

No platform-managed deployment, deployment-retry command or synthetic connection
report is issued. A slot cannot be rebound to a different infrastructure identity.

## Access

`participantAccess` in deployment configuration assigns an independent NodePort
and host port to each participant gateway. Current defaults:

| Site | Browser URL | Cluster NodePort |
| --- | --- | --- |
| Platform | http://192.168.50.2:31080 | 30080 |
| Participant 1 | http://192.168.50.2:31081 | 30081 |
| Participant 2 | http://192.168.50.2:31082 | 30082 |

The K3d fixture reserves host ports 31081-31100 mapped to NodePorts 30081-30100
on server 0. These are gateway entry points, not worker-node ports. Each gateway
routes to its own namespace's console and Agent. Deployment reports include
`consoleUrl`, `hostPort` and `nodePort` for every slot.

Increasing `participantSlots` does not create worker nodes. When increasing it,
also configure worker capacity and add the new browser origins to operations
`config/local.yml`. Changing the host or port ranges requires updating both
`participantAccess` and the K3d fixture mappings. Omit `participantAccess` for
ClusterIP-only gateways.

Existing clusters do not acquire new Docker port mappings on application apply.
Add the mappings explicitly to the existing K3d cluster, or recreate it only after
confirming data can be deleted. This change does not recreate a running cluster.

The console is at `/`; Agent, support and platform requests use same-origin gateway
routes. For browser access, include the actual browser origin in operations
`browserOrigins` before deployment. Do not use a cluster-only Service DNS name as
a browser URL. SSH tunnels may be used when K3d runs on a remote host.

The test-execution stage reuses this deployment and prepared-data report; it does
not create or register replacement Agents.
