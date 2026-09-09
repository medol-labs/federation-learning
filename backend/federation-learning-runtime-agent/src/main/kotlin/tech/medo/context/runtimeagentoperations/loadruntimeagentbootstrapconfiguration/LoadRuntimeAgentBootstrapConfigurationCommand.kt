package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleSelection
import java.util.UUID;


@Command
data class LoadRuntimeAgentBootstrapConfigurationCommand(
    val bootstrapRequestId: UUID = java.util.UUID.randomUUID()
) {
    @TargetEntityId
    val selection: RuntimeAgentLifecycleSelection = RuntimeAgentLifecycleSelection(bootstrapRequestId = bootstrapRequestId)

}
