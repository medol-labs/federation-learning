import {readFileSync, writeFileSync, existsSync, mkdirSync} from 'node:fs';
import {resolve} from 'node:path';
import {parseEnv} from 'node:util';
import {execFileSync} from 'node:child_process';
import {createHash, randomUUID} from 'node:crypto';
import {createComposeDeployment} from '../../src/runtime/compose-deployment.mjs';
import {executeDeployment} from '../../src/runtime/deployment.mjs';
import {prepareTestCredentials} from '../../src/runtime/test-credentials.mjs';
import {checkCases, checkApproval} from '../../src/cases.mjs';
import {executeCases} from '../../src/execute-cases.mjs';
import {validateBaselineManifest} from '../../src/data-plan.mjs';
import {verifyTrainingResult} from '../scenarios/verify-training.mjs';
import {verifyArtifact} from '../scenarios/verify-artifact.mjs';

const hash = value => createHash('sha256').update(JSON.stringify(value)).digest('hex');
export function secrets({config}, {root}) { return prepareTestCredentials(config, {root}); }
export async function deploy({config}, {root}) {
  const enrollment = 'simulation/compose/enrollment.private.json';
  if (existsSync(resolve(root, enrollment))) config = {...config, files: [...new Set([...config.files, enrollment])]};
  const catalog = {services: config.instances.map(item => ({name: item.service}))};
  const result = await executeDeployment(config, () => createComposeDeployment(config, {root}), {catalog});
  return {...result, participantDeployments: config.participantDeployments, runtimeEngineImage: config.runtimeEngineImage,
    composeFiles: config.files, envFile: config.envFile};
}

function client(environment, root, directory) {
  if (environment.target !== 'docker-compose' || !environment.participantDeployments?.length) throw new Error('An explicit Compose participant deployment is required');
  const env = {...process.env, ...parseEnv(readFileSync(resolve(root, environment.envFile), 'utf8'))};
  const endpoints = environment.endpoints;
  const headers = () => ({'content-type': 'application/json', 'X-MEDOL-INTERNAL-TOKEN': env.MEDOL_SECURITY_INTERNAL_TOKEN});
  const request = async (base, path, options = {}) => {
    const response = await fetch(base + path, {...options, headers: headers(), signal: AbortSignal.timeout(30000)});
    if (!response.ok) throw new Error(`HTTP ${response.status} ${path}: ${(await response.text()).slice(0, 500)}`);
    const body = await response.text(); return body ? JSON.parse(body) : {};
  };
  const page = (base, path, query = {}) => request(base, path + '?' + new URLSearchParams({...query, size: query.size || 100}));
  const wait = async (label, probe, details = () => '') => {
    const until = Date.now() + 600000;
    while (Date.now() < until) { const result = await probe(); if (result) return result; await new Promise(r => setTimeout(r, 1000)); }
    throw new Error('Timed out: ' + label + ' ' + details());
  };
  const init = (target, participant, trainingRunKey, createJob = false) => {
    const output = resolve(directory, `seed-${target}-${randomUUID()}.json`);
    execFileSync(process.execPath, ['scripts/init-training-prerequisites.mjs', '--target', target, '--scenario', 'csv',
      '--participant-key', participant.key, '--training-run-key', trainingRunKey, '--minimum-nodes-per-round', String(environment.participantDeployments.length),
      '--platform-url', endpoints.platform, '--support-url', endpoints.support, '--runtime-agent-url', participant.endpoint,
      '--runtime-environment-type', 'DOCKER_COMPOSE', '--agent-install-mode', 'MANUAL_GUIDED', '--endpoint-scope', 'PRIVATE',
      '--runtime-engine-image', environment.runtimeEngineImage, '--dataset-path', 'simulation/extensions/fixtures/test.csv',
      '--runtime-dataset-path', '/workspace/datasets/test.csv', '--create-job', String(createJob),
      '--register-runtime-infrastructure', String(target === 'platform'), '--skip-admin-setup',
      ...(createJob ? ['--training-job-key', randomUUID()] : []), '--output-summary', output, '--timeout', '600000', '--poll-interval', '1000'],
    {cwd: root, env: {...env, FL_API_TOKEN: '', FL_PLATFORM_API_TOKEN: '', FL_SUPPORT_API_TOKEN: '', FL_RUNTIME_AGENT_API_TOKEN: ''}, stdio: 'inherit', timeout: 900000});
    return JSON.parse(readFileSync(output, 'utf8'));
  };
  return {env, headers, request, page, wait, init};
}

