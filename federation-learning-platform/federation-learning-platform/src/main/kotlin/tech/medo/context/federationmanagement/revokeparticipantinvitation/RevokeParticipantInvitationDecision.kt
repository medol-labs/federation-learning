package tech.medo.federationmanagement.revokeparticipantinvitation

import tech.medo.federationmanagement.revokeparticipantinvitation.RevokeParticipantInvitationCommand


import tech.medo.federationmanagement.events.ParticipantInvitationRevokedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface RevokeParticipantInvitationDecision {
    fun decide(command: RevokeParticipantInvitationCommand, state: FederationMembershipState): List<Any> {
        require(state.currentState == FederationMembershipStateEnum.INVITED) {
            "RevokeParticipantInvitation requires FederationMembership to be Invited."
        }
        return listOf(
            ParticipantInvitationRevokedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, revokeReason = command.revokeReason)
        )
    }
}
