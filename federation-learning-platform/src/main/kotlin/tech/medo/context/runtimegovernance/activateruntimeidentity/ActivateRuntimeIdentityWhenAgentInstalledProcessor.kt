package tech.medo.runtimegovernance.activateruntimeidentity

import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimegovernance.activateruntimeidentity.ActivateRuntimeIdentityCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ActivateRuntimeIdentityWhenAgentInstalledProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeAgentInstallationSucceededEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ActivateRuntimeIdentityCommand(runtimeInfrastructureId = event.runtimeInfrastructureId, runtimeAgentId = event.runtimeAgentId, organizationId = java.util.UUID.randomUUID() /* TODO: provide organizationId */, runtimeName = "" /* TODO: provide runtimeName */)).resultMessage
}
