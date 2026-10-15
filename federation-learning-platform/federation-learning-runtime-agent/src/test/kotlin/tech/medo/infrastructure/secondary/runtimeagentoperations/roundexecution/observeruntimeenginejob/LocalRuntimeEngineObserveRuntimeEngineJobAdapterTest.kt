package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.observeruntimeenginejob

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.LocalRuntimeEngineProperties
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineClient
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineHealthResponse
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineJobRequest
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineJobResponse
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobInput
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobResult
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.util.UUID

class LocalRuntimeEngineObserveRuntimeEngineJobAdapterTest {
    @Test
    fun validatesPersonalizedArtifactsAndCalculatesContentDigest(@TempDir tempDir: Path) {
        val modelPath = tempDir.resolve("personalized.pt")
        val metricsPath = tempDir.resolve("personalized_metrics.json")
        Files.writeString(modelPath, "real-model-content")
        Files.writeString(metricsPath, """{"personalizationMode":"LOCAL_ADAPTATION","baselineAccuracy":0.5,"personalizedAccuracy":0.75,"personalizationLift":0.25}""")
        val request = input("job-1").copy(
            personalizationMode = "LOCAL_ADAPTATION", personalizedArtifactPolicy = "REGISTER_RUNTIME_LOCAL",
            personalizationScope = "PARTICIPANT", personalizationTargetType = "PARTICIPANT",
            personalizationTargetId = UUID.randomUUID(), secureAggregationRequired = false
        )
        val client = RecordingClient(RuntimeEngineJobResponse(jobId = "job-1", status = "completed", output = mapOf(
            "personalizedModel" to modelPath.toString(), "personalizedMetrics" to metricsPath.toString()
        )))
        val service = adapter(client)
        val result = service.execute(request) as ObserveRuntimeEngineJobResult.Succeeded
        assertEquals("COMPLETED", result.observedStatus)
        assertEquals(true, result.personalizedArtifactAvailable)
        assertEquals(modelPath.toUri().toString(), result.personalizedModelArtifactRef)
        assertEquals(BigDecimal("0.75"), result.personalizedAccuracy)
        assertEquals(BigDecimal("0.25"), result.personalizationLift)
        assertEquals("sha256:" + java.security.MessageDigest.getInstance("SHA-256")
            .digest(Files.readAllBytes(modelPath)).joinToString("") { "%02x".format(it) }, result.personalizedModelArtifactDigest)
        assertEquals(result.personalizedModelId, (service.execute(request) as ObserveRuntimeEngineJobResult.Succeeded).personalizedModelId)
        Files.delete(modelPath)
        val missing = service.execute(request) as ObserveRuntimeEngineJobResult.Succeeded
        assertEquals("FAILED", missing.observedStatus)
        assertEquals(false, missing.personalizedArtifactAvailable)
    }

    @Test
    fun returnsRunningWhenJobIsStillRunning() {
        val adapter = adapter(client = RecordingClient(RuntimeEngineJobResponse(jobId = "job-1", status = "running")))

        val result = adapter.execute(input(runtimeEngineJobId = "job-1")) as ObserveRuntimeEngineJobResult.Succeeded

        assertEquals("RUNNING", result.observedStatus)
        assertNull(result.failureReason)
        assertNull(result.trainingLoss)
    }

