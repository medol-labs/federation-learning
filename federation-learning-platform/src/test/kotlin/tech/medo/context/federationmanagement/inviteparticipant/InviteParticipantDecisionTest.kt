package tech.medo.federationmanagement.inviteparticipant

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand
import tech.medo.federationmanagement.events.ParticipantInvitedEvent



import java.util.UUID;


class InviteParticipantDecisionTest {
    @Test
    fun InviteParticipantEmitsParticipantInvitedEvent() {
        val events = (object : InviteParticipantDecision {}).decide(
            InviteParticipantCommand(
            federationId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            invitationNote = ""
            )
        )

        assertTrue(events.any { it is ParticipantInvitedEvent })
    }
}
