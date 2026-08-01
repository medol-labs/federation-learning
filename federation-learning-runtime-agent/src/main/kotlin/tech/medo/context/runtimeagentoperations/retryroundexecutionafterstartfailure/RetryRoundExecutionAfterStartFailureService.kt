package tech.medo.runtimeagentoperations.retryroundexecutionafterstartfailure

import java.util.UUID;

interface RetryRoundExecutionAfterStartFailureService {
    fun supports(input: RetryRoundExecutionAfterStartFailureInput): Boolean = true
    fun execute(input: RetryRoundExecutionAfterStartFailureInput): RetryRoundExecutionAfterStartFailureResult
}

data class RetryRoundExecutionAfterStartFailureInput(
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
    val runtimeEngineJobId: String,
    val retryReason: String
)

sealed interface RetryRoundExecutionAfterStartFailureResult {
    class Succeeded : RetryRoundExecutionAfterStartFailureResult


}
