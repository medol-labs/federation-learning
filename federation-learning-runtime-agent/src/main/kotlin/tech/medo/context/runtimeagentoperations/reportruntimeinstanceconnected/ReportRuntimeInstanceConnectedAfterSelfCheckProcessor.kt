package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent

@Component
class ReportRuntimeInstanceConnectedAfterSelfCheckProcessor(
    private val commandGateway: CommandGateway,
    @Value("\${runtime-agent.platform-connection-reporting.runtime-agent-endpoint:http://localhost:8082}")
    private val runtimeAgentEndpoint: String,
    @Value("\${runtime-agent.platform-connection-reporting.endpoint-scope:LOCAL}")
    private val endpointScope: String
) {
    @EventHandler
    fun on(event: RuntimeInstanceSelfCheckPassedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(
            ReportRuntimeInstanceConnectedCommand(
                runtimeInfrastructureId = event.runtimeInfrastructureId,
                runtimeAgentId = event.runtimeAgentId,
                runtimeAgentEndpoint = runtimeAgentEndpoint.trim(),
                endpointScope = endpointScope.trim(),
                runtimePlatformConnectionReady = true,
                platformApiReachable = true,
                agentAuthenticationSucceeded = true,
                controlChannelEstablished = true,
                heartbeatAccepted = true
            )
        ).resultMessage
}
