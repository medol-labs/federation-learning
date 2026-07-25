package tech.medo.federationmanagement.reactivatefederation

import org.springframework.stereotype.Component
import tech.medo.federationmanagement.reactivatefederation.ReactivateFederationCommand

import tech.medo.federationmanagement.events.FederationReactivatedEvent
import tech.medo.federationmanagement.federation.FederationState


import tech.medo.federationmanagement.domain.states.FederationStateEnum


@Component
class ReactivateFederationDecision {
    fun decide(command: ReactivateFederationCommand, state: FederationState): List<Any> {
        require(state.currentState == FederationStateEnum.SUSPENDED) {
            "ReactivateFederation requires Federation to be Suspended."
        }
        return listOf(
            FederationReactivatedEvent(federationId = command.federationId, reactivationReason = command.reactivationReason)
        )
    }
}
