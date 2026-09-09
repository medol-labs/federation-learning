package tech.medo.infrastructure.secondary.trainingorchestration.traininground.aggregateplainmodelupdates

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregatedModelFileUploadClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeEngineClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeJobRequest
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeJobResponse
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeProperties
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
            properties = AggregationRuntimeProperties(
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
                featureSchemaId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
                roundId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
                roundNumber = 1,
                maxRounds = 1,
                minimumAccuracy = BigDecimal("0.90"),
                aggregationAlgorithm = "FED_AVG_PYTORCH_STATE_DICT",
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
        assertEquals("PYTORCH_STATE_DICT", result.modelFormat)
        assertEquals("http://support/api/files/$aggregatedModelId/content", result.aggregatedModelArtifactUri)
    }
}

private class PlainAggregationRuntimeClient : AggregationRuntimeEngineClient {
    var submittedRequest: AggregationRuntimeJobRequest? = null

    override fun submit(endpoint: String, request: AggregationRuntimeJobRequest): AggregationRuntimeJobResponse {
        submittedRequest = request
        return AggregationRuntimeJobResponse(request.jobId, request.nodeName, "running")
    }

    override fun getJob(endpoint: String, jobId: String): AggregationRuntimeJobResponse =
        AggregationRuntimeJobResponse(jobId, "platform-aggregator", "completed", exitCode = 0)

    override fun downloadArtifact(endpoint: String, jobId: String, artifactName: String): ByteArray =
        "{\"format\":\"PYTORCH_STATE_DICT\"}".toByteArray()
}

private class PlainAggregationUploadClient : AggregatedModelFileUploadClient {
    var uploadedFileId: UUID? = null

    override fun upload(
        supportEndpoint: String,
        internalToken: String,
        fileId: UUID,
        fileName: String,
        contentType: String,
        content: ByteArray
    ): UploadedAggregatedModelFile {
        uploadedFileId = fileId
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
