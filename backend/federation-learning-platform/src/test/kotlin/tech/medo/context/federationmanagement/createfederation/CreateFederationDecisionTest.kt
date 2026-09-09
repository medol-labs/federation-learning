package tech.medo.federationmanagement.createfederation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.federationmanagement.createfederation.CreateFederationCommand
import tech.medo.federationmanagement.federation.FederationNameReservationState
import tech.medo.federationmanagement.events.FederationNameReservedEvent
import java.util.UUID

class CreateFederationDecisionTest {
    @Test
    fun RejectDuplicateFederation() {
        val federationNameReservation = FederationNameReservationState()
        federationNameReservation.evolve(
            FederationNameReservedEvent(
                federationId = java.util.UUID.randomUUID(),
                federationName = "B",
                normalizedName = "B".trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : CreateFederationDecision {}).decide(
                        CreateFederationCommand(
                        federationId = java.util.UUID.randomUUID(),
                        federationName = "B",
                        description = "",
                        minimumParticipantCount = 0
                        ),
                            federationNameReservation = federationNameReservation
                    )
        }
    }
}
