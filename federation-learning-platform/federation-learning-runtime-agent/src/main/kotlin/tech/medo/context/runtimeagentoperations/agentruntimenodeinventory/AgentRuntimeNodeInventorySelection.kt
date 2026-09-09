package tech.medo.runtimeagentoperations.agentruntimenodeinventory

import java.util.UUID;


data class AgentRuntimeNodeInventorySelection(
    val runtimeNodeInventoryReportId: UUID
)

object AgentRuntimeNodeInventoryTags {
    const val RUNTIME_NODE_INVENTORY_REPORT_ID = "runtimeNodeInventoryReportId"
}

object AgentRuntimeNodeInventoryMetadata {
    val concepts = listOf("AgentRuntimeNodeInventory")
}
