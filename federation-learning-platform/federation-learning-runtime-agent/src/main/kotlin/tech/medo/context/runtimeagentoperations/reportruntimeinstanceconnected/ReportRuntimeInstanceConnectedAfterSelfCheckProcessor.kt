package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-agent-operations-report-runtime-instance-connected")
@Component
class ReportRuntimeInstanceConnectedAfterSelfCheckProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeInstanceSelfCheckPassedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ReportRuntimeInstanceConnectedCommand(runtimeInfrastructureId = event.runtimeInfrastructureId, runtimeAgentId = event.runtimeAgentId, runtimeAgentEndpoint = event.runtimeAgentEndpoint, endpointScope = event.endpointScope, runtimePlatformConnectionReady = false /* TODO: provide runtimePlatformConnectionReady */, platformApiReachable = false /* TODO: provide platformApiReachable */, agentAuthenticationSucceeded = false /* TODO: provide agentAuthenticationSucceeded */, controlChannelEstablished = false /* TODO: provide controlChannelEstablished */, heartbeatAccepted = false /* TODO: provide heartbeatAccepted */)).resultMessage
}
