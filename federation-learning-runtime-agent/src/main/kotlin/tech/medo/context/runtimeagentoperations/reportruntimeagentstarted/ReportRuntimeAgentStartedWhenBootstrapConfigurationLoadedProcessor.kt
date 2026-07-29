package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadedEvent
import tech.medo.runtimeagentoperations.reportruntimeagentstarted.ReportRuntimeAgentStartedCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReportRuntimeAgentStartedWhenBootstrapConfigurationLoadedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeAgentBootstrapConfigurationLoadedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ReportRuntimeAgentStartedCommand(runtimeAgentId = event.runtimeAgentId, runtimeInfrastructureId = event.runtimeInfrastructureId, agentVersion = event.agentVersion)).resultMessage
}
