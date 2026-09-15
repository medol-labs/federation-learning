package tech.medo.runtimeagentoperations.profileagentdataset

import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-agent-operations-profile-agent-dataset")
@Component
class ProfileDatasetWhenAccessValidatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: AgentDatasetAccessValidatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ProfileAgentDatasetCommand(runtimeDatasetBindingId = event.runtimeDatasetBindingId, datasetId = event.datasetId, organizationId = event.organizationId, organizationName = event.organizationName, featureSchemaId = event.featureSchemaId, featureDomain = event.featureDomain, featureSchemaVersion = event.featureSchemaVersion, datasetName = event.datasetName, runtimeId = event.runtimeId, runtimeName = event.runtimeName)).resultMessage
}
