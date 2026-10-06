package tech.medo.secureaggregation.failsecureaggregationsession

import tech.medo.secureaggregation.failsecureaggregationsession.FailSecureAggregationSessionCommand


import tech.medo.secureaggregation.events.SecureAggregationFailedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState


import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


interface FailSecureAggregationSessionDecision {
    fun decide(command: FailSecureAggregationSessionCommand, state: SecureAggregationSessionState): List<Any> {
        if (state.currentState != SecureAggregationSessionStateEnum.Planned) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.secureaggregation.failSecureAggregationSession.requiresState",
                args = mapOf(
                    "command" to "FailSecureAggregationSession",
                    "aggregate" to "SecureAggregationSession",
                    "expectedState" to "Planned",
                    "actualState" to state.currentState.toString()
                ),
                message = "FailSecureAggregationSession requires SecureAggregationSession to be Planned."
            )
        }
        return listOf(
            SecureAggregationFailedEvent(secureAggregationSessionId = command.secureAggregationSessionId, failureReason = command.failureReason)
        )
    }
}
