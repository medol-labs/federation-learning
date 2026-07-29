package tech.medo.runtimeprovisioning.recordruntimeconnectionestablished

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureSelection
import java.util.UUID;


@Command
data class RecordRuntimeConnectionEstablishedCommand(
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val agentInstallMode: String,
    val organizationId: UUID,
    val runtimeName: String
) {
    @TargetEntityId
    val selection: RuntimeInfrastructureSelection = RuntimeInfrastructureSelection(runtimeInfrastructureId = runtimeInfrastructureId)

}
