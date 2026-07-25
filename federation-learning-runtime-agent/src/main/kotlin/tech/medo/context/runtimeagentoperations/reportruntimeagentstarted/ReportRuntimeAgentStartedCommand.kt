package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.runtimeagentlifecycle.RuntimeAgentLifecycleSelection
import java.util.UUID;


@Command
data class ReportRuntimeAgentStartedCommand(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val agentVersion: String
) {
    @TargetEntityId
    val selection: RuntimeAgentLifecycleSelection = RuntimeAgentLifecycleSelection(runtimeAgentId = runtimeAgentId)

}
