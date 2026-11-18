import {test} from 'node:test';
import assert from 'node:assert/strict';
import {readFileSync} from 'node:fs';
import {createHash} from 'node:crypto';
import {approveCases} from '../../src/cases.mjs';
import {executeCases} from '../../src/execute-cases.mjs';
import {createHttpExecutor} from '../../src/http-case-executor.mjs';

async function run(count, rounds, brokenLink = false) {
  const name = count === 20 ? 'twenty-participant-training' : 'training';
  const plan = approveCases(JSON.parse(readFileSync(new URL(`./${name}.json`, import.meta.url))), 'unit-test-only');
  const participants = Array.from({length: count}, (_, i) => ({slot: i + 1, runtimeId: `runtime-${i}`, runtimeAgentId: `agent-${i}`, datasetId: `dataset-${i}`, federationId: 'federation', trainingRunConfigurationId: 'config'}));
  const environment = {status: 'ready', environment: 'test', deploymentId: 'test', endpoints: {platform: 'http://platform.test', support: 'http://support.test'}, participantDeployments: participants.map(p => ({slot: p.slot, consoleUrl: `http://participant-${p.slot}.test`}))};
  const data = {schemaVersion: 1, status: 'ready', environment: 'test', deploymentId: 'test', records: [{participants}]};
  const config = JSON.parse(readFileSync(new URL('../../pipelines/http-executor.json', import.meta.url)));
  delete config.envFile;
  let jobId;
  const calls = [];
  const content = Buffer.from('model artifact');
  const digest = 'sha256:' + createHash('sha256').update(content).digest('hex');
  const adapter = createHttpExecutor(config, {env: {MEDOL_SECURITY_INTERNAL_TOKEN: 'test-only'}, fetchImpl: async (url, options) => {
    calls.push([url.pathname, options.method]);
    let body;
    if (options.method === 'POST') {body = JSON.parse(options.body); jobId = body.trainingJobId;}
    else if (url.pathname === '/actuator/health') body = {status: 'UP'};
    else if (url.pathname === '/model') return {ok: true, status: 200, body: (async function*(){yield content;})()};
    else if (url.pathname.includes('trainingrunconfigurationcatalog')) body = {page:{totalElements: 1}, content: [{federationId: 'federation', state: 'Draft', maxRounds: rounds, minimumNodesPerRound: count}]};
    else if (url.pathname.includes('runtimeagentendpointcatalog')) {
      const p = participants.find(p => p.runtimeAgentId === url.searchParams.get('runtimeAgentId.equals'));
      body = {page:{totalElements: 1}, content: [{runtimeId: p.runtimeId, runtimeAgentEndpoint: 'http://reachable.test'}]};
    } else if (url.pathname.endsWith('/datasetcapability')) body = {page:{totalElements: 1}, content: [{schemaCompatible: true, labelCompatible: true}]};
    else if (url.pathname.endsWith('/trainingjobdashboard')) body = {page:{totalElements: 1}, content: [{trainingJobId: jobId, state: 'Completed'}]};
    else if (url.pathname.endsWith('/trainingroundprogress')) body = {page:{totalElements: rounds}, content: Array.from({length: rounds}, (_, i) => ({roundId: `round-${i}`, roundNumber: i + 1, state: 'Completed', minimumNodesPerRound: count, acceptedModelUpdateCount: count,
      selectedParticipants: participants, aggregatedModelId: `model-${i}`, artifactRefs: participants.map(p => `http://support.test/api/files/update-${i}-${p.slot}/content`)}))};
    else if (url.pathname.endsWith('/roundexecutioncatalog')) {
      const p = participants.find(p => p.runtimeId === url.searchParams.get('runtimeId.equals'));
      const i = Number(url.searchParams.get('roundId.equals').split('-')[1]);
      body = {page:{totalElements: 1}, content: [{roundExecutionId: `exec-${i}-${p.slot}`, updateArtifactId: `update-${i}-${p.slot}`, baseModelId: brokenLink ? 'wrong' : `model-${i - 1}`}]};
    } else if (url.pathname.endsWith('/modelartifactcatalog')) body = {page:{totalElements: 1}, content: [{modelArtifactUri: 'http://support.test/model', modelArtifactDigest: digest}]};
    else throw Error('Unexpected request: ' + url.pathname);
    return {ok: true, status: 200, json: async () => body};
  }});
  return {report: await executeCases(plan, environment, data, adapter), calls};
}
test('two participants execute declarative training without project training callbacks', async () => {
  const {report, calls} = await run(2, 1);
  assert.equal(report.status, 'passed', JSON.stringify(report));
  assert.equal(calls.filter(c => c[1] === 'POST').length, 2);
});
test('twenty participants verify two rounds and forty executions through generic loops', async () => {
  const {report, calls} = await run(20, 2);
  assert.equal(report.status, 'passed', JSON.stringify(report));
  assert.equal(calls.filter(c => c[0].endsWith('/roundexecutioncatalog')).length, 40);
});
test('a later round using the wrong base model fails', async () => {
  const {report} = await run(20, 2, true);
  assert.equal(report.status, 'failed');
  assert.match(report.cases[0].error, /previous-model/);
});
