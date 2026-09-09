package tech.medo.secureaggregation.failsecureaggregationsession

import tech.medo.secureaggregation.failsecureaggregationsession.FailSecureAggregationSessionCommand


import tech.medo.secureaggregation.events.SecureAggregationFailedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState


import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


interface FailSecureAggregationSessionDecision {
    fun decide(command: FailSecureAggregationSessionCommand, state: SecureAggregationSessionState): List<Any> {
        require(state.currentState == SecureAggregationSessionStateEnum.PLANNED) {
            "FailSecureAggregationSession requires SecureAggregationSession to be Planned."
        }
        return listOf(
            SecureAggregationFailedEvent(secureAggregationSessionId = command.secureAggregationSessionId, failureReason = command.failureReason)
        )
    }
}
