package tech.medo.federationmanagement.suspendfederation

import tech.medo.federationmanagement.suspendfederation.SuspendFederationCommand


import tech.medo.federationmanagement.events.FederationSuspendedEvent
import tech.medo.federationmanagement.federation.FederationState


import tech.medo.federationmanagement.domain.states.FederationStateEnum


interface SuspendFederationDecision {
    fun decide(command: SuspendFederationCommand, state: FederationState): List<Any> {
        if (state.currentState != FederationStateEnum.Active) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.suspendFederation.requiresState",
                args = mapOf(
                    "command" to "SuspendFederation",
                    "aggregate" to "Federation",
                    "expectedState" to "Active",
                    "actualState" to state.currentState.toString()
                ),
                message = "SuspendFederation requires Federation to be Active."
            )
        }
        return listOf(
            FederationSuspendedEvent(federationId = command.federationId, suspensionReason = command.suspensionReason, federationName = command.federationName)
        )
    }
}
