package tech.medo.infrastructure.secondary.trainingorchestration.traininground.submitglobalmodelevaluation

import com.fasterxml.jackson.databind.JsonNode
import java.net.URI
import java.util.Base64
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeProperties
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationInput
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationResult
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationService

@Component
class LocalSubmitGlobalModelEvaluationAdapter(
    builder: RestClient.Builder,
    private val properties: AggregationRuntimeProperties
) : SubmitGlobalModelEvaluationService {
    private val client = builder.build()

    override fun supports(input: SubmitGlobalModelEvaluationInput): Boolean = true

    override fun execute(input: SubmitGlobalModelEvaluationInput): SubmitGlobalModelEvaluationResult {
        val endpoint = properties.requireRuntimeEngineEndpoint()
        val path = "/api/files/${input.aggregatedModelId}/content"
        require(URI.create(input.aggregatedModelArtifactUri).path.endsWith(path)) { "Unexpected aggregate file reference" }
        val model = requireNotNull(client.get().uri(properties.supportEndpoint.trimEnd('/') + path)
            .header("X-MEDOL-INTERNAL-TOKEN", properties.internalToken).retrieve().body(ByteArray::class.java))
        val result = requireNotNull(client.post().uri("$endpoint/evaluations")
            .body(mapOf("modelContent" to Base64.getEncoder().encodeToString(model),
                "modelArtifactDigest" to input.modelArtifactDigest, "modelPlugin" to input.modelPlugin,
                "featureSchemaId" to input.featureSchemaId.toString()))
            .retrieve().body(JsonNode::class.java))
        require(result.required("modelArtifactDigest").asText() == input.modelArtifactDigest.lowercase().removePrefix("sha256:"))
        fun count(key: String): Int = result.required(key).let {
            require(it.isIntegralNumber && it.canConvertToInt() && it.intValue() >= 0)
            it.intValue()
        }
        val samples = count("evaluatedSampleCount")
        val tp = count("truePositiveCount")
        val tn = count("trueNegativeCount")
        val fp = count("falsePositiveCount")
        val fn = count("falseNegativeCount")
        require(samples > 0 && tp.toLong() + tn + fp + fn == samples.toLong())
        return SubmitGlobalModelEvaluationResult.Succeeded(
            globalAccuracy = (tp + tn).toBigDecimal().divide(samples.toBigDecimal(), java.math.MathContext.DECIMAL64),
            globalFairnessScore = null,
            validationDatasetVersion = result.required("validationDatasetVersion").textValue().also { require(!it.isNullOrBlank()) },
            validationDatasetDigest = result.required("validationDatasetDigest").asText().also { require(it.matches(Regex("[0-9a-f]{64}"))) },
            evaluatedSampleCount = samples,
            truePositiveCount = tp, trueNegativeCount = tn, falsePositiveCount = fp, falseNegativeCount = fn
        )
    }
}
