package tech.medo.federationmanagement.removeparticipant

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.federationmanagement.federationmembership.FederationMembershipSelection
import java.util.UUID;


@Command
data class RemoveParticipantCommand(
    val federationId: UUID,
    val federationName: String?,
    val organizationId: UUID,
    val organizationName: String?,
    val removalReason: String
) {
    @TargetEntityId
    val selection: FederationMembershipSelection = FederationMembershipSelection(federationId = federationId, organizationId = organizationId)

}
