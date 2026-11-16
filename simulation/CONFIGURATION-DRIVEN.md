# Configuration-Driven Execution

Fixture IDs are labels, never execution switches. A scenario declares `fixtures`
with source, SHA-256, optional workspace-local copy and explicit destinations.
The generated `src/runtime/fixtures.mjs` validates all inputs before mutations,
checks selected cluster nodes, and verifies the transferred bytes.

The currently implemented transport is `k3d-node-file`. It requires explicit
kubeconfig, cluster name, node selector and destination path. A Kubernetes target
alone does not imply K3d: real nodes must belong to the named K3d cluster.
Other transports are unsupported and fail explicitly; no fallback copies data
to arbitrary hosts. Runtime data does not belong in generator templates.

Project scenario `dataPlan` is executed by the generated `prepareData` engine.
`dataRequirements` maps reviewed case requirements to plan step IDs. Missing
mappings or unsupported operations fail before participant preparation. The project
registers PrepareParticipants and CheckParticipantData; these operations remain
business-specific, not generator vocabulary.

Test cases select registered operations and assertions. They are no longer
restricted to a two-step submit/verify sequence. Complex business checks remain
project implementations; additional operations can be registered through the
project adapter's testOperations. Editing approved cases still invalidates approval.

Model-generated inputs use explicit mappings, specification values or examples/
defaults. Fields explicitly marked generated can use generated values. Other
fields remain Unresolved; names and Id suffixes never establish identity links.
The legacy flow runner rejects unresolved manual inputs before sending commands,
and automatic steps only observe events rather than invent command inputs.
Regenerate simulation-model.json to replace previously inferred metadata.

No default admin password or internal token is provided by the training adapter.
Use the explicit private test-configuration task or configured environment secrets.

## Independent Baseline Preparation

`data/baseline-plan.json` declares operation order, HTTP health checks, timeouts
and readiness evidence. `data/baseline-adapter.json` supplies authentication paths,
credential variable names and dictionary initializer/catalog inputs. The project
HTTP adapter initializes the administrator, runs dictionary initialization, then
verifies every configured dictionary and value through the generated prepareData
engine. It does not import the training adapter or require kubeconfig/participants.
A ready Compose or Kubernetes manifest with the configured HTTP endpoints can be
used for this stage; that does not imply full training support for both targets.

Test credentials are declared in `operations/extensions/test-credentials.json`.
The generated runtime creates random named values, resolves structured $secret
references, and writes configured env/JSON outputs with private permissions.
Unmanaged/conflicting files, symlinks and concurrent initialization are rejected.
No credentials are returned in manifests. This is not a production secret manager.

## Remaining Migration

Additional deployment services and co-deployment dependencies are declared in
`operations/extensions/deployment-services.json`. The generic deployment planner
validates `requires` against explicitly selected, positive-replica instances before
creating an adapter. It never implicitly deploys dependencies or infers them from
service names. The extension is merged with the generated catalog without mutating
that catalog. CLI deployment and workflow deployment use the same catalog.

This is not a claim that the entire project workflow is infrastructure-neutral.
Participant topology now lives in operations/extensions/participant-package.json.
The generated kubernetes-instances executor handles rendering, deployment,
readiness and explicit identity injection; the project slot adapter selects the
package without embedding service names, routing or Secret mappings.
Platform deployment now uses the independent platform-deployment adapter and
generated kubernetes-deployment executor, not training-environment or K3d shell
scripts. operations/extensions/platform-deployment.json selects Operations
resources, component templates, images, configuration image targets and probes.
Its platform-runtime templates replace the old shell-generated component.
Images are pulled for the requested architecture and pinned to verified registry
digests. config.images may explicitly override references by service name,
including private mirrors for dependencies. Secrets must already exist; deployment
does not generate defaults or overwrite them with shell defaults.

The current data/test operations still use a Kubernetes-specific training adapter.
Legacy combined commands remain available but are not used by the deployment
stage. This change does not implement Docker Compose deployment or make project
hostPath/scheduling policies portable to every Kubernetes installation. Those
policies are explicit project Operations inputs that must match the target cluster.
The generated core must never acquire those project service names, business
registration rules or scheduling labels. No remote deployment is performed by
this refactoring's unit tests.
