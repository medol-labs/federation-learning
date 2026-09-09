package tech.medo.runtimeagentoperations.runtimeagentlifecycle

import java.util.UUID;


data class RuntimeAgentLifecycleSelection(
    val bootstrapRequestId: UUID
)

object RuntimeAgentLifecycleTags {
    const val BOOTSTRAP_REQUEST_ID = "bootstrapRequestId"
}

object RuntimeAgentLifecycleMetadata {
    val concepts = listOf("RuntimeAgentLifecycle")
}
