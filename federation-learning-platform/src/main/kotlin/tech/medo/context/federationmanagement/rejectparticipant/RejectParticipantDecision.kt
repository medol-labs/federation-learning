package tech.medo.federationmanagement.rejectparticipant

import tech.medo.federationmanagement.rejectparticipant.RejectParticipantCommand

import tech.medo.federationmanagement.events.ParticipantRejectedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface RejectParticipantDecision {
    fun decide(command: RejectParticipantCommand, state: FederationMembershipState): List<Any> {
        require(state.currentState == FederationMembershipStateEnum.INVITED) {
            "RejectParticipant requires FederationMembership to be Invited."
        }
        return listOf(
            ParticipantRejectedEvent(federationId = command.federationId, organizationId = command.organizationId, rejectionReason = command.rejectionReason)
        )
    }
}
