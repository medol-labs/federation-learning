package tech.medo.runtimeagentoperations.observeruntimeenginejob

import java.util.UUID;
import java.math.BigDecimal;

interface ObserveRuntimeEngineJobService {
    fun supports(input: ObserveRuntimeEngineJobInput): Boolean = true
    fun execute(input: ObserveRuntimeEngineJobInput): ObserveRuntimeEngineJobResult
}

data class ObserveRuntimeEngineJobInput(
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
    val runtimeEngineJobId: String
)

sealed interface ObserveRuntimeEngineJobResult {
    data class Succeeded(
        val observedStatus: String,
        val failureReason: String?,
        val localUpdateArtifactRef: String?,
        val metricsArtifactRef: String?,
        val trainingLoss: BigDecimal?
    ) : ObserveRuntimeEngineJobResult


}
