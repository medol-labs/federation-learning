package tech.medo.trainingorchestration.starttraininground

import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand

import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartFailedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState


import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum


interface StartTrainingRoundDecision {
    fun decide(command: StartTrainingRoundCommand, state: TrainingRoundState, portResult: StartTrainingRoundResult, now: java.time.LocalDateTime): List<Any> {
        if (state.currentState != TrainingRoundStateEnum.ParticipantsSelected) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.trainingorchestration.startTrainingRound.requiresState",
                args = mapOf(
                    "command" to "StartTrainingRound",
                    "aggregate" to "TrainingRound",
                    "expectedState" to "ParticipantsSelected",
                    "actualState" to state.currentState.toString()
                ),
                message = "StartTrainingRound requires TrainingRound to be ParticipantsSelected."
            )
        }
        return when (portResult) {
                    is StartTrainingRoundResult.Succeeded -> listOf(
            TrainingRoundStartedEvent(trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipants = requireNotNull(state.selectedParticipants) { "selectedParticipants is required from state." }, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, aggregationAlgorithm = command.aggregationAlgorithm, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale)
            )
                    is StartTrainingRoundResult.Rejected -> listOf(TrainingRoundStartFailedEvent(trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipants = requireNotNull(state.selectedParticipants) { "selectedParticipants is required from state." }, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale, failureReason = portResult.failureReason))
                    is StartTrainingRoundResult.Unavailable -> listOf(TrainingRoundStartFailedEvent(trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipants = requireNotNull(state.selectedParticipants) { "selectedParticipants is required from state." }, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale, failureReason = portResult.failureReason))
                }
    }
}
