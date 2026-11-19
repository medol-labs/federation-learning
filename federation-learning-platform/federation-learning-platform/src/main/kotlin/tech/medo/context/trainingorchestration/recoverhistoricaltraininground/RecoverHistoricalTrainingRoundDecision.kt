package tech.medo.trainingorchestration.recoverhistoricaltraininground

import tech.medo.trainingorchestration.recoverhistoricaltraininground.RecoverHistoricalTrainingRoundCommand


import tech.medo.trainingorchestration.events.HistoricalTrainingRoundRecoveredEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface RecoverHistoricalTrainingRoundDecision {
    fun decide(command: RecoverHistoricalTrainingRoundCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            HistoricalTrainingRoundRecoveredEvent(roundId = command.roundId, trainingJobId = command.trainingJobId, recoveryDigest = command.recoveryDigest, canonicalRoundId = requireNotNull(state.canonicalRoundId) { "canonicalRoundId is required from state." }, recoverySubmissionIds = emptyList() /* TODO: derive value */, federationId = requireNotNull(state.federationId) { "federationId is required from state." }, trainingRunConfigurationId = requireNotNull(state.trainingRunConfigurationId) { "trainingRunConfigurationId is required from state." }, featureSchemaId = requireNotNull(state.featureSchemaId) { "featureSchemaId is required from state." }, trainingJobObjective = requireNotNull(state.trainingJobObjective) { "trainingJobObjective is required from state." }, roundNumber = requireNotNull(state.roundNumber) { "roundNumber is required from state." }, maxRounds = requireNotNull(state.maxRounds) { "maxRounds is required from state." }, minimumAccuracy = requireNotNull(state.minimumAccuracy) { "minimumAccuracy is required from state." }, aggregationAlgorithm = requireNotNull(state.aggregationAlgorithm) { "aggregationAlgorithm is required from state." }, minimumNodesPerRound = requireNotNull(state.minimumNodesPerRound) { "minimumNodesPerRound is required from state." }, secureAggregationRequired = requireNotNull(state.secureAggregationRequired) { "secureAggregationRequired is required from state." }, selectedParticipants = requireNotNull(state.selectedParticipants) { "selectedParticipants is required from state." }, selectedOrganizationIds = requireNotNull(state.selectedOrganizationIds) { "selectedOrganizationIds is required from state." }, selectedRuntimeIds = requireNotNull(state.selectedRuntimeIds) { "selectedRuntimeIds is required from state." }, selectedOrganizationCount = requireNotNull(state.selectedOrganizationCount) { "selectedOrganizationCount is required from state." }, selectedRuntimeCount = requireNotNull(state.selectedRuntimeCount) { "selectedRuntimeCount is required from state." })
        )
    }
}
