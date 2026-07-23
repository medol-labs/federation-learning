package tech.medo.federationmanagement.createfederation

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.federationmanagement.federation.FederationSelection
import java.util.UUID;

import tech.medo.federationmanagement.federation.FederationNameSelection

@Command
data class CreateFederationCommand(
    val federationId: UUID = java.util.UUID.randomUUID(),
    val federationName: String,
    val description: String,
    val minimumParticipantCount: Int
) {
    @TargetEntityId
    val selection: FederationSelection = FederationSelection(federationId = federationId)

    val federationNameSelection: FederationNameSelection = FederationNameSelection(normalizedName = federationName.trim().lowercase())
}
