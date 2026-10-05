package tech.medo.runtimeagentoperations.agentruntimenoderesourcetelemetry

import java.util.UUID;


data class AgentRuntimeNodeResourceTelemetrySelection(
    val nodeId: UUID
)

object AgentRuntimeNodeResourceTelemetryTags {
    const val NODE_ID = "nodeId"
}

object AgentRuntimeNodeResourceTelemetryMetadata {
    val concepts = listOf("AgentRuntimeNodeResourceTelemetry")
}
