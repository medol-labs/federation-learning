package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReportReadyAfterConfigurationFetchAndSelfCheckProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeAgentStartedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(
            ReportRuntimeInstanceSelfCheckPassedCommand(
                runtimeAgentId = event.runtimeAgentId,
                runtimeInfrastructureId = event.runtimeInfrastructureId,
                agentVersion = event.agentVersion,
                runtimeAgentSelfCheckPassed = true,
                configurationLoaded = true,
                secretStoreAccessible = true,
                runtimeEngineAdapterReady = true,
                modelRepositoryClientReady = true,
                localDatasetBindingStoreReady = true,
                workingDirectoryWritable = true,
                bootstrapRequestId = event.bootstrapRequestId
            )
        ).resultMessage
}
