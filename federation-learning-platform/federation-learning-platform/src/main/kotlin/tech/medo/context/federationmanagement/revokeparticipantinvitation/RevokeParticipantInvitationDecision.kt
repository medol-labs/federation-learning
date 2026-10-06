package tech.medo.federationmanagement.revokeparticipantinvitation

import tech.medo.federationmanagement.revokeparticipantinvitation.RevokeParticipantInvitationCommand


import tech.medo.federationmanagement.events.ParticipantInvitationRevokedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState


import tech.medo.federationmanagement.domain.states.FederationMembershipStateEnum


interface RevokeParticipantInvitationDecision {
    fun decide(command: RevokeParticipantInvitationCommand, state: FederationMembershipState): List<Any> {
        if (state.currentState != FederationMembershipStateEnum.Invited) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.federationmanagement.revokeParticipantInvitation.requiresState",
                args = mapOf(
                    "command" to "RevokeParticipantInvitation",
                    "aggregate" to "FederationMembership",
                    "expectedState" to "Invited",
                    "actualState" to state.currentState.toString()
                ),
                message = "RevokeParticipantInvitation requires FederationMembership to be Invited."
            )
        }
        return listOf(
            ParticipantInvitationRevokedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, revokeReason = command.revokeReason)
        )
    }
}
