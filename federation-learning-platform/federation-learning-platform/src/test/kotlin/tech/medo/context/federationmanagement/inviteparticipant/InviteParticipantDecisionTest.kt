package tech.medo.federationmanagement.inviteparticipant

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand
import tech.medo.federationmanagement.federationmembership.FederationMembershipFederationIdOrganizationIdReservationState
import tech.medo.federationmanagement.events.FederationMembershipFederationIdOrganizationIdReservedEvent
import java.util.UUID

class InviteParticipantDecisionTest {
    @Test
    fun RejectDuplicateParticipantInvitation() {
        val federationMembershipFederationIdOrganizationIdReservation = FederationMembershipFederationIdOrganizationIdReservationState()
        federationMembershipFederationIdOrganizationIdReservation.evolve(
            FederationMembershipFederationIdOrganizationIdReservedEvent(
                federationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
                organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                normalizedFederationId = UUID.fromString("11111111-1111-4111-8111-111111111111").toString().trim().lowercase(),
                normalizedOrganizationId = UUID.fromString("22222222-2222-4222-8222-222222222222").toString().trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : InviteParticipantDecision {}).decide(
                        InviteParticipantCommand(
                        federationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
                        federationName = null,
                        organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                        organizationName = null,
                        invitationNote = "Duplicate invitation"
                        ),
                            federationMembershipFederationIdOrganizationIdReservation = federationMembershipFederationIdOrganizationIdReservation
                    )
        }
    }
}
