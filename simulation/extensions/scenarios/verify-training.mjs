import {roundHasExpectedContributions} from './training-assertions.mjs';
import {verifyArtifact} from './verify-artifact.mjs';

export async function verifyTrainingResult(trainingJobId, participants, {getPage, platformUrl, supportUrl, waitForCondition, fail, federationMode, requestHeaders}) {
    const query = {'trainingJobId.equals': trainingJobId};
    let lastStatus = {};
    await waitForCondition('training job completion', async () => {
        const rounds = await getPage(platformUrl, '/traininground/trainingroundprogress', query);
        lastStatus.rounds = rounds.content ?? [];
        const failed = (rounds.content ?? []).find((round) => String(round.state).toUpperCase() === 'FAILED');
        if (failed) fail(`Training round ${failed.roundId} failed: ${failed.failureReason ?? 'unknown reason'}`);
        const jobs = await getPage(platformUrl, '/trainingjob/trainingjobdashboard', query);
        lastStatus.jobs = jobs.content ?? [];
        const failedJob = lastStatus.jobs.find((job) => ['FAILED', 'CANCELLED', 'CANCELED'].includes(String(job.state).toUpperCase()));
        if (failedJob) fail(`Training job ${trainingJobId} ended in ${failedJob.state}: ${failedJob.failureReason ?? 'no failure reason reported'}`);
        const completed = lastStatus.jobs.find((job) => String(job.state).toUpperCase() === 'COMPLETED');
        if (completed && lastStatus.rounds.some(round => roundHasExpectedContributions(round, participants))) return completed;
        const latestRound = [...lastStatus.rounds].sort((a, b) => Number(b.roundNumber) - Number(a.roundNumber))[0];
        lastStatus.participants = [];
        if (latestRound?.roundId) {
            for (const participant of participants) {
                const page = await getPage(participant.runtimeAgentUrl, '/roundexecution/roundexecutioncatalog', {
                    ...query, 'roundId.equals': latestRound.roundId,
                });
                lastStatus.participants.push({runtimeId: participant.runtimeAgentId,
                    executions: (page.content ?? []).map(({roundExecutionId, state, failureReason, rejectionReasons}) =>
                        ({roundExecutionId, state, failureReason, rejectionReasons}))});
            }
            const failures = lastStatus.participants.filter((participant) =>
                participant.executions.length > 0 && participant.executions.every((execution) =>
                    ['FAILED', 'STARTFAILED', 'STARTRETRYFAILED', 'RUNTIMERETRYFAILED'].includes(String(execution.state).toUpperCase()) ||
                    String(execution.state).toUpperCase() === 'RUNTIMEENGINERELEASEHANDLED' && Boolean(execution.failureReason)
                ));
            if (participants.length > 0 && failures.length === participants.length) {
                fail(`All participants failed in round ${latestRound.roundId}: ${JSON.stringify(failures)}`);
            }
        }
        return false;
    }, () => `Last training status: ${JSON.stringify(lastStatus)}`);
    const completedRound = lastStatus.rounds.find(round => roundHasExpectedContributions(round, participants));
    for (const participant of participants) {
        await waitForCondition(`runtime ${participant.runtimeId} update submission`, async () => {
            const page = await getPage(participant.runtimeAgentUrl, '/roundexecution/roundexecutioncatalog', {
                ...query, 'roundId.equals': completedRound.roundId, 'runtimeId.equals': participant.runtimeId,
                'submittedAt.specified': 'true', size: 100
            });
            return (page.content || []).some(item => item.runtimeId === participant.runtimeId && item.submittedAt && item.updateArtifactId);
        });
    }
    const model = await waitForCondition('aggregated model catalog', async () => {
        const page = await getPage(platformUrl, '/modelartifact/modelartifactcatalog', {
            'modelId.equals': completedRound.aggregatedModelId
        });
        return (page.content || []).find(item => item.modelId === completedRound.aggregatedModelId && item.modelArtifactUri && item.modelArtifactDigest);
    });
    await verifyArtifact(model, [platformUrl, supportUrl], url => requestHeaders({}, url));
    if (federationMode === 'personalized') {
        await waitForCondition('runtime-local personalized models for all participants', async () => {
            const page = await getPage(platformUrl, '/personalizedmodelresult/personalizedmodelcatalog', {
                ...query, size: Math.max(20, participants.length)
            });
            const models = page.content ?? [];
            return participants.every((participant) => models.some((model) =>
                model.runtimeId === participant.runtimeAgentId &&
                model.personalizationTargetId === participant.runtimeAgentId &&
                model.personalizedModelStorageLocation === 'RUNTIME_LOCAL' &&
                model.personalizedModelArtifactDigest?.match(/^sha256:[a-f\d]{64}$/i) &&
                model.personalizedModelArtifactRef && !model.platformManagedArtifactRef &&
                model.personalizedAccuracy != null && model.personalizationLift != null
            ));
        });
    }
    console.log(`[k3d-flow] verified completed training job ${trainingJobId}; federationMode=${federationMode}, participants=${participants.length}`);
}
