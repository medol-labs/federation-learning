package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-runtime-agent-operations-report-runtime-instance-self-check-passed")
@Component
class ReportReadyAfterConfigurationFetchAndSelfCheckProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeAgentStartedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ReportRuntimeInstanceSelfCheckPassedCommand(runtimeAgentId = event.runtimeAgentId, runtimeInfrastructureId = event.runtimeInfrastructureId, agentVersion = event.agentVersion, runtimeAgentEndpoint = event.runtimeAgentEndpoint, endpointScope = event.endpointScope, bootstrapRequestId = event.bootstrapRequestId)).resultMessage
}
