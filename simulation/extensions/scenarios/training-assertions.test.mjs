import test from 'node:test';
import assert from 'node:assert/strict';
import {roundHasExpectedContributions, participantHasAcceptedArtifact} from './training-assertions.mjs';

test('participant submission evidence must match a platform contribution, not an unassigned timestamp', () => {
    const round = {artifactRefs: ['https://support/api/files/artifact/content']};
    const execution = {runtimeId: 'a', updateArtifactId: 'artifact', artifactRef: '/workspace/local_update.json', submittedAt: null};
    assert.equal(participantHasAcceptedArtifact(execution, 'a', round), true);
    for (const patch of [{runtimeId: 'b'}, {updateArtifactId: null}, {updateArtifactId: 'other'}]) {
        assert.equal(participantHasAcceptedArtifact({...execution, ...patch}, 'a', round), false);
    }
    assert.equal(participantHasAcceptedArtifact(execution, 'a', {artifactRefs: []}), false);
    assert.equal(participantHasAcceptedArtifact(execution, 'a', {artifactRefs: ['/workspace/local_update.json']}), false);
    assert.equal(participantHasAcceptedArtifact(execution, 'a', {
        artifactRefs: ['https://gateway/api/support/api/files/artifact/content']
    }, 'https://gateway/api/support'), true);
});

test('requires exact participants, quorum, updates and aggregation', () => {
    const participants = [{runtimeId: 'a'}, {runtimeId: 'b'}];
    const round = {minimumNodesPerRound: 2, selectedParticipants: participants,
        acceptedModelUpdateCount: 2, aggregatedModelId: 'model'};
    assert.equal(roundHasExpectedContributions(round, participants), true);
    for (const patch of [{minimumNodesPerRound: 1}, {acceptedModelUpdateCount: 1},
        {aggregatedModelId: null}, {selectedParticipants: [{runtimeId: 'a'}]}]) {
        assert.equal(roundHasExpectedContributions({...round, ...patch}, participants), false);
    }
    assert.throws(() => roundHasExpectedContributions(round, [{runtimeId: 'a'}, {runtimeId: 'a'}]), /distinct/);
});
