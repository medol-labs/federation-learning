# Medol Simulation Service

Generated service for executing business flows against a running business system.

## Start

```bash
npm start
```

The service has no package dependencies beyond Node.js 20+.

Configure the target business system:

```bash
BUSINESS_BASE_URL=http://localhost:8080 npm start
```

## Execute A Scenario

```bash
curl -sS -X POST http://localhost:3199/simulations/register-organization-to-reactivate-organization/run \
  -H 'content-type: application/json' \
  -d '{"seed":1001}'
```

Or from the command line:

```bash
npm run simulate -- register-organization-to-reactivate-organization --seed 1001
```

## Event Observation

Command steps call the business system through HTTP command endpoints derived from the generated Axon 5 resources.

Automatic steps are not called directly. The service waits for their expected events. To enforce event assertions, either post events back to this service:

```bash
curl -sS -X POST http://localhost:3199/events \
  -H 'content-type: application/json' \
  -d '{"eventName":"OrganizationRegistered","payload":{"organizationId":"..."}}'
```

or set `EVENT_OBSERVER_URL` to an event observer service exposing `GET /events?eventName=<name>`.

Set `STRICT_EVENTS=true` when missing expected events should fail the simulation.
Set `WAIT_FOR_EVENTS=true` when non-strict simulations should still wait for posted or observed events.

## API

- `GET /health`
- `GET /scenarios`
- `GET /scenarios/:id`
- `POST /simulations`
- `POST /simulations/:id/run`
- `GET /events`
- `POST /events`

## Generated Scenarios

