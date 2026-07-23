package tech.medo.runtimegovernance.runtimecapability

import java.util.UUID;


data class RuntimeCapabilitySelection(
    val runtimeId: UUID
)

object RuntimeCapabilityTags {
    const val RUNTIME_ID = "runtimeId"
}

object RuntimeCapabilityMetadata {
    val concepts = listOf("RuntimeCapability")
}
