package tech.medo.federationmanagement.suspendfederation

import org.springframework.stereotype.Component
import tech.medo.federationmanagement.suspendfederation.SuspendFederationCommand

import tech.medo.federationmanagement.events.FederationSuspendedEvent
import tech.medo.federationmanagement.federation.FederationState


import tech.medo.federationmanagement.domain.states.FederationStateEnum


@Component
class SuspendFederationDecision {
    fun decide(command: SuspendFederationCommand, state: FederationState): List<Any> {
        require(state.currentState == FederationStateEnum.ACTIVE) {
            "SuspendFederation requires Federation to be Active."
        }
        return listOf(
            FederationSuspendedEvent(federationId = command.federationId, suspensionReason = command.suspensionReason)
        )
    }
}
