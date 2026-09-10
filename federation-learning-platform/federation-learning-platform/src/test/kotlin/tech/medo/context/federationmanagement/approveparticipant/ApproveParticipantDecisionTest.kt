package tech.medo.federationmanagement.approveparticipant

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.federationmanagement.approveparticipant.ApproveParticipantCommand
import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.events.ParticipantJoinedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipState
import java.util.UUID

class ApproveParticipantDecisionTest {
    @Test
    fun ApproveInvitedParticipant() {
        val state = FederationMembershipState()
        state.evolve(
            ParticipantInvitedEvent(
            federationId = UUID.nameUUIDFromBytes("fed-1".toByteArray()),
            federationName = null,
            organizationId = UUID.nameUUIDFromBytes("org-1".toByteArray()),
            organizationName = null,
            invitationNote = ""
            )
        )

        val command = ApproveParticipantCommand(
            federationId = UUID.nameUUIDFromBytes("fed-1".toByteArray()),
            federationName = null,
            organizationId = UUID.nameUUIDFromBytes("org-1".toByteArray()),
            organizationName = null,
            approvalNote = null
        )

        val events = (object : ApproveParticipantDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<ParticipantJoinedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("fed-1".toByteArray()), event.federationId)
        assertEquals(command.federationName, event.federationName)
        assertEquals(UUID.nameUUIDFromBytes("org-1".toByteArray()), event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(command.approvalNote, event.approvalNote)
    }
}
