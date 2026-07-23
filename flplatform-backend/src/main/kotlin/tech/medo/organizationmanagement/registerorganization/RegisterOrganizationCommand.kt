package tech.medo.organizationmanagement.registerorganization

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.organizationmanagement.organization.OrganizationSelection
import java.util.UUID;
import tech.medo.organizationmanagement.domain.types.OrganizationType;

import tech.medo.organizationmanagement.organization.OrganizationNameSelection

@Command
data class RegisterOrganizationCommand(
    val organizationId: UUID = java.util.UUID.randomUUID(),
    val organizationName: String,
    val organizationType: OrganizationType,
    val contactEmail: String
) {
    @TargetEntityId
    val selection: OrganizationSelection = OrganizationSelection(organizationId = organizationId)

    val organizationNameSelection: OrganizationNameSelection = OrganizationNameSelection(normalizedName = organizationName.trim().lowercase())
}
