package tech.medo.runtimeprovisioning.deployruntimeagent

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureSelection
import java.util.UUID;


@Command
data class DeployRuntimeAgentCommand(
    val runtimeAgentId: UUID = java.util.UUID.randomUUID(),
    val runtimeInfrastructureId: UUID
) {
    @TargetEntityId
    val selection: RuntimeInfrastructureSelection = RuntimeInfrastructureSelection(runtimeInfrastructureId = runtimeInfrastructureId)

}
