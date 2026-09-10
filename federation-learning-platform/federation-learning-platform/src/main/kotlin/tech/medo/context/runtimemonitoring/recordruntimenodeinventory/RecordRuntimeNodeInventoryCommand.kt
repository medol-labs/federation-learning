package tech.medo.runtimemonitoring.recordruntimenodeinventory

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.runtimenodeinventory.RuntimeNodeInventorySelection
import java.util.UUID;


@Command
data class RecordRuntimeNodeInventoryCommand(
    val nodeId: UUID,
    val runtimeNodeInventoryReportId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val runtimeName: String?,
    val runtimeNodeName: String,
    val infrastructureNodeId: String?,
    val runtimeNodeRole: String,
    val nodeReady: Boolean,
    val runtimeEngineVersion: String?,
    val containerEngineVersion: String?,
    val operatingSystem: String?,
    val architecture: String,
    val inventoryHash: String
) {
    @TargetEntityId
    val selection: RuntimeNodeInventorySelection = RuntimeNodeInventorySelection(nodeId = nodeId)

}
