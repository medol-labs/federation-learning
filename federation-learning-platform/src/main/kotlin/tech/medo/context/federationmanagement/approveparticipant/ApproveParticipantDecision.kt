package tech.medo.federationmanagement.approveparticipant

import tech.medo.federationmanagement.approveparticipant.ApproveParticipantCommand

import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface ApproveParticipantDecision {
    fun decide(command: ApproveParticipantCommand, state: FederationMembershipState): List<Any> {
        require(state.currentState == FederationMembershipStateEnum.INVITED) {
            "ApproveParticipant requires FederationMembership to be Invited."
        }
        return listOf(
            ParticipantJoinedEvent(federationId = command.federationId, organizationId = command.organizationId, approvalNote = command.approvalNote)
        )
    }
}
