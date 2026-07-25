package tech.medo.organizationmanagement.activateorganization

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.organizationmanagement.organization.OrganizationSelection
import java.util.UUID;


@Command
data class ActivateOrganizationCommand(
    val organizationId: UUID,
    val activationNote: String?
) {
    @TargetEntityId
    val selection: OrganizationSelection = OrganizationSelection(organizationId = organizationId)

}
