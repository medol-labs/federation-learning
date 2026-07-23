package tech.medo.organizationmanagement.registerorganization

import org.springframework.stereotype.Component
import tech.medo.organizationmanagement.registerorganization.RegisterOrganizationCommand

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.organizationmanagement.events.OrganizationNameReservedEvent
import tech.medo.organizationmanagement.organization.OrganizationState

import tech.medo.organizationmanagement.organization.OrganizationNameReservationState



@Component
class RegisterOrganizationDecision {
    fun decide(command: RegisterOrganizationCommand, organizationNameReservation: OrganizationNameReservationState): List<Any> {
        require(!organizationNameReservation.reserved) {
            "Name already exists."
        }
        return listOf(
            OrganizationNameReservedEvent(organizationId = command.organizationId, organizationName = command.organizationName, normalizedName = command.organizationName.trim().lowercase()),
            OrganizationRegisteredEvent(organizationId = command.organizationId, organizationName = command.organizationName, organizationType = command.organizationType, contactEmail = command.contactEmail)
        )
    }
}
