package tech.medo.domain.federationmanagement.inviteparticipant

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import tech.medo.federationmanagement.events.ParticipantInvitedEvent
import tech.medo.federationmanagement.events.FederationMembershipFederationIdOrganizationIdReservedEvent
import tech.medo.federationmanagement.federationmembership.FederationMembershipFederationIdOrganizationIdReservationState
import tech.medo.federationmanagement.federationmembershipdirectory.*
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand
import tech.medo.shared.domain.CommandRejectedException
import java.util.UUID

class InviteParticipantDecisionComponentTest {
    private val memberships = mock(FederationMembershipDirectoryReadModelRepository::class.java)
    private val decision = InviteParticipantDecisionComponent(memberships)
    private val command = InviteParticipantCommand(UUID.randomUUID(), "Federation", UUID.randomUUID(), "Organization", "Join")

    @Test
    fun `first invitation reserves the exact pair and emits the invitation`() {
        val events = decision.decide(command, FederationMembershipFederationIdOrganizationIdReservationState())
        val reservation = events.filterIsInstance<FederationMembershipFederationIdOrganizationIdReservedEvent>().single()
        assertEquals(command.federationId.toString(), reservation.normalizedFederationId)
        assertEquals(command.organizationId.toString(), reservation.normalizedOrganizationId)
        assertEquals(command.organizationId, events.filterIsInstance<ParticipantInvitedEvent>().single().organizationId)
        verify(memberships).findProjectionById(FederationMembershipDirectoryReadModelKey(command.federationId, command.organizationId))
    }

    @Test
    fun `repeat invitation is rejected even before the membership projection catches up`() {
        val state = FederationMembershipFederationIdOrganizationIdReservationState()
        decision.decide(command, state).filterIsInstance<FederationMembershipFederationIdOrganizationIdReservedEvent>().forEach(state::evolve)
        clearInvocations(memberships)
        val error = assertThrows(CommandRejectedException::class.java) { decision.decide(command, state) }
        assertEquals("PARTICIPANT_ALREADY_INVITED", error.code)
        verifyNoInteractions(memberships)
    }

    @Test
    fun `legacy invitations and memberships cannot bypass the new reservation`() {
        for (status in listOf("Invited", "Active", "Suspended", "Rejected", "InvitationRevoked", "Removed")) {
            `when`(memberships.findProjectionById(FederationMembershipDirectoryReadModelKey(command.federationId, command.organizationId)))
                .thenReturn(FederationMembershipDirectoryReadModelProjection().apply { membershipStatus = status })
            assertThrows(CommandRejectedException::class.java) {
                decision.decide(command, FederationMembershipFederationIdOrganizationIdReservationState())
            }
        }
    }

    @Test
    fun `same organization in another federation and another organization in the same federation remain valid`() {
        `when`(memberships.findProjectionById(FederationMembershipDirectoryReadModelKey(command.federationId, command.organizationId)))
            .thenReturn(FederationMembershipDirectoryReadModelProjection())
        for (other in listOf(command.copy(federationId = UUID.randomUUID()), command.copy(organizationId = UUID.randomUUID()))) {
            assertEquals(2, decision.decide(other, FederationMembershipFederationIdOrganizationIdReservationState()).size)
        }
    }
}
