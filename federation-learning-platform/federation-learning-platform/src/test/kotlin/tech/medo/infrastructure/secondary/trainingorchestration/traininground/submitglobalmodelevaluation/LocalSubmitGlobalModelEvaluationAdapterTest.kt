package tech.medo.infrastructure.secondary.trainingorchestration.traininground.submitglobalmodelevaluation

import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestClient
import org.springframework.http.MediaType
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeProperties
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationInput
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationResult

class LocalSubmitGlobalModelEvaluationAdapterTest {
    @Test
    fun returnsLocalEvaluationMetrics() {
        val builder = RestClient.builder()
        val server = MockRestServiceServer.bindTo(builder).build()
        val digest = "a".repeat(64)
        val path = "/api/files/55555555-5555-4555-8555-555555555555/content"
        server.expect(requestTo("http://support$path")).andRespond(withSuccess("{}", MediaType.APPLICATION_JSON))
        server.expect(requestTo("http://engine/evaluations")).andRespond(withSuccess(
            """{"modelArtifactDigest":"$digest","validationDatasetVersion":"v1","validationDatasetDigest":"$digest","evaluatedSampleCount":4,"truePositiveCount":1,"trueNegativeCount":2,"falsePositiveCount":1,"falseNegativeCount":0}""",
            MediaType.APPLICATION_JSON))
        val adapter = LocalSubmitGlobalModelEvaluationAdapter(builder,
            AggregationRuntimeProperties(supportEndpoint = "http://support", runtimeEngineEndpoint = "http://engine"))
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
            modelPlugin = "SKLEARN_LOGISTIC_REGRESSION",
            aggregatedModelArtifactUri = "http://public$path",
            aggregatedModelRegistryRef = "local://federation-learning/global-models",
            modelFormat = "JSON",
            modelArtifactDigest = digest,
            aggregatedModelSignatureUri = null
        )

        val result = adapter.execute(input)

        assertTrue(result is SubmitGlobalModelEvaluationResult.Succeeded)
        result as SubmitGlobalModelEvaluationResult.Succeeded
        assertEquals("0.75", result.globalAccuracy.toPlainString())
        org.junit.jupiter.api.Assertions.assertNull(result.globalFairnessScore)
        server.verify()
    }
}
