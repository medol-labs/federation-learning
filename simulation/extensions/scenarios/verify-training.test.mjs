import {test} from 'node:test';
import assert from 'node:assert/strict';
import {createHash} from 'node:crypto';
import {verifyTrainingResult} from './verify-training.mjs';

function fixture() {
    const participants = Array.from({length: 20}, (_, i) => ({runtimeId: `runtime-${i}`, runtimeAgentUrl: `http://agent-${i}`}));
    const supportUrl = 'http://support';
    const bytes = Buffer.from('{"weights":[1,2,3]}');
    const digest = 'sha256:' + createHash('sha256').update(bytes).digest('hex');
    const rounds = [1, 2].map(number => ({roundId: `round-${number}`, roundNumber: number,
        minimumNodesPerRound: 20, selectedParticipants: participants, acceptedModelUpdateCount: 20,
        aggregatedModelId: `model-${number}`, artifactRefs: participants.map(p => `http://support/api/files/update-${number}-${p.runtimeId}/content`)}));
    const executions = rounds.flatMap(round => participants.map(p => ({roundId: round.roundId, runtimeId: p.runtimeId,
        roundExecutionId: `${round.roundId}-${p.runtimeId}`, updateArtifactId: `update-${round.roundNumber}-${p.runtimeId}`,
        baseModelId: round.roundNumber === 2 ? 'model-1' : 'initial'})));
    const options = {platformUrl: 'http://platform', supportUrl, expectedRounds: 2, federationMode: 'global', requestHeaders: () => ({}),
        fail: message => { throw new Error(message); },
        waitForCondition: async (_name, condition) => { const value = await condition(); if (!value) throw new Error('Condition not met'); return value; },
        getPage: async (_base, path, query) => {
            if (path.endsWith('trainingroundprogress')) return {content: rounds};
            if (path.endsWith('trainingjobdashboard')) return {content: [{state: 'COMPLETED'}]};
            if (path.endsWith('roundexecutioncatalog')) return {content: executions.filter(e => e.roundId === query['roundId.equals'] && e.runtimeId === query['runtimeId.equals'])};
            return {content: [{modelId: query['modelId.equals'], modelArtifactUri: 'http://support/api/files/global/content', modelArtifactDigest: digest}]};
        }};
    return {participants, rounds, executions, options, bytes};
}

test('verifies all forty executions and both model artifacts, rejects broken model lineage', async t => {
    const f = fixture(), original = globalThis.fetch;
    globalThis.fetch = async () => new Response(f.bytes);
    t.after(() => { globalThis.fetch = original; });
    const result = await verifyTrainingResult('job', f.participants, f.options);
    assert.equal(result.acceptedExecutionCount, 40);
    assert.equal(result.completedRoundCount, 2);
    assert.equal(result.rounds[1].executions.length, 20);
    f.executions.at(-1).baseModelId = 'initial';
    await assert.rejects(verifyTrainingResult('job', f.participants, f.options), /previous aggregated model/);
    f.rounds.pop();
    await assert.rejects(verifyTrainingResult('job', f.participants, f.options), /Condition not met/);
});
