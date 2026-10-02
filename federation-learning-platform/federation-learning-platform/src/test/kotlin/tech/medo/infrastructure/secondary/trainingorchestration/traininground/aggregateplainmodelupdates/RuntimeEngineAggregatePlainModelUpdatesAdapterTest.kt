package tech.medo.infrastructure.secondary.trainingorchestration.traininground.aggregateplainmodelupdates

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregatedModelFileUploadClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregatedModelUpdateArtifactClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeEngineClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeJobRequest
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeJobResponse
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeProperties
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.EmbeddedAggregationService
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.UploadedAggregatedModelFile
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesInput
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import java.math.BigDecimal
import java.time.Duration
import java.util.UUID

class RuntimeEngineAggregatePlainModelUpdatesAdapterTest {
    @Test
    fun aggregatesPlainUpdatesWithTheTrainingConfigurationAlgorithm() {
        val runtimeClient = PlainAggregationRuntimeClient()
        val uploadClient = PlainAggregationUploadClient()
        val adapter = RuntimeEngineAggregatePlainModelUpdatesAdapter(
            runtimeEngineClient = runtimeClient,
            fileUploadClient = uploadClient,
            embeddedAggregationService = EmbeddedAggregationService(
                artifactClient = PlainAggregatedModelUpdateArtifactClient(),
                fileUploadClient = uploadClient,
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
            AggregatePlainModelUpdatesInput(
                trainingJobId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                trainingRunConfigurationId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
                trainingJobObjective = "Train fraud detection model",
                featureSchemaId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
                roundId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
                roundNumber = 1,
                maxRounds = 1,
                minimumAccuracy = BigDecimal("0.90"),
                aggregationAlgorithm = "FED_AVG_PYTORCH_STATE_DICT",
                modelPlugin = "PYTORCH_RESNET_CLASSIFIER",
                aggregatedModelId = aggregatedModelId,
                modelUpdateArtifactRefs = listOf("/workspace/updates/local-update.json")
            )
        ) as AggregatePlainModelUpdatesResult.Succeeded

        assertEquals(
            "FED_AVG_PYTORCH_STATE_DICT",
            runtimeClient.submittedRequest?.modelParameter?.get("aggregationAlgorithm")
        )
        assertEquals(
            listOf("/workspace/updates/local-update.json"),
            runtimeClient.submittedRequest?.input?.get("updates")
        )
        assertEquals(aggregatedModelId, uploadClient.uploadedFileId)
        assertEquals("66666666-6666-4666-8666-666666666666.global_model_state_dict.pt", uploadClient.uploadedFileName)
        assertEquals("application/octet-stream", uploadClient.uploadedContentType)
        assertEquals("PYTORCH_STATE_DICT", result.modelFormat)
        assertEquals("http://support/api/files/$aggregatedModelId/content", result.aggregatedModelArtifactUri)
    }

    @Test
    fun embeddedPyTorchAggregationUploadsThePtArtifactForSingleNodeDevFlow() {
        val uploadClient = PlainAggregationUploadClient()
        val adapter = RuntimeEngineAggregatePlainModelUpdatesAdapter(
            runtimeEngineClient = PlainAggregationRuntimeClient(),
            fileUploadClient = uploadClient,
            embeddedAggregationService = EmbeddedAggregationService(
                artifactClient = PlainAggregatedModelUpdateArtifactClient("pt-state-dict".toByteArray()),
                fileUploadClient = uploadClient,
                objectMapper = ObjectMapper(),
                properties = AggregationRuntimeProperties(supportEndpoint = "http://support")
            ),
            properties = AggregationRuntimeProperties(
                mode = "embedded",
                supportEndpoint = "http://support",
                pollInterval = Duration.ZERO
            )
        )
        val aggregatedModelId = UUID.fromString("66666666-6666-4666-8666-666666666666")

        val result = adapter.execute(
            AggregatePlainModelUpdatesInput(
                trainingJobId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                trainingRunConfigurationId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
                trainingJobObjective = "Train image model",
                featureSchemaId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
                roundId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
                roundNumber = 1,
                maxRounds = 1,
                minimumAccuracy = BigDecimal.ZERO,
                aggregationAlgorithm = "FED_AVG_PYTORCH_STATE_DICT",
                modelPlugin = "PYTORCH_TORCHVISION_DENSENET121_CLASSIFIER",
                aggregatedModelId = aggregatedModelId,
                modelUpdateArtifactRefs = listOf("http://support/api/files/local-update/content")
            )
        ) as AggregatePlainModelUpdatesResult.Succeeded

        assertEquals(aggregatedModelId, uploadClient.uploadedFileId)
        assertEquals("66666666-6666-4666-8666-666666666666.global_model_state_dict.pt", uploadClient.uploadedFileName)
        assertEquals("application/octet-stream", uploadClient.uploadedContentType)
        assertEquals("PYTORCH_STATE_DICT", result.modelFormat)
    }
}

private class PlainAggregatedModelUpdateArtifactClient(
    private val content: ByteArray = "{\"update\":\"local\"}".toByteArray()
) : AggregatedModelUpdateArtifactClient {
    override fun download(supportEndpoint: String, artifactRef: String): ByteArray =
        content
}

private class PlainAggregationRuntimeClient : AggregationRuntimeEngineClient {
    var submittedRequest: AggregationRuntimeJobRequest? = null

    override fun submit(endpoint: String, request: AggregationRuntimeJobRequest): AggregationRuntimeJobResponse {
        submittedRequest = request
        return AggregationRuntimeJobResponse(request.jobId, request.nodeName, "running")
    }

    override fun getJob(endpoint: String, jobId: String): AggregationRuntimeJobResponse =
        AggregationRuntimeJobResponse(jobId, "platform-aggregator", "completed", exitCode = 0)

    override fun downloadArtifact(endpoint: String, jobId: String, artifactName: String): ByteArray {
        assertEquals("weightArtifact", artifactName)
        return "pt-state-dict".toByteArray()
    }
}

private class PlainAggregationUploadClient : AggregatedModelFileUploadClient {
    var uploadedFileId: UUID? = null
    var uploadedFileName: String? = null
    var uploadedContentType: String? = null

    override fun upload(
        supportEndpoint: String,
        internalToken: String,
        fileId: UUID,
        fileName: String,
        contentType: String,
        content: ByteArray
    ): UploadedAggregatedModelFile {
        uploadedFileId = fileId
        uploadedFileName = fileName
        uploadedContentType = contentType
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
