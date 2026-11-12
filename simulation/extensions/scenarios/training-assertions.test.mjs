import test from 'node:test';
import assert from 'node:assert/strict';
import {roundHasExpectedContributions} from './training-assertions.mjs';

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
