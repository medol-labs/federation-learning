package tech.medo.runtimemonitoring.runtimenoderesourcepressure

import java.util.UUID;


data class RuntimeNodeResourcePressureSelection(
    val nodeId: UUID
)

object RuntimeNodeResourcePressureTags {
    const val NODE_ID = "nodeId"
}

object RuntimeNodeResourcePressureMetadata {
    val concepts = listOf("RuntimeNodeResourcePressure")
}
