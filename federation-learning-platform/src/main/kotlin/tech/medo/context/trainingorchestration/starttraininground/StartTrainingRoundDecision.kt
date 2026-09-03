package tech.medo.trainingorchestration.starttraininground

import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand

import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundResult
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.events.TrainingRoundStartFailedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState


import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum


interface StartTrainingRoundDecision {
    fun decide(command: StartTrainingRoundCommand, state: TrainingRoundState, portResult: StartTrainingRoundResult, now: java.time.LocalDateTime): List<Any> {
        require(state.currentState == TrainingRoundStateEnum.PARTICIPANTS_SELECTED) {
            "StartTrainingRound requires TrainingRound to be ParticipantsSelected."
        }
        return when (portResult) {
                    is StartTrainingRoundResult.Succeeded -> listOf(TrainingRoundStartedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipants = requireNotNull(state.selectedParticipants) { "selectedParticipants is required from state." }, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, aggregationAlgorithm = command.aggregationAlgorithm, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale))
                    is StartTrainingRoundResult.Rejected -> listOf(TrainingRoundStartFailedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipants = requireNotNull(state.selectedParticipants) { "selectedParticipants is required from state." }, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale, failureReason = portResult.failureReason))
                    is StartTrainingRoundResult.Unavailable -> listOf(TrainingRoundStartFailedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipants = requireNotNull(state.selectedParticipants) { "selectedParticipants is required from state." }, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale, failureReason = portResult.failureReason))
                }
    }
}