    @Test
    fun returnsCompletedWithArtifactsAndTrainingLoss(@TempDir tempDir: Path) {
        val metricsPath = tempDir.resolve("job-1/local-runtime/metrics.json")
        Files.createDirectories(metricsPath.parent)
        Files.writeString(metricsPath, """{"samples":10,"loss":0.125}""")
        val adapter = adapter(
            client = RecordingClient(
                RuntimeEngineJobResponse(
                    jobId = "job-1",
                    status = "completed",
                    output = mapOf(
                        "localUpdate" to "/workspace/tmp/runtime-engine/job-1/local-runtime/local_update.json",
                        "metrics" to "/workspace/tmp/runtime-engine/job-1/local-runtime/metrics.json"
                    )
                )
            ),
            properties = LocalRuntimeEngineProperties(
                endpoint = "http://localhost:18080",
                runtimeRoot = "/workspace/tmp/runtime-engine",
                runtimeRootHostRoot = tempDir.toString(),
                jobObservationTimeout = Duration.ZERO,
                jobObservationPollInterval = Duration.ofMillis(1)
            )
        )

        val result = adapter.execute(input(runtimeEngineJobId = "job-1")) as ObserveRuntimeEngineJobResult.Succeeded

        assertEquals("COMPLETED", result.observedStatus)
        assertNull(result.failureReason)
        assertEquals("/workspace/tmp/runtime-engine/job-1/local-runtime/local_update.json", result.localUpdateArtifactRef)
        assertEquals("/workspace/tmp/runtime-engine/job-1/local-runtime/metrics.json", result.metricsArtifactRef)
        assertEquals(BigDecimal("0.125"), result.trainingLoss)
    }

    @Test
    fun returnsFailedWhenRuntimeEngineJobFailed() {
        val adapter = adapter(
            client = RecordingClient(RuntimeEngineJobResponse(jobId = "job-1", status = "failed", exitCode = 1))
        )

        val result = adapter.execute(input(runtimeEngineJobId = "job-1")) as ObserveRuntimeEngineJobResult.Succeeded

        assertEquals("FAILED", result.observedStatus)
        assertEquals("Runtime engine job job-1 finished with status failed and exitCode=1.", result.failureReason)
    }

    private fun adapter(
        client: RuntimeEngineClient,
        properties: LocalRuntimeEngineProperties = LocalRuntimeEngineProperties(
            endpoint = "http://localhost:18080",
            jobObservationTimeout = Duration.ZERO,
            jobObservationPollInterval = Duration.ofMillis(1)
        )
    ): LocalRuntimeEngineObserveRuntimeEngineJobAdapter =
        LocalRuntimeEngineObserveRuntimeEngineJobAdapter(
            properties = properties,
            runtimeEngineClient = client,
            objectMapper = ObjectMapper()
        )

    private fun input(runtimeEngineJobId: String): ObserveRuntimeEngineJobInput =
        ObserveRuntimeEngineJobInput(
            roundExecutionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            executionSessionId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            executionPlanId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            trainingJobId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            trainingRunConfigurationId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            roundId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            roundNumber = 1,
            runtimeId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            organizationId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            featureSchemaId = UUID.fromString("99999999-9999-4999-8999-999999999999"),
            personalizationMode = "GLOBAL_ONLY",
            personalizationScope = "FEDERATION",
            personalizedArtifactPolicy = "NONE",
            personalizationMetric = null,
            minimumPersonalizationLift = BigDecimal.ZERO,
            personalizationTargetType = null,
            personalizationTargetId = null,
            personalizationCohortId = null,
            personalizationProfileId = null,
            runtimeEngineJobId = runtimeEngineJobId,
            secureAggregationRequired = true,
            secureAggregationSessionId = UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            encryptionScheme = "PAILLIER",
            publicKeyVersion = "local-dev-v1"
        )

    private class RecordingClient(
        private val response: RuntimeEngineJobResponse
    ) : RuntimeEngineClient {
        override fun health(endpoint: String): RuntimeEngineHealthResponse =
            RuntimeEngineHealthResponse(status = "ok", nodeName = "local-runtime")

        override fun startJob(endpoint: String, request: RuntimeEngineJobRequest): RuntimeEngineJobResponse =
            response

        override fun getJob(endpoint: String, jobId: String): RuntimeEngineJobResponse =
            response

        override fun cancelJob(endpoint: String, jobId: String): RuntimeEngineJobResponse =
            RuntimeEngineJobResponse(jobId = jobId, nodeName = "local-runtime", status = "cancelled")
    }
}
