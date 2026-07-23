package tech.medo.runtimeagentoperations.reportagentruntimenodeinventory

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.agentruntimenodeinventory.AgentRuntimeNodeInventorySelection
import java.util.UUID;


@Command
data class ReportAgentRuntimeNodeInventoryCommand(
    val runtimeNodeInventoryReportId: UUID = java.util.UUID.randomUUID(),
    val organizationId: UUID,
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
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
    val selection: AgentRuntimeNodeInventorySelection = AgentRuntimeNodeInventorySelection(runtimeNodeInventoryReportId = runtimeNodeInventoryReportId)

}
