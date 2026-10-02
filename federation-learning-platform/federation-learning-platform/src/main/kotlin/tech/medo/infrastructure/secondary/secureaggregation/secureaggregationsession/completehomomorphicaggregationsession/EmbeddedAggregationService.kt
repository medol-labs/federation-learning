package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.UUID

@Component
class EmbeddedAggregationService(
    private val artifactClient: AggregatedModelUpdateArtifactClient,
    private val fileUploadClient: AggregatedModelFileUploadClient,
    private val objectMapper: ObjectMapper,
    private val properties: AggregationRuntimeProperties
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun aggregate(
        aggregatedModelId: UUID,
        trainingJobId: UUID,
        roundNumber: Int,
        artifactRefs: List<String>,
        aggregationAlgorithm: String,
        secureAggregation: Boolean
    ): EmbeddedAggregationResult {
        require(artifactRefs.isNotEmpty()) {
            "At least one model update artifact is required for embedded aggregation."
        }
        val updates = artifactRefs.map { ref ->
            ref to artifactClient.download(properties.supportEndpoint, ref)
        }
        val pytorchStateDictAggregation = isPytorchStateDictAggregation(aggregationAlgorithm)
        require(!pytorchStateDictAggregation || updates.size == 1) {
            "Embedded PyTorch state_dict aggregation supports a single local update only. " +
                "Use runtime-engine aggregation for multiple PyTorch updates."
        }
        val modelBytes = if (updates.size == 1) {
            updates.single().second
        } else {
            objectMapper.writeValueAsBytes(
                mapOf(
                    "aggregationMode" to "embedded",
                    "aggregationAlgorithm" to aggregationAlgorithm,
                    "updateCount" to updates.size,
                    "updates" to updates.map { (ref, bytes) ->
                        mapOf(
                            "artifactRef" to ref,
                            "contentBase64" to Base64.getEncoder().encodeToString(bytes)
                        )
                    }
                )
            )
        }
        require(!pytorchStateDictAggregation || !looksLikeJson(modelBytes)) {
            "Embedded PyTorch state_dict aggregation expected a .pt model update artifact, but received JSON content."
        }
        val uploadedFile = fileUploadClient.upload(
            supportEndpoint = properties.supportEndpoint,
            internalToken = properties.internalToken,
            fileId = aggregatedModelId,
            fileName = aggregatedModelFileName(aggregatedModelId, pytorchStateDictAggregation),
            contentType = aggregatedModelContentType(pytorchStateDictAggregation),
            content = modelBytes
        )

        log.info(
            "Stored embedded aggregated model. trainingJobId={}, roundNumber={}, aggregatedModelId={}, updateCount={}, secureAggregation={}, artifactUri={}, digest={}, sizeBytes={}",
            trainingJobId,
            roundNumber,
            aggregatedModelId,
            updates.size,
            secureAggregation,
            uploadedFile.artifactUri,
            uploadedFile.digest,
            uploadedFile.sizeBytes
        )
        return EmbeddedAggregationResult(
            artifactUri = uploadedFile.artifactUri,
            registryRef = uploadedFile.registryRef,
            digest = uploadedFile.digest,
            sizeBytes = uploadedFile.sizeBytes?.let(Math::toIntExact)
        )
    }

    private fun aggregatedModelFileName(aggregatedModelId: UUID, pytorchStateDictAggregation: Boolean): String =
        if (pytorchStateDictAggregation) {
            "$aggregatedModelId.global_model_state_dict.pt"
        } else {
            "$aggregatedModelId.global_model.json"
        }

    private fun aggregatedModelContentType(pytorchStateDictAggregation: Boolean): String =
        if (pytorchStateDictAggregation) {
            "application/octet-stream"
        } else {
            "application/json"
        }

    private fun isPytorchStateDictAggregation(aggregationAlgorithm: String): Boolean =
        aggregationAlgorithm.contains("PYTORCH", ignoreCase = true) ||
            aggregationAlgorithm.contains("TORCH", ignoreCase = true)

    private fun looksLikeJson(bytes: ByteArray): Boolean =
        bytes.firstOrNull { !it.toInt().toChar().isWhitespace() } == '{'.code.toByte()
}

data class EmbeddedAggregationResult(
    val artifactUri: String,
    val registryRef: String,
    val digest: String,
    val sizeBytes: Int?
)
