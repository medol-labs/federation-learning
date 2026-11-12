export function roundHasExpectedContributions(round, participants) {
    const expected = new Set(participants.map(item => item.runtimeId));
    if (expected.has(undefined) || expected.has(null) || expected.size !== participants.length) {
        throw new Error('Participants must have distinct runtime IDs from the endpoint catalog');
    }
    const selected = new Set((round?.selectedParticipants || []).map(item => item.runtimeId));
    return round?.minimumNodesPerRound === expected.size &&
        selected.size === expected.size && [...expected].every(id => selected.has(id)) &&
        Number(round.acceptedModelUpdateCount ?? round.acceptedUpdateCount ?? 0) >= expected.size &&
        Boolean(round.aggregatedModelId);
}
