import {readFileSync, writeFileSync, existsSync, mkdirSync} from 'node:fs';
import {resolve} from 'node:path';
import {parseEnv} from 'node:util';
import {execFileSync} from 'node:child_process';
import {createHash, randomUUID} from 'node:crypto';
import {createComposeDeployment} from '../../src/runtime/compose-deployment.mjs';
import {executeDeployment} from '../../src/runtime/deployment.mjs';
import {prepareTestCredentials} from '../../src/runtime/test-credentials.mjs';
import {prepareWithoutFixtures} from '../../src/workflow-tasks.mjs';

import {bootstrapParticipant} from './participant-bootstrap.mjs';

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
  const init = (target, participant, trainingRunKey) => {
    const output = resolve(directory, `seed-${target}-${randomUUID()}.json`);
    execFileSync(process.execPath, ['scripts/init-training-prerequisites.mjs', '--target', target, '--scenario', 'csv',
      '--participant-key', participant.key, '--training-run-key', trainingRunKey, '--minimum-nodes-per-round', String(environment.participantDeployments.length),
      '--platform-url', endpoints.platform, '--support-url', endpoints.support, '--runtime-agent-url', participant.endpoint,
      '--runtime-environment-type', 'DOCKER_COMPOSE', '--agent-install-mode', 'MANUAL_GUIDED', '--endpoint-scope', 'PRIVATE',
      '--runtime-engine-image', environment.runtimeEngineImage, '--dataset-path', 'simulation/extensions/fixtures/test.csv',
      '--runtime-dataset-path', '/workspace/datasets/test.csv', '--create-job', 'false',
      '--register-runtime-infrastructure', String(target === 'platform'), '--skip-admin-setup',
      '--output-summary', output, '--timeout', '600000', '--poll-interval', '1000'],
    {cwd: root, env: {...env, FL_API_TOKEN: '', FL_PLATFORM_API_TOKEN: '', FL_SUPPORT_API_TOKEN: '', FL_RUNTIME_AGENT_API_TOKEN: ''}, stdio: 'inherit', timeout: 900000});
    return JSON.parse(readFileSync(output, 'utf8'));
  };
  return {env, headers, init};
}

export async function prepare({environment, baseline, cases}, {root, directory, runId}, connect = client) {
  const ready = prepareWithoutFixtures(environment, baseline, cases);
  if (ready) return ready;
  const api = connect(environment, root, directory), participants = [], trainingRunKey = `compose-${runId}`;
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
    const endpoint = await bootstrapParticipant({platform:environment.endpoints.platform, participant:slot.endpoint,
      runtimeAgentId:seed.runtimeAgentId, runtimeInfrastructureId:seed.runtimeInfrastructureId, timeoutMs:600000}, api.headers);
    api.init('runtime-agent', slot, trainingRunKey);
    participants.push({...seed, slot: slot.slot, participantKey: slot.key, runtimeId: endpoint.runtimeId, runtimeAgentEndpoint: endpoint.runtimeAgentEndpoint, runtimeAgentUrl: slot.endpoint});
  }
  return {schemaVersion: 1, mode: 'case', status: 'ready', environment: environment.environment, deploymentId: environment.deploymentId,
    casePlanFingerprint: hash(cases), caseIds: cases.cases.map(c => c.id), records: [{trainingRunKey, participants}]};
}


