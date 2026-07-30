package tech.medo.runtimeagentoperations.profileagentdataset

import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.profileagentdataset.ProfileAgentDatasetCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ProfileDatasetWhenAccessValidatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: AgentDatasetAccessValidatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ProfileAgentDatasetCommand(runtimeDatasetBindingId = event.runtimeDatasetBindingId)).resultMessage
}
