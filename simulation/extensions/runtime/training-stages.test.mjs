import {test} from 'node:test';
import assert from 'node:assert/strict';
import {prepareTrainingData, testTraining, trainingArguments} from './training-stages.mjs';
import {approveCases} from '../../src/cases.mjs';

const environment = {status: 'ready', target: 'kubernetes', environment: 'local', deploymentId: 'test', kubeconfigFile: '/tmp/test-config', endpoints: {platform: 'http://platform', support: 'http://support'}};
test('data preparation never invokes infrastructure creation or deployment', async () => {
  const calls = [];
  const data = await prepareTrainingData(environment, {arguments: []}, async args => {
    assert.equal(args[args.indexOf('--kubeconfig-file') + 1], '/tmp/test-config');
    return {participantCount: 2, validateConnection: async () => calls.push('connect'), waitReady: async () => {},
      bootstrap: async () => {}, prepareParticipant: async slot => calls.push(slot),
      dataSnapshot: () => ({trainingRunKey: 'configuration', participants: ['one', 'two'].map(id => ({runtimeAgentId: id, runtimeId: id, datasetId: id, trainingRunConfigurationId: 'configuration'}))}), cleanup: () => calls.push('cleanup')};
  });
  assert.deepEqual(calls, ['connect', 1, 2, 'cleanup']);
  assert.equal(data.deploymentId, environment.deploymentId);
  assert.equal(data.status, 'ready');
});
test('unidentified deployment is rejected before adapter creation', () => {
  assert.throws(() => trainingArguments({}, {}), /ready deployment/);
});
test('dry runs and skipped business preparation cannot create ready fixtures', () => {
  const args = trainingArguments(environment, {arguments: ['--dry-run', 'true', '--skip-runtime-agent-data', 'true']});
  assert.equal(args[args.lastIndexOf('--dry-run') + 1], 'false');
  assert.equal(args[args.lastIndexOf('--skip-runtime-agent-data') + 1], 'false');
});
test('incomplete prepared fixtures fail and still clean up tunnels', async () => {
  let cleaned = false;
  await assert.rejects(prepareTrainingData(environment, {}, async () => ({participantCount: 1,
    validateConnection: async () => {}, waitReady: async () => {}, bootstrap: async () => {},
    prepareParticipant: async () => {}, dataSnapshot: () => ({participants: []}), cleanup: () => {cleaned = true;}
  })), /incomplete/);
  assert.equal(cleaned, true);
});
test('tests reuse fixture without calling participant preparation', async () => {
  const plan = {schemaVersion: 2, cases: [{id: 'test', title: 'Test', origin: 'manual', status: 'approved', reviewIssues: [], prerequisites: [], dataRequirements: [], timeoutMs: 1000, cleanupPolicy: 'retain',
    steps: [{id: 'submit', type: 'command', command: 'SubmitTraining'}, {id: 'verify', type: 'query', query: 'VerifyTraining'}],
    assertions: [{type: 'query', stepId: 'verify', path: 'verified', operator: 'equals', expected: true}]}]};
  const calls = [];
  const report = await testTraining(environment, {}, {...environment, schemaVersion: 1, records: [{participants: []}]}, approveCases(plan, 'test'), undefined, async () => ({
    validateConnection: async () => {}, waitReady: async () => {}, restoreData: async () => calls.push('restore'),
    submitTraining: async () => calls.push('submit'), verifyTraining: async () => calls.push('verify'),
    snapshot: () => ({trainingJobId: 'job'}), cleanup: () => calls.push('cleanup')
  }));
  assert.equal(report.status, 'passed');
  assert.deepEqual(calls, ['restore', 'submit', 'verify', 'cleanup']);
});
