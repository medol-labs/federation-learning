package tech.medo.runtimeagentoperations.runtimeagentlifecycle

import java.util.UUID;


data class RuntimeAgentLifecycleSelection(
    val runtimeAgentId: UUID
)

object RuntimeAgentLifecycleTags {
    const val RUNTIME_AGENT_ID = "runtimeAgentId"
}

object RuntimeAgentLifecycleMetadata {
    val concepts = listOf("RuntimeAgentLifecycle")
}
