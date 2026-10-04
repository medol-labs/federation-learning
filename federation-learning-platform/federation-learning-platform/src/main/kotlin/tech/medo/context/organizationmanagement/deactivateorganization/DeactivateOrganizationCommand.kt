package tech.medo.organizationmanagement.deactivateorganization

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.organizationmanagement.organization.OrganizationSelection
import java.util.UUID;


@Command
data class DeactivateOrganizationCommand(
    val organizationId: UUID,
    val organizationName: String,
    val deactivationReason: String
) {
    @TargetEntityId
    val selection: OrganizationSelection = OrganizationSelection(organizationName = organizationName.trim().lowercase())

    val organizationNameEntityId: String = organizationName.trim().lowercase()

}
