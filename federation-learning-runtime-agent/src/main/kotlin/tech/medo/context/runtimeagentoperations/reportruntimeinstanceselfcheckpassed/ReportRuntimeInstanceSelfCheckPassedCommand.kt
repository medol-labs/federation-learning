package tech.medo.runtimeagentoperations.reportruntimeinstanceselfcheckpassed

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleSelection
import java.util.UUID;


@Command
data class ReportRuntimeInstanceSelfCheckPassedCommand(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val agentVersion: String,
    val runtimeAgentSelfCheckPassed: Boolean,
    val configurationLoaded: Boolean,
    val secretStoreAccessible: Boolean,
    val runtimeEngineAdapterReady: Boolean,
    val modelRepositoryClientReady: Boolean,
    val localDatasetBindingStoreReady: Boolean,
    val workingDirectoryWritable: Boolean,
    val bootstrapRequestId: UUID
) {
    @TargetEntityId
    val selection: RuntimeAgentLifecycleSelection = RuntimeAgentLifecycleSelection(bootstrapRequestId = bootstrapRequestId)

}
