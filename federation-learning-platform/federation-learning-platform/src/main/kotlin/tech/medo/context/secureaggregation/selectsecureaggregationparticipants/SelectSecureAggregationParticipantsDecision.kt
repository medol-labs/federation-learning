package tech.medo.secureaggregation.selectsecureaggregationparticipants

import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsCommand

import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsResult
import tech.medo.secureaggregation.events.SecureAggregationParticipantsSelectedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState


import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


interface SelectSecureAggregationParticipantsDecision {
    fun decide(command: SelectSecureAggregationParticipantsCommand, state: SecureAggregationSessionState, portResult: SelectSecureAggregationParticipantsResult): List<Any> {
        require(state.currentState == SecureAggregationSessionStateEnum.PLANNED) {
            "SelectSecureAggregationParticipants requires SecureAggregationSession to be Planned."
        }
        return when (portResult) {
                    is SelectSecureAggregationParticipantsResult.Succeeded -> listOf(SecureAggregationParticipantsSelectedEvent(secureAggregationSessionId = command.secureAggregationSessionId, roundId = command.roundId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedParticipantCount = command.selectedParticipantCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired))
                }
    }
}
