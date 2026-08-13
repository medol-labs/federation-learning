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
    val baseModelVersionId: UUID,
    val runtimeEngineJobId: String
)

sealed interface StartRoundExecutionResult {
    class Succeeded : StartRoundExecutionResult

    data class Rejected(
        val failureReason: String
    ) : StartRoundExecutionResult

    data class Unavailable(
        val failureReason: String
    ) : StartRoundExecutionResult
}
