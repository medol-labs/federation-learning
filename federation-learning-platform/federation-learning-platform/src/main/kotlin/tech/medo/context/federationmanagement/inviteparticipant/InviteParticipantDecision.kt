package tech.medo.federationmanagement.inviteparticipant

import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand


import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState





interface InviteParticipantDecision {
    fun decide(command: InviteParticipantCommand): List<Any> {
        return listOf(
            ParticipantInvitedEvent(federationId = command.federationId, federationName = command.federationName, organizationId = command.organizationId, organizationName = command.organizationName, invitationNote = command.invitationNote)
        )
    }
}