export async function prepare({environment, baseline, cases}, {root, directory, runId}) {
  checkCases(cases); checkApproval(cases); validateBaselineManifest(baseline, environment);
  if (cases.cases.some(item => item.steps.some(step => !['SubmitTraining', 'VerifyTraining'].includes(step.command || step.query)))) throw new Error('Unsupported Compose training case');
  const api = client(environment, root, directory), participants = [], trainingRunKey = `compose-${runId}`;
  const overrideFile = resolve(root, 'simulation/compose/enrollment.private.json');
  const override = existsSync(overrideFile) ? JSON.parse(readFileSync(overrideFile)) : {services: {}};
  for (const slot of environment.participantDeployments) {
    const seed = api.init('platform', slot, trainingRunKey);
    const existing = override.services[slot.service]?.environment?.RUNTIME_AGENT_ID;
    if (existing && existing !== seed.runtimeAgentId) throw new Error('Refusing to replace participant identity');
    override.services[slot.service] = {environment: {RUNTIME_AGENT_ID: seed.runtimeAgentId,
      RUNTIME_INFRASTRUCTURE_ID: seed.runtimeInfrastructureId, RUNTIME_AGENT_ORGANIZATION_ID: seed.organizationId,
      RUNTIME_AGENT_RUNTIME_NAME: seed.runtimeName, RUNTIME_AGENT_BOOTSTRAP_ENABLED: 'true', RUNTIME_AGENT_INSTALL_MODE: 'MANUAL_GUIDED'}};
    mkdirSync(resolve(root, 'simulation/compose'), {recursive: true});
    writeFileSync(overrideFile, JSON.stringify(override), {mode: 0o600});
    execFileSync('docker', ['--context', environment.dockerContext, 'compose', '-p', environment.composeProject, '--env-file', resolve(root, environment.envFile),
      ...environment.composeFiles.flatMap(file => ['-f', resolve(root, file)]), '-f', overrideFile, 'up', '-d', '--no-deps', '--wait', '--wait-timeout', '300', slot.service],
    {env: api.env, stdio: 'inherit', timeout: 360000});
    const query = {'runtimeAgentId.equals': seed.runtimeAgentId};
    let endpoint = (await api.page(environment.endpoints.platform, '/runtimeinfrastructure/runtimeagentendpointcatalog', query)).content?.find(item => item.runtimeAgentEndpoint);
    if (!endpoint) await api.request(slot.endpoint, '/runtimeagentlifecycle/loadruntimeagentbootstrapconfiguration', {method: 'POST', body: '{}'});
    endpoint = await api.wait('participant endpoint', async () => (await api.page(environment.endpoints.platform, '/runtimeinfrastructure/runtimeagentendpointcatalog', query)).content?.find(item => item.runtimeAgentEndpoint && item.runtimeId));
    api.init('runtime-agent', slot, trainingRunKey);
    participants.push({...seed, slot: slot.slot, participantKey: slot.key, runtimeId: endpoint.runtimeId, runtimeAgentEndpoint: endpoint.runtimeAgentEndpoint, runtimeAgentUrl: slot.endpoint});
  }
  return {schemaVersion: 1, mode: 'case', status: 'ready', environment: environment.environment, deploymentId: environment.deploymentId,
    casePlanFingerprint: hash(cases), caseIds: cases.cases.map(c => c.id), records: [{trainingRunKey, participants}]};
}

export async function test({environment, data, cases}, {root, directory}) {
  const api = client(environment, root, directory), snapshot = data.records?.[0];
  let trainingJobId, evidence;
  const executor = {
    capabilities: {steps: ['command', 'query'], assertions: ['query']},
    async validatePlan(plan) {
      for (const item of plan.cases) for (const step of item.steps) if (!['SubmitTraining', 'VerifyTraining'].includes(step.command || step.query)) throw new Error('Unsupported operation');
    },
    async validateEnvironment() {
      for (const url of [environment.endpoints.support, environment.endpoints.platform, ...environment.participantDeployments.map(p => p.endpoint)]) await api.request(url, '/actuator/health');
    },
    async validateData() {
      if (snapshot.participants?.length !== environment.participantDeployments.length || new Set(snapshot.participants.map(p => p.runtimeId)).size !== snapshot.participants.length) throw new Error('Invalid participant fixture');
      const configurations = await api.page(environment.endpoints.platform, '/trainingrunconfiguration/trainingrunconfigurationcatalog',
        {'trainingRunConfigurationId.equals': snapshot.participants[0].trainingRunConfigurationId});
      if (!configurations.content?.some(item => String(item.state).toUpperCase() === 'DRAFT')) {
        throw new Error('Training configuration fixture is consumed or unavailable; run prepare-case in a new run, reusing only the baseline and plan.');
      }
      for (const p of snapshot.participants) {
        const page = await api.page(p.runtimeAgentUrl, '/dataset/datasetcapability', {'datasetId.equals': p.datasetId});
        if (!page.content?.some(item => item.datasetId === p.datasetId && item.schemaCompatible && item.labelCompatible)) throw new Error('Dataset capability is not ready');
      }
    },
    async execute(step) {
      if (step.command === 'SubmitTraining') {
        trainingJobId = api.init('training-job', environment.participantDeployments[0], snapshot.trainingRunKey, true).trainingJobId;
        return {status: 'ok', body: {trainingJobId, participants: snapshot.participants}};
      }
      await verifyTrainingResult(trainingJobId, snapshot.participants, {getPage: api.page, platformUrl: environment.endpoints.platform,
        supportUrl: environment.endpoints.support, waitForCondition: api.wait, fail: message => { throw new Error(message); }, federationMode: 'global', requestHeaders: api.headers});
      const rounds = await api.page(environment.endpoints.platform, '/traininground/trainingroundprogress', {'trainingJobId.equals': trainingJobId});
      const round = rounds.content.find(item => item.aggregatedModelId);
      const models = await api.page(environment.endpoints.platform, '/modelartifact/modelartifactcatalog', {'modelId.equals': round.aggregatedModelId});
      evidence = await verifyArtifact(models.content[0], [environment.endpoints.support, environment.endpoints.platform], api.headers);
      return {status: 'ok', body: {verified: true, trainingJobId, evidence}};
    }
  };
  const report = await executeCases(cases, environment, data, executor, {record: value => writeFileSync(resolve(directory, 'training-progress.json'), JSON.stringify(value, null, 2))});
  return {...report, trainingJobId, artifact: evidence};
}
