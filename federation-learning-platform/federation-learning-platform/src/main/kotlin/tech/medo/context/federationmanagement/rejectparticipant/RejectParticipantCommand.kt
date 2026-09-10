package tech.medo.federationmanagement.rejectparticipant

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.federationmanagement.federationmembership.FederationMembershipSelection
import java.util.UUID;


@Command
data class RejectParticipantCommand(
    val federationId: UUID,
    val federationName: String?,
    val organizationId: UUID,
    val organizationName: String?,
    val rejectionReason: String
) {
    @TargetEntityId
    val selection: FederationMembershipSelection = FederationMembershipSelection(federationId = federationId, organizationId = organizationId)

}
