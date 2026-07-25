package tech.medo.federationmanagement.revokeparticipantinvitation

import org.springframework.stereotype.Component
import tech.medo.federationmanagement.revokeparticipantinvitation.RevokeParticipantInvitationCommand

import tech.medo.federationmanagement.events.ParticipantInvitationRevokedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


@Component
class RevokeParticipantInvitationDecision {
    fun decide(command: RevokeParticipantInvitationCommand, state: FederationMembershipState): List<Any> {
        require(state.currentState == FederationMembershipStateEnum.INVITED) {
            "RevokeParticipantInvitation requires FederationMembership to be Invited."
        }
        return listOf(
            ParticipantInvitationRevokedEvent(federationId = command.federationId, organizationId = command.organizationId, revokeReason = command.revokeReason)
        )
    }
}