- register-organization-to-reactivate-organization: Register Organization To Reactivate Organization (4 steps)
- register-organization-to-deploy-runtime-agent: Register Organization To Deploy Runtime Agent (8 steps)
- bind-user-account-to-organization-flow: Bind User Account To Organization Flow (1 steps)
- create-federation-to-reactivate-federation: Create Federation To Reactivate Federation (4 steps)
- create-federation-to-remove-participant: Create Federation To Remove Participant (5 steps)
- create-federation-to-reject-participant: Create Federation To Reject Participant (3 steps)
- create-federation-to-revoke-participant-invitation: Create Federation To Revoke Participant Invitation (3 steps)
- invite-participant-to-remove-participant: Invite Participant To Remove Participant (4 steps)
- invite-participant-to-reject-participant: Invite Participant To Reject Participant (2 steps)
- invite-participant-to-revoke-participant-invitation: Invite Participant To Revoke Participant Invitation (2 steps)
- register-runtime-infrastructure-package-flow: Register Runtime Infrastructure Package Flow (1 steps)
- create-runtime-installation-plan-to-detect-runtime-capabilities: Create Runtime Installation Plan To Detect Runtime Capabilities (8 steps)
- create-runtime-installation-plan-to-revoke-runtime-identity: Create Runtime Installation Plan To Revoke Runtime Identity (8 steps)
- plan-runtime-infrastructure-to-detect-runtime-capabilities: Plan Runtime Infrastructure To Detect Runtime Capabilities (7 steps)
- plan-runtime-infrastructure-to-revoke-runtime-identity: Plan Runtime Infrastructure To Revoke Runtime Identity (7 steps)
- define-feature-schema-to-retire-feature-schema: Define Feature Schema To Retire Feature Schema (4 steps)
- define-feature-schema-to-supersede-feature-schema-version: Define Feature Schema To Supersede Feature Schema Version (3 steps)
- define-feature-schema-to-mark-current-recommended-feature-schema-version: Define Feature Schema To Mark Current Recommended Feature Schema Version (3 steps)
- define-feature-schema-to-record-runtime-dataset-metadata: Define Feature Schema To Record Runtime Dataset Metadata (7 steps)
- define-feature-schema-to-reject-dataset-for-training: Define Feature Schema To Reject Dataset For Training (8 steps)
- define-feature-schema-to-approve-dataset-for-training: Define Feature Schema To Approve Dataset For Training (8 steps)
- define-feature-schema-to-retry-dataset-contract-validation: Define Feature Schema To Retry Dataset Contract Validation (8 steps)
- define-feature-schema-to-record-runtime-dataset-reprofiled-metadata: Define Feature Schema To Record Runtime Dataset Reprofiled Metadata (8 steps)
- define-feature-schema-to-record-runtime-dataset-metadata-2: Define Feature Schema To Record Runtime Dataset Metadata (8 steps)
- define-feature-schema-to-validate-dataset-contract: Define Feature Schema To Validate Dataset Contract (8 steps)
- define-feature-schema-to-validate-dataset-contract-2: Define Feature Schema To Validate Dataset Contract (8 steps)
- define-feature-schema-to-validate-dataset-contract-3: Define Feature Schema To Validate Dataset Contract (8 steps)
- define-feature-schema-to-reprofile-agent-dataset: Define Feature Schema To Reprofile Agent Dataset (8 steps)
- record-runtime-dataset-metadata-flow: Record Runtime Dataset Metadata Flow (1 steps)
- register-model-artifact-to-download-model-artifact: Register Model Artifact To Download Model Artifact (2 steps)
- register-runtime-engine-profile-flow: Register Runtime Engine Profile Flow (1 steps)
- define-training-run-configuration-to-update-training-run-configuration: Define Training Run Configuration To Update Training Run Configuration (2 steps)
- define-training-run-configuration-to-lock-training-run-configuration: Define Training Run Configuration To Lock Training Run Configuration (4 steps)
- define-training-run-configuration-to-receive-participant-execution-plan: Define Training Run Configuration To Receive Participant Execution Plan (8 steps)
- define-training-run-configuration-to-resume-training-job: Define Training Run Configuration To Resume Training Job (7 steps)
- define-training-run-configuration-to-cancel-training-job: Define Training Run Configuration To Cancel Training Job (6 steps)
- define-training-run-configuration-to-prepare-homomorphic-encryption-context: Define Training Run Configuration To Prepare Homomorphic Encryption Context (8 steps)
- define-training-run-configuration-to-fail-secure-aggregation-session: Define Training Run Configuration To Fail Secure Aggregation Session (7 steps)
- define-training-run-configuration-to-retry-training-round-participant-selection: Define Training Run Configuration To Retry Training Round Participant Selection (5 steps)
- create-training-job-to-lock-training-run-configuration: Create Training Job To Lock Training Run Configuration (3 steps)
- create-training-job-to-accept-execution-plan: Create Training Job To Accept Execution Plan (8 steps)
- create-training-job-to-resume-training-job: Create Training Job To Resume Training Job (6 steps)
- create-training-job-to-cancel-training-job: Create Training Job To Cancel Training Job (5 steps)
- create-training-job-to-start-training-round: Create Training Job To Start Training Round (8 steps)
- create-training-job-to-start-training-round-2: Create Training Job To Start Training Round (8 steps)
- create-training-job-to-start-training-round-3: Create Training Job To Start Training Round (8 steps)
- create-training-job-to-fail-secure-aggregation-session: Create Training Job To Fail Secure Aggregation Session (6 steps)
- create-training-job-to-retry-training-round-participant-selection: Create Training Job To Retry Training Round Participant Selection (4 steps)
- select-training-round-participants-to-observe-runtime-engine-job: Select Training Round Participants To Observe Runtime Engine Job (8 steps)
- select-training-round-participants-to-observe-runtime-engine-job-2: Select Training Round Participants To Observe Runtime Engine Job (8 steps)
- select-training-round-participants-to-fail-training-round: Select Training Round Participants To Fail Training Round (8 steps)
- select-training-round-participants-to-release-runtime-engine-job-after-start-failure: Select Training Round Participants To Release Runtime Engine Job After Start Failure (8 steps)
- select-training-round-participants-to-retry-round-execution-after-start-failure: Select Training Round Participants To Retry Round Execution After Start Failure (8 steps)
- select-training-round-participants-to-retry-round-execution-after-start-failure-2: Select Training Round Participants To Retry Round Execution After Start Failure (8 steps)
- select-training-round-participants-to-resume-training-job: Select Training Round Participants To Resume Training Job (4 steps)
- select-training-round-participants-to-cancel-training-job: Select Training Round Participants To Cancel Training Job (3 steps)
- select-training-round-participants-to-dispatch-participant-execution-plan: Select Training Round Participants To Dispatch Participant Execution Plan (8 steps)
- select-training-round-participants-to-resume-training-job-2: Select Training Round Participants To Resume Training Job (8 steps)
- select-training-round-participants-to-cancel-training-job-2: Select Training Round Participants To Cancel Training Job (7 steps)
- select-training-round-participants-to-fail-secure-aggregation-session: Select Training Round Participants To Fail Secure Aggregation Session (4 steps)
- select-training-round-participants-to-retry-training-round-participant-selection: Select Training Round Participants To Retry Training Round Participant Selection (2 steps)
- generate-participant-execution-plan-to-submit-agent-local-model-update: Generate Participant Execution Plan To Submit Agent Local Model Update (8 steps)
- generate-participant-execution-plan-to-submit-agent-local-model-update-2: Generate Participant Execution Plan To Submit Agent Local Model Update (8 steps)
- generate-participant-execution-plan-to-fail-training-round: Generate Participant Execution Plan To Fail Training Round (8 steps)
- generate-participant-execution-plan-to-release-runtime-engine-job-after-failure: Generate Participant Execution Plan To Release Runtime Engine Job After Failure (8 steps)
- generate-participant-execution-plan-to-retry-round-execution-after-runtime-failure: Generate Participant Execution Plan To Retry Round Execution After Runtime Failure (8 steps)
- generate-participant-execution-plan-to-retry-round-execution-after-runtime-failure-2: Generate Participant Execution Plan To Retry Round Execution After Runtime Failure (8 steps)
- generate-participant-execution-plan-to-fail-training-round-2: Generate Participant Execution Plan To Fail Training Round (6 steps)
- generate-participant-execution-plan-to-release-runtime-engine-job-after-start-failure: Generate Participant Execution Plan To Release Runtime Engine Job After Start Failure (6 steps)
- generate-participant-execution-plan-to-fail-training-round-3: Generate Participant Execution Plan To Fail Training Round (7 steps)
- generate-participant-execution-plan-to-release-runtime-engine-job-after-retry-failure: Generate Participant Execution Plan To Release Runtime Engine Job After Retry Failure (7 steps)
- record-personalized-model-result-to-download-model-artifact: Record Personalized Model Result To Download Model Artifact (3 steps)
- detect-runtime-agent-offline-to-mark-runtime-agent-recovered: Detect Runtime Agent Offline To Mark Runtime Agent Recovered (2 steps)
- detect-runtime-node-resource-pressure-to-resolve-training-alert: Detect Runtime Node Resource Pressure To Resolve Training Alert (4 steps)
- record-runtime-node-inventory-flow: Record Runtime Node Inventory Flow (1 steps)
- record-runtime-node-resource-telemetry-flow: Record Runtime Node Resource Telemetry Flow (1 steps)
- raise-training-alert-to-resolve-training-alert: Raise Training Alert To Resolve Training Alert (3 steps)
- activate-runtime-identity-to-detect-runtime-capabilities: Activate Runtime Identity To Detect Runtime Capabilities (2 steps)
- activate-runtime-identity-to-revoke-runtime-identity: Activate Runtime Identity To Revoke Runtime Identity (2 steps)
- detect-runtime-capabilities-flow: Detect Runtime Capabilities Flow (1 steps)

The model-first flow is:

```text
codegen-model.json
  -> SimulationModel
  -> Simulation service
  -> HTTP command calls to business system
  -> optional event observation
```
