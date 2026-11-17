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

export function participantHasAcceptedArtifact(execution, runtimeId, round, supportUrl) {
    const prefix = supportUrl ? new URL(supportUrl).pathname.replace(/\/$/, '') : '';
    return execution?.runtimeId === runtimeId && Boolean(execution.updateArtifactId) &&
        (round.artifactRefs || []).some(ref => {
            try {
                const url = new URL(ref);
                return ['http:', 'https:'].includes(url.protocol) &&
                    url.pathname === `${prefix}/api/files/${encodeURIComponent(execution.updateArtifactId)}/content`;
            } catch { return false; }
        });
}
