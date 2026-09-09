package tech.medo.runtimemonitoring.runtimenodecapacity

import java.util.UUID;


data class RuntimeNodeCapacitySelection(
    val nodeId: UUID
)

object RuntimeNodeCapacityTags {
    const val NODE_ID = "nodeId"
}

object RuntimeNodeCapacityMetadata {
    val concepts = listOf("RuntimeNodeCapacity")
}
