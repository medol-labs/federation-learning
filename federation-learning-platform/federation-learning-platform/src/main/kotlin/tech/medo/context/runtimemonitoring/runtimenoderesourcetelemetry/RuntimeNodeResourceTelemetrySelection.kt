package tech.medo.runtimemonitoring.runtimenoderesourcetelemetry

import java.util.UUID;


data class RuntimeNodeResourceTelemetrySelection(
    val nodeId: UUID
)

object RuntimeNodeResourceTelemetryTags {
    const val NODE_ID = "nodeId"
}

object RuntimeNodeResourceTelemetryMetadata {
    val concepts = listOf("RuntimeNodeResourceTelemetry")
}
