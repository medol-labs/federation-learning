import test from 'node:test';
import assert from 'node:assert/strict';
import { executeSteps } from './run-training.mjs';

test('simulation owns ordered stages and repeats independent participants', async () => {
  const calls = [];
  await executeSteps({ steps: [{ id: 'cluster', action: 'prepareCluster' }, { id: 'participants', action: 'prepareParticipant', repeat: 'participantCount' }] }, {
    participantCount: 2, prepareCluster: async () => calls.push('cluster'),
    prepareParticipant: async index => calls.push(index), snapshot: () => ({}), cleanup: () => calls.push('cleanup')
  });
  assert.deepEqual(calls, ['cluster', 1, 2, 'cleanup']);
});

test('failure prevents subsequent stages and cleans up resources', async () => {
  const calls = [];
  await assert.rejects(executeSteps({ steps: [{ id: 'cluster', action: 'prepareCluster' }, { id: 'verify', action: 'verifyTraining' }] }, {
    prepareCluster: async () => { throw new Error('unavailable'); }, verifyTraining: async () => calls.push('verify'),
    snapshot: () => ({}), cleanup: () => calls.push('cleanup')
  }), /unavailable/);
  assert.deepEqual(calls, ['cleanup']);
});
