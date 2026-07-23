package tech.medo.federationmanagement.removeparticipant

import org.springframework.stereotype.Component
import tech.medo.federationmanagement.removeparticipant.RemoveParticipantCommand

import tech.medo.federationmanagement.events.ParticipantRemovedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


@Component
class RemoveParticipantDecision {
    fun decide(command: RemoveParticipantCommand, state: FederationMembershipState): List<Any> {
        require(state.currentState == FederationMembershipStateEnum.SUSPENDED) {
            "RemoveParticipant requires FederationMembership to be Suspended."
        }
        return listOf(
            ParticipantRemovedEvent(federationId = command.federationId, organizationId = command.organizationId, removalReason = command.removalReason)
        )
    }
}
