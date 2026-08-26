package tech.medo.trainingorchestration.requestsecureaggregation

import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationCommand

import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState


import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum


interface RequestSecureAggregationDecision {
    fun decide(command: RequestSecureAggregationCommand, state: TrainingRoundState): List<Any> {
        require(state.currentState == TrainingRoundStateEnum.PARTICIPANTS_SELECTED) {
            "RequestSecureAggregation requires TrainingRound to be ParticipantsSelected."
        }
        return listOf(
            SecureAggregationRequestedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, requiredParticipantCount = command.requiredParticipantCount, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, secureAggregationRequired = command.secureAggregationRequired)
        )
    }
}
