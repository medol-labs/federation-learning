import {readFileSync, writeFileSync, mkdirSync, existsSync} from 'node:fs';
import {resolve, dirname} from 'node:path';
import {pathToFileURL} from 'node:url';
import {createTrainingEnvironment} from '../adapters/training-environment.mjs';
import {executeCases, validateDataManifest} from '../../src/execute-cases.mjs';

export function trainingArguments(environment, scenario) {
  if (environment.status !== 'ready' || !environment.deploymentId || !environment.environment
    || !environment.kubeconfigFile || !environment.endpoints?.platform || !environment.endpoints?.support) {
    throw new Error('A ready deployment with kubeconfig and platform/support endpoints is required');
  }
  if (!['k3d', 'kubernetes', 'k3s'].includes(environment.target)) throw new Error('This project training adapter requires Kubernetes');
  const input = scenario.arguments || [];
  if (!Array.isArray(input) || input.some(v => typeof v !== 'string')) throw new Error('Scenario arguments must be a string array');
  // Connection and lifecycle flags always come last and cannot be overridden by the scenario.
  return [...input, '--environment', environment.environment, '--kubeconfig-file', environment.kubeconfigFile,
    '--namespace', environment.namespace || 'federation-learning-platform',
    '--cluster-name', environment.clusterName || 'existing',
    '--platform-url', environment.endpoints.platform, '--support-url', environment.endpoints.support,
    '--skip-cluster', 'true', '--skip-apply', 'true', '--recreate', 'false',
    '--rebuild-app-images', 'false', '--sync-default-kubeconfig', 'false',
    '--dry-run', 'false', '--skip-runtime-agent-data', 'false', '--skip-training-job', 'false', '--wait-for-training'];
}

export async function prepareTrainingData(environment, scenario, create = createTrainingEnvironment) {
  const adapter = await create(trainingArguments(environment, scenario));
  try {
    if (!Number.isInteger(adapter.participantCount) || adapter.participantCount < 1) throw new Error('A positive participant count is required');
    await adapter.validateConnection();
    await adapter.waitReady();
    await adapter.bootstrap();
    for (let slot = 1; slot <= adapter.participantCount; slot++) await adapter.prepareParticipant(slot);
    const snapshot = adapter.dataSnapshot();
    if (snapshot.participants?.length !== adapter.participantCount || !snapshot.trainingRunKey
      || snapshot.participants.some(p => !p.runtimeAgentId || !p.runtimeId || !p.datasetId || !p.trainingRunConfigurationId)) {
      throw new Error('Prepared participant snapshot is incomplete');
    }
    return {schemaVersion: 1, status: 'ready', mode: 'baseline', environment: environment.environment,
      deploymentId: environment.deploymentId, records: [snapshot],
      readinessChecks: ['participant-endpoint', 'runtime-agent-health', 'participant-data-initialization']};
  } finally { await adapter.cleanup(); }
}

export async function testTraining(environment, scenario, data, plan, record, create = createTrainingEnvironment) {
  validateDataManifest(data, environment);
  const adapter = await create(trainingArguments(environment, scenario));
  try {
    return await executeCases(plan, environment, data, {
      capabilities: {steps: ['command', 'query'], assertions: ['query']},
      validatePlan(plan) {
        for (const item of plan.cases) {
          if (item.steps.length !== 2 || item.steps[0].type !== 'command' || item.steps[0].command !== 'SubmitTraining'
            || item.steps[1].type !== 'query' || item.steps[1].query !== 'VerifyTraining') throw new Error('Training case must submit once and then verify');
        }
      },
      async validateEnvironment() { await adapter.validateConnection(); await adapter.waitReady(); },
      async validateData() { await adapter.restoreData(data.records[0]); },
      async execute(step) {
        if (step.type === 'command' && step.command === 'SubmitTraining') {
          await adapter.submitTraining();
          return {status: 'ok', body: adapter.snapshot()};
        }
        if (step.type === 'query' && step.query === 'VerifyTraining') {
          await adapter.verifyTraining();
          return {status: 'ok', body: {verified: true, ...adapter.snapshot()}};
        }
        throw new Error('Unsupported project training action');
      }
    }, {record});
  } finally { await adapter.cleanup(); }
}

if (process.argv[1] && import.meta.url === pathToFileURL(resolve(process.argv[1])).href) {
  let activeReport;
  try {
    const [action, envPath, scenarioPath, dataPath, casePath, reportPath] = process.argv.slice(2);
    if (!['prepare', 'test'].includes(action) || !dataPath || (action === 'test' && (!casePath || !reportPath))) throw new Error('Usage: training-stages.mjs prepare <deployment> <scenario> <new-data> | test <deployment> <scenario> <data> <cases> <new-report>');
    const read = file => JSON.parse(readFileSync(file, 'utf8'));
    const output = action === 'prepare' ? dataPath : reportPath;
    if (existsSync(output)) throw new Error('Output already exists; choose a new path');
    mkdirSync(dirname(resolve(output)), {recursive: true});
    const environment = read(envPath), scenario = read(scenarioPath);
    if (action === 'prepare') writeFileSync(output, JSON.stringify(await prepareTrainingData(environment, scenario), null, 2), {flag: 'wx', mode: 0o600});
    else {
      const data = read(dataPath), plan = read(casePath);
      writeFileSync(output, JSON.stringify({status: 'running'}), {flag: 'wx', mode: 0o600});
      activeReport = output;
      const report = await testTraining(environment, scenario, data, plan, r => writeFileSync(output, JSON.stringify(r, null, 2)));
      if (report.status !== 'passed') process.exitCode = 1;
    }
  } catch (error) {
    if (activeReport) writeFileSync(activeReport, JSON.stringify({status: 'failed', error: error.message}, null, 2));
    console.error(error.message); process.exitCode = 1;
  }
}
