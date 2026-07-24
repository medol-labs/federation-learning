package tech.medo.organizationmanagement.registerorganization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.organizationmanagement.registerorganization.RegisterOrganizationCommand

import tech.medo.organizationmanagement.organization.OrganizationNameReservationState
import tech.medo.organizationmanagement.events.OrganizationNameReservedEvent


import java.util.UUID;
import tech.medo.organizationmanagement.domain.types.OrganizationType;


class RegisterOrganizationDecisionTest {
    @Test
    fun RejectDuplicateOrganization() {
        val organizationNameReservation = OrganizationNameReservationState()
        organizationNameReservation.evolve(
            OrganizationNameReservedEvent(
                organizationId = java.util.UUID.randomUUID(),
                organizationName = "Acme",
                normalizedName = "Acme".trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            RegisterOrganizationDecision().decide(
                        RegisterOrganizationCommand(
                        organizationId = java.util.UUID.randomUUID(),
                        organizationName = "Acme",
                        organizationType = OrganizationType.HOSPITAL,
                        contactEmail = ""
                        ),
                            organizationNameReservation = organizationNameReservation
                    )
        }
    }
}
