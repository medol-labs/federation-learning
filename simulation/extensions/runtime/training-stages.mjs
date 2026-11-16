import {createHash} from 'node:crypto';
import {checkCases, checkApproval} from '../../src/cases.mjs';
import {validateBaselineManifest, validateDataPlan} from '../../src/data-plan.mjs';
import {createTrainingEnvironment} from '../adapters/training-environment.mjs';
import {executeCases, validateDataManifest} from '../../src/execute-cases.mjs';
import {prepareData} from '../../src/prepare-data.mjs';

export function trainingArguments(environment, scenario, requireParticipants = true) {
  if (environment.status !== 'ready' || !environment.deploymentId || !environment.environment
    || !environment.kubeconfigFile || !environment.endpoints?.platform || !environment.endpoints?.support) {
    throw new Error('A ready deployment with kubeconfig and platform/support endpoints is required');
  }
  if (!['k3d', 'kubernetes', 'k3s'].includes(environment.target)) throw new Error('This project training adapter requires Kubernetes');
  const input = scenario.arguments || [];
  if (requireParticipants && !environment.participantDeployments?.length) throw new Error('Deploy participant slots before preparing or testing training data');
  if (!Array.isArray(input) || input.some(v => typeof v !== 'string')) throw new Error('Scenario arguments must be a string array');
  // Connection and lifecycle flags always come last and cannot be overridden by the scenario.
  return [...input, '--environment', environment.environment, '--kubeconfig-file', environment.kubeconfigFile,
    '--namespace', environment.namespace || 'federation-learning-platform',
    '--cluster-name', environment.clusterName || 'existing',
    '--platform-url', environment.endpoints.platform, '--support-url', environment.endpoints.support,
    ...(environment.endpoints.gateway ? ['--gateway-url', environment.endpoints.gateway] : []),
    ...(environment.imagePrefix ? ['--app-image-prefix', environment.imagePrefix, '--image-source', 'registry'] : []),
    ...(environment.imageVersion ? ['--image-version', environment.imageVersion] : []),
    '--participant-slots', String(environment.participantDeployments?.length || 0),
    '--skip-cluster', 'true', '--skip-apply', 'true', '--recreate', 'false',
    '--rebuild-app-images', 'false', '--sync-default-kubeconfig', 'false',
    '--dry-run', 'false', '--skip-runtime-agent-data', 'false', '--skip-training-job', 'false', '--wait-for-training'];
}

export async function enrollParticipants(environment, scenario, baseline, create = createTrainingEnvironment) {
  validateBaselineManifest(baseline, environment);
  const adapter = await create([...trainingArguments(environment, scenario), '--skip-admin-setup', 'true', '--skip-dictionary', 'true', '--skip-runtime-agent-data', 'true', '--skip-training-job', 'true']);
  try {
    if (!Number.isInteger(adapter.participantCount) || adapter.participantCount < 1 || adapter.participantCount > environment.participantDeployments.length) throw new Error('Participant count exceeds deployed slots or is invalid');
    await adapter.validateConnection();
    await adapter.waitReady();
    await adapter.verifyBaseline();
    for (let slot = 1; slot <= adapter.participantCount; slot++) await adapter.enrollParticipant(slot);
    const {participants} = adapter.dataSnapshot();
    if (participants.length !== adapter.participantCount || participants.some(item => !item.runtimeId || !item.runtimeAgentEndpoint)
      || new Set(participants.map(item => item.runtimeId)).size !== participants.length) throw new Error('Participant enrollment is incomplete');
    return {schemaVersion: 1, mode: 'enrollment', status: 'ready', environment: environment.environment, deploymentId: environment.deploymentId, participants};
  } finally { await adapter.cleanup(); }
}

