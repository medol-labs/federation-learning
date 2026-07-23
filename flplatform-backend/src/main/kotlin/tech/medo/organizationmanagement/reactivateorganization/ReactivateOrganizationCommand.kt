package tech.medo.organizationmanagement.reactivateorganization

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.organizationmanagement.organization.OrganizationSelection
import java.util.UUID;


@Command
data class ReactivateOrganizationCommand(
    val organizationId: UUID,
    val reactivationReason: String
) {
    @TargetEntityId
    val selection: OrganizationSelection = OrganizationSelection(organizationId = organizationId)

}
