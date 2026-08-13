package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

import java.util.UUID;

interface RetryRoundExecutionAfterRuntimeFailureService {
    fun supports(input: RetryRoundExecutionAfterRuntimeFailureInput): Boolean = true
    fun execute(input: RetryRoundExecutionAfterRuntimeFailureInput): RetryRoundExecutionAfterRuntimeFailureResult
}

data class RetryRoundExecutionAfterRuntimeFailureInput(
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
    val runtimeEngineJobId: String,
    val retryReason: String
)

sealed interface RetryRoundExecutionAfterRuntimeFailureResult {
    class Succeeded : RetryRoundExecutionAfterRuntimeFailureResult


}
