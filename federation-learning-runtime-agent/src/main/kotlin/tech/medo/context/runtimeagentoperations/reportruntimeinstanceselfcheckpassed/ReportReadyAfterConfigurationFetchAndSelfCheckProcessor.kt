package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent
import tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed.ReportRuntimeInstanceSelfCheckPassedCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class ReportReadyAfterConfigurationFetchAndSelfCheckProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: RuntimeAgentStartedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(ReportRuntimeInstanceSelfCheckPassedCommand(runtimeAgentId = event.runtimeAgentId, runtimeInfrastructureId = event.runtimeInfrastructureId, agentVersion = event.agentVersion, runtimeAgentSelfCheckPassed = false /* TODO: provide runtimeAgentSelfCheckPassed */, configurationLoaded = false /* TODO: provide configurationLoaded */, secretStoreAccessible = false /* TODO: provide secretStoreAccessible */, runtimeEngineAdapterReady = false /* TODO: provide runtimeEngineAdapterReady */, modelRepositoryClientReady = false /* TODO: provide modelRepositoryClientReady */, localDatasetBindingStoreReady = false /* TODO: provide localDatasetBindingStoreReady */, workingDirectoryWritable = false /* TODO: provide workingDirectoryWritable */)).resultMessage
}
