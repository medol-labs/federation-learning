import {test} from 'node:test';
import assert from 'node:assert/strict';
import {prepareCaseData, enrollParticipants, trainingArguments} from './training-stages.mjs';
import {approveCases} from '../../src/cases.mjs';
import {readFileSync} from 'node:fs';
const scenario = JSON.parse(readFileSync(new URL('../scenarios/two-participant-training.json', import.meta.url)));

const environment = {status: 'ready', target: 'kubernetes', environment: 'local', deploymentId: 'test', kubeconfigFile: '/tmp/test-config', participantDeployments: [{slot: 1}, {slot: 2}], endpoints: {platform: 'http://platform', support: 'http://support'}};
const baseline = {schemaVersion: 1, mode: 'baseline', status: 'ready', environment: 'local', deploymentId: 'test', records: []};
test('enrollment only registers deployed participants and requires actual endpoints', async () => {
  const calls = [];
  let complete = true;
  const factory = async () => ({participantCount: 2, validateConnection: async () => {}, waitReady: async () => {}, verifyBaseline: async () => {},
    enrollParticipant: async slot => calls.push(slot),
    dataSnapshot: () => ({participants: [1, 2].map(id => ({runtimeId: String(id), runtimeAgentEndpoint: complete ? `http://agent-${id}` : null}))}),
    cleanup: async () => calls.push('cleanup')});
  const result = await enrollParticipants(environment, {}, baseline, factory);
  assert.equal(result.mode, 'enrollment');
  assert.deepEqual(calls, [1, 2, 'cleanup']);
  complete = false;
  await assert.rejects(enrollParticipants(environment, {}, baseline, factory), /incomplete/);
  await assert.rejects(enrollParticipants(environment, {}, {...baseline, deploymentId: 'other'}, factory), /different/);
});
const approved = approveCases({schemaVersion: 2, cases: [{id: 'test', title: 'Test', origin: 'manual', status: 'review', reviewIssues: [], prerequisites: [], dataRequirements: [{id: 'participants'}], timeoutMs: 1000, cleanupPolicy: 'retain',
  steps: [{id: 'submit', type: 'command', command: 'SubmitTraining'}, {id: 'verify', type: 'query', query: 'VerifyTraining'}],
  assertions: [{type: 'query', stepId: 'verify', path: 'verified', operator: 'equals', expected: true}]}]}, 'test');
test('case preparation rejects missing or foreign baselines and unapproved cases before side effects', async () => {
  const create = () => assert.fail('must not create adapter');
  await assert.rejects(prepareCaseData(environment, {}, null, approved, create), /baseline/);
  await assert.rejects(prepareCaseData(environment, {}, {...baseline, deploymentId: 'other'}, approved, create), /different/);
  const draft = structuredClone(approved); draft.cases[0].status = 'draft';
  await assert.rejects(prepareCaseData(environment, {}, baseline, draft, create), /approval/);
});
test('data preparation never invokes infrastructure creation or deployment', async () => {
  const calls = [];
  const data = await prepareCaseData(environment, {...scenario, arguments: []}, baseline, approved, async args => {
    assert.equal(args[args.indexOf('--kubeconfig-file') + 1], '/tmp/test-config');
    return {participantCount: 2, validateConnection: async () => calls.push('connect'), waitReady: async () => {},
      bootstrap: async () => assert.fail('must not reinitialize baseline'), verifyBaseline: async () => {}, prepareParticipant: async slot => calls.push(slot),
      dataSnapshot: () => ({trainingRunKey: 'configuration', participants: ['one', 'two'].map(id => ({runtimeAgentId: id, runtimeId: id, datasetId: id, trainingRunConfigurationId: 'configuration'}))}), cleanup: () => calls.push('cleanup')};
  });
  assert.deepEqual(calls, ['connect', 1, 2, 'cleanup']);
  assert.equal(data.deploymentId, environment.deploymentId);
  assert.equal(data.status, 'ready');
  assert.equal(data.mode, 'case');
  assert.deepEqual(data.caseIds, ['test']);
});
test('unidentified deployment is rejected before adapter creation', () => {
  assert.throws(() => trainingArguments({}, {}), /ready deployment/);
});
test('data preparation keeps the deployed registry and gateway rather than local defaults', () => {
  const args = trainingArguments({...environment, imagePrefix: 'registry.example/fl', imageVersion: 'test-release', endpoints: {...environment.endpoints, gateway: 'http://host:31080'}}, {});
  assert.equal(args[args.indexOf('--app-image-prefix') + 1], 'registry.example/fl');
  assert.equal(args[args.indexOf('--image-version') + 1], 'test-release');
  assert.equal(args[args.indexOf('--gateway-url') + 1], 'http://host:31080');
});
test('platform-only deployment cannot silently provision agents during data preparation', () => {
  assert.throws(() => trainingArguments({...environment, participantDeployments: []}, {}), /Deploy participant slots/);
});
test('dry runs and skipped business preparation cannot create ready fixtures', () => {
  const args = trainingArguments(environment, {arguments: ['--dry-run', 'true', '--skip-runtime-agent-data', 'true']});
  assert.equal(args[args.lastIndexOf('--dry-run') + 1], 'false');
  assert.equal(args[args.lastIndexOf('--skip-runtime-agent-data') + 1], 'false');
});
test('incomplete prepared fixtures fail and still clean up tunnels', async () => {
  let cleaned = false;
  await assert.rejects(prepareCaseData(environment, scenario, baseline, approved, async () => ({participantCount: 1,
    validateConnection: async () => {}, waitReady: async () => {}, verifyBaseline: async () => {},
    prepareParticipant: async () => {}, dataSnapshot: () => ({participants: []}), cleanup: () => {cleaned = true;}
  })), /incomplete/);
  assert.equal(cleaned, true);
});
test('unmapped data requirements are rejected before creating adapters', async () => {
  await assert.rejects(prepareCaseData(environment, {...scenario, dataRequirements: {}}, baseline, approved,
    () => assert.fail('must not create adapter')), /Unmapped/);
});
