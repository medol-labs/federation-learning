package tech.medo.runtimegovernance.runtimeidentity

import java.util.UUID;


data class RuntimeIdentitySelection(
    val runtimeId: UUID
)

object RuntimeIdentityTags {
    const val RUNTIME_ID = "runtimeId"
}

object RuntimeIdentityMetadata {
    val concepts = listOf("RuntimeIdentity")
}
