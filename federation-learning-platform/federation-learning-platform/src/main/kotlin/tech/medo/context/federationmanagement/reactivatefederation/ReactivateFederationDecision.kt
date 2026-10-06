package tech.medo.federationmanagement.reactivatefederation

import tech.medo.federationmanagement.reactivatefederation.ReactivateFederationCommand


import tech.medo.federationmanagement.events.FederationReactivatedEvent
import tech.medo.federationmanagement.federation.FederationState


import tech.medo.federationmanagement.domain.states.FederationStateEnum


interface ReactivateFederationDecision {
    fun decide(command: ReactivateFederationCommand, state: FederationState): List<Any> {
        if (state.currentState != FederationStateEnum.Suspended) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.reactivateFederation.requiresState",
                args = mapOf(
                    "command" to "ReactivateFederation",
                    "aggregate" to "Federation",
                    "expectedState" to "Suspended",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReactivateFederation requires Federation to be Suspended."
            )
        }
        return listOf(
            FederationReactivatedEvent(federationId = command.federationId, reactivationReason = command.reactivationReason, federationName = command.federationName)
        )
    }
}
