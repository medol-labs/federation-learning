package tech.medo.trainingorchestration.submitglobalmodelevaluation

import java.util.UUID;
import java.math.BigDecimal;

interface SubmitGlobalModelEvaluationService {
    fun supports(input: SubmitGlobalModelEvaluationInput): Boolean = true
    fun execute(input: SubmitGlobalModelEvaluationInput): SubmitGlobalModelEvaluationResult
}

data class SubmitGlobalModelEvaluationInput(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregatedModelId: UUID,
    val modelPlugin: String,
    val aggregatedModelArtifactUri: String,
    val aggregatedModelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val aggregatedModelSignatureUri: String?
)

sealed interface SubmitGlobalModelEvaluationResult {
    data class Succeeded(
        val globalAccuracy: BigDecimal,
        val globalFairnessScore: BigDecimal
    ) : SubmitGlobalModelEvaluationResult


}
