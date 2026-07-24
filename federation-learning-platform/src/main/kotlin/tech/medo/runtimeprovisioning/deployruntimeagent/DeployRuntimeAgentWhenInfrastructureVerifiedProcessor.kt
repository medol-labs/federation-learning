package tech.medo.runtimeprovisioning.deployruntimeagent

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class DeployRuntimeAgentWhenInfrastructureVerifiedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeInfrastructureVerifiedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(DeployRuntimeAgentCommand(runtimeAgentId = java.util.UUID.randomUUID() /* TODO: provide runtimeAgentId */, runtimeInfrastructureId = event.runtimeInfrastructureId)).resultMessage
}