export function trainingDataPlan(scenario, plan) {
  if (!scenario.dataPlan) throw new Error('Scenario dataPlan is required');
  const dataPlan = {...scenario.dataPlan, mode: 'case', caseIds: plan.cases.map(c => c.id),
    fulfillments: Object.fromEntries(plan.cases.map(c => [c.id,
      Object.fromEntries(c.dataRequirements.map(r => [r.id, scenario.dataRequirements?.[r.id]]))]))};
  validateDataPlan(dataPlan, {casePlan: plan});
  const operations = {PrepareParticipants: 'command', CheckParticipantData: 'wait'};
  if (!dataPlan.steps.some(step => step.kind === 'wait')) throw new Error('Data plan requires readiness wait');
  for (const step of dataPlan.steps) {
    if (!Object.hasOwn(operations, step.operation) || operations[step.operation] !== step.kind) throw new Error('Unsupported data operation');
  }
  return dataPlan;
}

export async function prepareCaseData(environment, scenario, baseline, plan, create = createTrainingEnvironment) {
  validateBaselineManifest(baseline, environment);
  checkCases(plan);
  checkApproval(plan);
  const dataPlan = trainingDataPlan(scenario, plan);
  const adapter = await create([...trainingArguments(environment, scenario), '--skip-admin-setup', 'true', '--skip-dictionary', 'true']);
  try {
    if (!Number.isInteger(adapter.participantCount) || adapter.participantCount < 1) throw new Error('A positive participant count is required');
    if (adapter.participantCount > environment.participantDeployments.length) throw new Error('Participant count exceeds deployed slots');
    await adapter.validateConnection();
    await adapter.waitReady();
    await adapter.verifyBaseline();
    return await prepareData('case', dataPlan, environment, {casePlan: plan, baseline, adapter: {operations: {
      PrepareParticipants: {kind: 'command', async execute() {
        for (let slot = 1; slot <= adapter.participantCount; slot++) await adapter.prepareParticipant(slot);
        return adapter.dataSnapshot();
      }},
      CheckParticipantData: {kind: 'wait', async execute() {
        const snapshot = adapter.dataSnapshot();
        if (snapshot.participants?.length !== adapter.participantCount || !snapshot.trainingRunKey
          || snapshot.participants.some(p => !p.runtimeAgentId || !p.runtimeId || !p.datasetId || !p.trainingRunConfigurationId)) {
          throw new Error('Prepared participant snapshot is incomplete');
        }
        return {ready: true, snapshot};
      }}
    }}});
  } finally { await adapter.cleanup(); }
}

export async function testTraining(environment, scenario, data, plan, record, create = createTrainingEnvironment) {
  validateDataManifest(data, environment);
  if (data.mode !== 'case' || data.casePlanFingerprint !== createHash('sha256').update(JSON.stringify(plan)).digest('hex')) throw new Error('Case data must match the approved test plan');
  const adapter = await create([...trainingArguments(environment, scenario), '--skip-admin-setup', 'true', '--skip-dictionary', 'true']);
  try {
    const operations = {
      SubmitTraining: {type: 'command', async execute() {
        await adapter.submitTraining();
        return {status: 'ok', body: adapter.snapshot()};
      }},
      VerifyTraining: {type: 'query', async execute() {
        await adapter.verifyTraining();
        return {status: 'ok', body: {verified: true, ...adapter.snapshot()}};
      }},
      ...(adapter.testOperations || {})
    };
    return await executeCases(plan, environment, data, {
      capabilities: {steps: ['command', 'query'], assertions: ['query']},
      validatePlan(plan) {
        for (const item of plan.cases) {
          for (const step of item.steps) {
            const operation = operations[step.command || step.query];
            if (!operation || operation.type !== step.type) throw new Error('Unsupported project training action');
          }
        }
      },
      async validateEnvironment() { await adapter.validateConnection(); await adapter.waitReady(); },
      async validateData() { await adapter.restoreData(data.records[0]); },
      async execute(step, context) {
        return operations[step.command || step.query].execute(step, context);
      }
    }, {record});
  } finally { await adapter.cleanup(); }
}
