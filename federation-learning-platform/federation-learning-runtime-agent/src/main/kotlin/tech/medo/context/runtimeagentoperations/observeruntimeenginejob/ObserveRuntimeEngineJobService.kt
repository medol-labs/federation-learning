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
    val personalizationMode: String?,
    val personalizationScope: String?,
    val personalizedArtifactPolicy: String?,
    val personalizationMetric: String?,
    val minimumPersonalizationLift: BigDecimal?,
    val personalizationTargetType: String?,
    val personalizationTargetId: UUID?,
    val personalizationCohortId: UUID?,
    val personalizationProfileId: UUID?,
    val runtimeEngineJobId: String,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?
)

sealed interface ObserveRuntimeEngineJobResult {
    data class Succeeded(
        val observedStatus: String,
        val failureReason: String?,
        val localUpdateArtifactRef: String?,
        val encryptedUpdateArtifactRef: String?,
        val encryptedUpdateDigest: String?,
        val modelUpdateArtifactRef: String?,
        val modelUpdateArtifactDigest: String?,
        val updateProtectionType: String?,
        val metricsArtifactRef: String?,
        val trainingLoss: BigDecimal?,
        val personalizedArtifactAvailable: Boolean?,
        val personalizedModelId: UUID?,
        val personalizedModelArtifactRef: String?,
        val personalizedModelArtifactDigest: String?,
        val personalizedMetricsArtifactRef: String?,
        val personalizedAccuracy: BigDecimal?,
        val personalizationLift: BigDecimal?
    ) : ObserveRuntimeEngineJobResult


}
