package tech.medo.organizationmanagement.activateorganization

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.organizationmanagement.organization.OrganizationSelection
import java.util.UUID;


@Command
data class ActivateOrganizationCommand(
    val organizationId: UUID,
    val organizationName: String,
    val activationNote: String?
) {
    @TargetEntityId
    val selection: OrganizationSelection = OrganizationSelection(organizationName = organizationName.trim().lowercase())

    val organizationNameEntityId: String = organizationName.trim().lowercase()

}
