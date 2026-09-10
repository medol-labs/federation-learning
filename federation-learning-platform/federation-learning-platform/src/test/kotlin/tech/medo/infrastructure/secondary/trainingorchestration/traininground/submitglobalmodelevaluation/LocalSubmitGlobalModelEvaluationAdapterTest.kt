package tech.medo.infrastructure.secondary.trainingorchestration.traininground.submitglobalmodelevaluation

import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationInput
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationResult

class LocalSubmitGlobalModelEvaluationAdapterTest {
    @Test
    fun returnsLocalEvaluationMetrics() {
        val adapter = LocalSubmitGlobalModelEvaluationAdapter()
        val input = SubmitGlobalModelEvaluationInput(
            trainingJobId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            trainingRunConfigurationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            trainingJobObjective = "Train fraud detection model",
            featureSchemaId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            roundId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            roundNumber = 1,
            maxRounds = 1,
            minimumAccuracy = BigDecimal("0.90"),
            aggregatedModelId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            aggregatedModelArtifactUri = "file:///tmp/global_model.json",
            aggregatedModelRegistryRef = "local://federation-learning/global-models",
            modelFormat = "JSON",
            modelArtifactDigest = "abc123",
            aggregatedModelSignatureUri = null
        )

        val result = adapter.execute(input)

        assertTrue(result is SubmitGlobalModelEvaluationResult.Succeeded)
        result as SubmitGlobalModelEvaluationResult.Succeeded
        assertEquals("0.9000", result.globalAccuracy.toPlainString())
        assertEquals("1.0000", result.globalFairnessScore.toPlainString())
    }
}
