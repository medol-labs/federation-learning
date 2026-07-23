package tech.medo.federationmanagement.suspendparticipant

import org.springframework.stereotype.Component
import tech.medo.federationmanagement.suspendparticipant.SuspendParticipantCommand

import tech.medo.federationmanagement.events.ParticipantSuspendedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


@Component
class SuspendParticipantDecision {
    fun decide(command: SuspendParticipantCommand, state: FederationMembershipState): List<Any> {
        require(state.currentState == FederationMembershipStateEnum.ACTIVE) {
            "SuspendParticipant requires FederationMembership to be Active."
        }
        return listOf(
            ParticipantSuspendedEvent(federationId = command.federationId, organizationId = command.organizationId, suspensionReason = command.suspensionReason)
        )
    }
}
