package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.releaseruntimeenginejobafterfailure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.LocalRuntimeEngineProperties
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineClient
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineHealthResponse
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineJobRequest
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.RuntimeEngineJobResponse
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureResult
import java.util.UUID

class LocalRuntimeEngineReleaseRuntimeEngineJobAdapterTest {
    @Test
    fun cancelsRuntimeEngineJobWhenJobIdIsAvailable() {
        val client = RecordingClient()
        val adapter = adapter(client)

        val result = adapter.execute(input(runtimeEngineJobId = "job-1"))

        assertTrue(result is ReleaseRuntimeEngineJobAfterFailureResult.Succeeded)
        result as ReleaseRuntimeEngineJobAfterFailureResult.Succeeded
        assertNull(result.failureReason)
        assertEquals(listOf("http://localhost:18080" to "job-1"), client.cancelledJobs)
    }

    @Test
    fun skipsReleaseWhenJobIdIsMissing() {
        val client = RecordingClient()
        val adapter = adapter(client)

        val result = adapter.execute(input(runtimeEngineJobId = null))

        assertTrue(result is ReleaseRuntimeEngineJobAfterFailureResult.Succeeded)
        result as ReleaseRuntimeEngineJobAfterFailureResult.Succeeded
        assertEquals("Runtime engine job release skipped because runtimeEngineJobId is empty.", result.failureReason)
        assertTrue(client.cancelledJobs.isEmpty())
    }

    @Test
    fun recordsReleaseFailureWithoutThrowing() {
        val client = RecordingClient(cancelException = IllegalStateException("engine unavailable"))
        val adapter = adapter(client)

        val result = adapter.execute(input(runtimeEngineJobId = "job-1"))

        assertTrue(result is ReleaseRuntimeEngineJobAfterFailureResult.Succeeded)
        result as ReleaseRuntimeEngineJobAfterFailureResult.Succeeded
        assertEquals("Runtime engine job job-1 release failed: engine unavailable", result.failureReason)
    }

    private fun adapter(client: RuntimeEngineClient): LocalRuntimeEngineReleaseRuntimeEngineJobAdapter =
        LocalRuntimeEngineReleaseRuntimeEngineJobAdapter(
            properties = LocalRuntimeEngineProperties(
                enabled = true,
                endpoint = "http://localhost:18080/"
            ),
            runtimeEngineClient = client
        )

    private fun input(runtimeEngineJobId: String?): ReleaseRuntimeEngineJobAfterFailureInput =
        ReleaseRuntimeEngineJobAfterFailureInput(
            roundExecutionId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            runtimeEngineJobId = runtimeEngineJobId
        )

    private class RecordingClient(
        private val cancelException: RuntimeException? = null
    ) : RuntimeEngineClient {
        val cancelledJobs = mutableListOf<Pair<String, String>>()

        override fun health(endpoint: String): RuntimeEngineHealthResponse =
            RuntimeEngineHealthResponse(status = "ok", nodeName = "local-runtime")

        override fun startJob(endpoint: String, request: RuntimeEngineJobRequest): RuntimeEngineJobResponse =
            RuntimeEngineJobResponse(jobId = request.jobId, nodeName = request.nodeName, status = "running")

        override fun getJob(endpoint: String, jobId: String): RuntimeEngineJobResponse =
            RuntimeEngineJobResponse(jobId = jobId, nodeName = "local-runtime", status = "running")

        override fun cancelJob(endpoint: String, jobId: String): RuntimeEngineJobResponse {
            cancelException?.let { throw it }
            cancelledJobs += endpoint to jobId
            return RuntimeEngineJobResponse(jobId = jobId, nodeName = "local-runtime", status = "cancelled")
        }
    }
}
