package tech.medo.runtimeprovisioning.runtimeinfrastructure

import java.util.UUID;


data class RuntimeInfrastructureSelection(
    val runtimeInfrastructureId: UUID
)

object RuntimeInfrastructureTags {
    const val RUNTIME_INFRASTRUCTURE_ID = "runtimeInfrastructureId"
}

object RuntimeInfrastructureMetadata {
    val concepts = listOf("RuntimeInfrastructure")
}
