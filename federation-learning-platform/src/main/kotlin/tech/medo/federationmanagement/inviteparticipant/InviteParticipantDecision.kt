package tech.medo.federationmanagement.inviteparticipant

import org.springframework.stereotype.Component
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand

import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState





@Component
class InviteParticipantDecision {
    fun decide(command: InviteParticipantCommand): List<Any> {
        return listOf(
            ParticipantInvitedEvent(federationId = command.federationId, organizationId = command.organizationId, invitationNote = command.invitationNote)
        )
    }
}
