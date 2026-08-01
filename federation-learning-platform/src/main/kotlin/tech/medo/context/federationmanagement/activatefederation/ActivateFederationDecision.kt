package tech.medo.federationmanagement.activatefederation

import tech.medo.federationmanagement.activatefederation.ActivateFederationCommand

import tech.medo.federationmanagement.events.FederationActivatedEvent
import tech.medo.federationmanagement.federation.FederationState


import tech.medo.federationmanagement.domain.states.FederationStateEnum


interface ActivateFederationDecision {
    fun decide(command: ActivateFederationCommand, state: FederationState): List<Any> {
        require(state.currentState == FederationStateEnum.DRAFT) {
            "ActivateFederation requires Federation to be Draft."
        }
        return listOf(
            FederationActivatedEvent(federationId = command.federationId, activationNote = command.activationNote, federationName = command.federationName)
        )
    }
}
