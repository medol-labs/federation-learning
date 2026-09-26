package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionInput
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult
import java.math.BigDecimal
import java.time.Duration
import java.util.UUID

class RuntimeEngineCompleteHomomorphicAggregationSessionAdapterTest {
    @Test
    fun aggregatesUpdatesAndStoresGlobalModelInSupport() {
        val runtimeClient = FakeAggregationRuntimeEngineClient()
        val fileUploadClient = FakeAggregatedModelFileUploadClient()
        val adapter = RuntimeEngineCompleteHomomorphicAggregationSessionAdapter(
            runtimeEngineClient = runtimeClient,
            fileUploadClient = fileUploadClient,
            embeddedAggregationService = EmbeddedAggregationService(
                artifactClient = FakeAggregatedModelUpdateArtifactClient(),
                fileUploadClient = fileUploadClient,
                objectMapper = ObjectMapper(),
                properties = AggregationRuntimeProperties(supportEndpoint = "http://support")
            ),
            properties = AggregationRuntimeProperties(
                mode = "runtime-engine",
                runtimeEngineEndpoint = "http://runtime-engine",
                supportEndpoint = "http://support",
                pollInterval = Duration.ZERO
            )
        )
        val aggregatedModelId = UUID.fromString("66666666-6666-4666-8666-666666666666")
        val result = adapter.execute(
            CompleteHomomorphicAggregationSessionInput(
                secureAggregationSessionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
                trainingJobId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                trainingRunConfigurationId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
                trainingJobObjective = "Train fraud detection model",
                featureSchemaId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
                roundId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
                roundNumber = 1,
                maxRounds = 3,
                minimumAccuracy = BigDecimal("0.90"),
                modelPlugin = "SKLEARN_LOGISTIC_REGRESSION",
                aggregatedModelId = aggregatedModelId,
                encryptedUpdateArtifactRefs = listOf(
                    "http://support/api/files/update-a/content",
                    "http://support/api/files/update-b/content"
                )
            )
        )

        assertTrue(result is CompleteHomomorphicAggregationSessionResult.Succeeded)
        result as CompleteHomomorphicAggregationSessionResult.Succeeded
        assertEquals("FED_AVG", runtimeClient.submittedRequest?.modelParameter?.get("aggregationAlgorithm"))
        assertEquals(2, (runtimeClient.submittedRequest?.input?.get("updates") as List<*>).size)
        assertEquals(runtimeClient.artifactBytes.toList(), fileUploadClient.uploadedContent?.toList())
        assertEquals(aggregatedModelId, fileUploadClient.uploadedFileId)
        assertEquals("http://support/api/files/$aggregatedModelId/content", result.aggregatedModelArtifactUri)
        assertEquals("FEDERATED_TRAINING", result.modelSourceType)
        assertEquals("round-1", result.aggregatedModelVersion)
    }
}

private class FakeAggregatedModelUpdateArtifactClient : AggregatedModelUpdateArtifactClient {
    override fun download(supportEndpoint: String, artifactRef: String): ByteArray =
        "{\"update\":\"$artifactRef\"}".toByteArray()
}

private class FakeAggregationRuntimeEngineClient : AggregationRuntimeEngineClient {
    var submittedRequest: AggregationRuntimeJobRequest? = null
    val artifactBytes = "{\"weights\":[0.2],\"bias\":0.1}".toByteArray()

    override fun submit(endpoint: String, request: AggregationRuntimeJobRequest): AggregationRuntimeJobResponse {
        submittedRequest = request
        return AggregationRuntimeJobResponse(request.jobId, request.nodeName, "running")
    }

    override fun getJob(endpoint: String, jobId: String): AggregationRuntimeJobResponse =
        AggregationRuntimeJobResponse(jobId, "platform-aggregator", "completed", exitCode = 0)

    override fun downloadArtifact(endpoint: String, jobId: String, artifactName: String): ByteArray = artifactBytes
}

private class FakeAggregatedModelFileUploadClient : AggregatedModelFileUploadClient {
    var uploadedFileId: UUID? = null
    var uploadedContent: ByteArray? = null

    override fun upload(
        supportEndpoint: String,
        internalToken: String,
        fileId: UUID,
        fileName: String,
        contentType: String,
        content: ByteArray
    ): UploadedAggregatedModelFile {
        uploadedFileId = fileId
        uploadedContent = content
        return UploadedAggregatedModelFile(
            fileId = fileId,
            fileLocation = "$supportEndpoint/api/files/$fileId/content",
            originalFileName = fileName,
            contentType = contentType,
            sizeBytes = content.size.toLong(),
            checksum = "sha256:test"
        )
    }
}
