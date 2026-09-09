package tech.medo.federationmanagement.activatefederation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.federationmanagement.federation.FederationSelection
import java.util.UUID;


@Command
data class ActivateFederationCommand(
    val federationId: UUID,
    val activationNote: String?,
    val federationName: String
) {
    @TargetEntityId
    val selection: FederationSelection = FederationSelection(federationName = federationName)

}
