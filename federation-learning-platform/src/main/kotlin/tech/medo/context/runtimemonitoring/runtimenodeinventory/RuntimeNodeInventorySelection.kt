package tech.medo.runtimemonitoring.runtimenodeinventory

import java.util.UUID;


data class RuntimeNodeInventorySelection(
    val nodeId: UUID
)

object RuntimeNodeInventoryTags {
    const val NODE_ID = "nodeId"
}

object RuntimeNodeInventoryMetadata {
    val concepts = listOf("RuntimeNodeInventory")
}
