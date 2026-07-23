package tech.medo.secureaggregation.selectsecureaggregationparticipants

import org.springframework.stereotype.Component
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsCommand

import tech.medo.secureaggregation.events.SecureAggregationParticipantsSelectedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState


import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


@Component
class SelectSecureAggregationParticipantsDecision {
    fun decide(command: SelectSecureAggregationParticipantsCommand, state: SecureAggregationSessionState): List<Any> {
        require(state.currentState == SecureAggregationSessionStateEnum.PLANNED) {
            "SelectSecureAggregationParticipants requires SecureAggregationSession to be Planned."
        }
        return listOf(
            SecureAggregationParticipantsSelectedEvent(secureAggregationSessionId = command.secureAggregationSessionId, roundId = command.roundId, acceptedRuntimeIds = command.acceptedRuntimeIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedParticipantCount = command.selectedParticipantCount)
        )
    }
}
