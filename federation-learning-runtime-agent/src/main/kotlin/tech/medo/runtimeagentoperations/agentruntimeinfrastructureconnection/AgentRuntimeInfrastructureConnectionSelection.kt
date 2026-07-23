package tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnection

import java.util.UUID;


data class AgentRuntimeInfrastructureConnectionSelection(
    val runtimeInfrastructureId: UUID
)

object AgentRuntimeInfrastructureConnectionTags {
    const val RUNTIME_INFRASTRUCTURE_ID = "runtimeInfrastructureId"
}

object AgentRuntimeInfrastructureConnectionMetadata {
    val concepts = listOf("AgentRuntimeInfrastructureConnection")
}
