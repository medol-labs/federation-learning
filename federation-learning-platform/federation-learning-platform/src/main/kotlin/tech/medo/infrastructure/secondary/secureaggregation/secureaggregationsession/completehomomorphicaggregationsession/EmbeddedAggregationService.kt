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
        val uploadedFile = fileUploadClient.upload(
            supportEndpoint = properties.supportEndpoint,
            internalToken = properties.internalToken,
            fileId = aggregatedModelId,
            fileName = "${aggregatedModelId}.global_model.json",
            contentType = "application/json",
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
}

data class EmbeddedAggregationResult(
    val artifactUri: String,
    val registryRef: String,
    val digest: String,
    val sizeBytes: Int?
)
