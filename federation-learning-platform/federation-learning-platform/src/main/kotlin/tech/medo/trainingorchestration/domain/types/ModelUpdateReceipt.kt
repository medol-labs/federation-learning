package tech.medo.trainingorchestration.domain.types

import java.util.UUID;


data class ModelUpdateReceipt(
    val roundExecutionId: UUID,
    val runtimeId: UUID,
    val updateArtifactId: UUID,
    val artifactDigest: String
)
