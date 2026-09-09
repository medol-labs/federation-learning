package tech.medo.federationmanagement.reactivatefederation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.federationmanagement.federation.FederationSelection
import java.util.UUID;


@Command
data class ReactivateFederationCommand(
    val federationId: UUID,
    val reactivationReason: String,
    val federationName: String
) {
    @TargetEntityId
    val selection: FederationSelection = FederationSelection(federationName = federationName)

}
