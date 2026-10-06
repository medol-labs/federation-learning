package tech.medo.federationmanagement.activatefederation

import tech.medo.federationmanagement.activatefederation.ActivateFederationCommand


import tech.medo.federationmanagement.events.FederationActivatedEvent
import tech.medo.federationmanagement.federation.FederationState


import tech.medo.federationmanagement.domain.states.FederationStateEnum


interface ActivateFederationDecision {
    fun decide(command: ActivateFederationCommand, state: FederationState): List<Any> {
        if (state.currentState != FederationStateEnum.Draft) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.activateFederation.requiresState",
                args = mapOf(
                    "command" to "ActivateFederation",
                    "aggregate" to "Federation",
                    "expectedState" to "Draft",
                    "actualState" to state.currentState.toString()
                ),
                message = "ActivateFederation requires Federation to be Draft."
            )
        }
        return listOf(
            FederationActivatedEvent(federationId = command.federationId, activationNote = command.activationNote, federationName = command.federationName)
        )
    }
}
