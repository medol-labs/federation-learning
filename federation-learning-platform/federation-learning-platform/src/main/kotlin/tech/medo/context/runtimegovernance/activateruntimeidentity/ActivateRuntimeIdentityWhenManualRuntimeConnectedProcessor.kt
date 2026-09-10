package tech.medo.runtimegovernance.activateruntimeidentity

import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.runtimegovernance.activateruntimeidentity.ActivateRuntimeIdentityCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ActivateRuntimeIdentityWhenManualRuntimeConnectedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeConnectionEstablishedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.agentInstallMode == "MANUAL_GUIDED") {
            commandGateway.send(ActivateRuntimeIdentityCommand(runtimeInfrastructureId = event.runtimeInfrastructureId, runtimeAgentId = event.runtimeAgentId, organizationId = event.organizationId, organizationName = event.organizationName, runtimeName = event.runtimeName)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
