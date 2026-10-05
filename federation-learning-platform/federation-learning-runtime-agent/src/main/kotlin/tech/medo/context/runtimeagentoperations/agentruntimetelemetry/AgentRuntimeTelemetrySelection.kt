package tech.medo.runtimeagentoperations.agentruntimetelemetry

import java.util.UUID;


data class AgentRuntimeTelemetrySelection(
    val nodeId: UUID
)

object AgentRuntimeTelemetryTags {
    const val NODE_ID = "nodeId"
}

object AgentRuntimeTelemetryMetadata {
    val concepts = listOf("AgentRuntimeTelemetry")
}
