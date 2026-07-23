package tech.medo.runtimemonitoring.noderuntimehealth

import java.util.UUID;


data class NodeRuntimeHealthSelection(
    val nodeId: UUID
)

object NodeRuntimeHealthTags {
    const val NODE_ID = "nodeId"
}

object NodeRuntimeHealthMetadata {
    val concepts = listOf("NodeRuntimeHealth")
}
