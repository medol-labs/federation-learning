package tech.medo.runtimeagentoperations.startroundexecution

import java.util.UUID;

interface StartRoundExecutionService {
    fun supports(input: StartRoundExecutionInput): Boolean = true
    fun execute(input: StartRoundExecutionInput): StartRoundExecutionResult
}

data class StartRoundExecutionInput(
    val roundExecutionId: UUID,
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val baseModelId: UUID,
    val baseModelArtifactUri: String,
    val baseModelRegistryRef: String,
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?
)

sealed interface StartRoundExecutionResult {
    data class Succeeded(
        val runtimeEngineJobId: String
    ) : StartRoundExecutionResult

    data class Rejected(
        val runtimeEngineJobId: String?,
        val failureReason: String
    ) : StartRoundExecutionResult

    data class Unavailable(
        val failureReason: String
    ) : StartRoundExecutionResult
}
