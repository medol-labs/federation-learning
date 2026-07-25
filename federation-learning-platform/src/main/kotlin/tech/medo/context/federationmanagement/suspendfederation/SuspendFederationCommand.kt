package tech.medo.federationmanagement.suspendfederation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.federationmanagement.federation.FederationSelection
import java.util.UUID;


@Command
data class SuspendFederationCommand(
    val federationId: UUID,
    val suspensionReason: String
) {
    @TargetEntityId
    val selection: FederationSelection = FederationSelection(federationId = federationId)

}
