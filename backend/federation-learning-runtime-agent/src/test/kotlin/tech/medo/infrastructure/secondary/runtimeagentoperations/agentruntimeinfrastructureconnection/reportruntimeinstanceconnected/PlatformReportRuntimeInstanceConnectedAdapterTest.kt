package tech.medo.infrastructure.secondary.runtimeagentoperations.agentruntimeinfrastructureconnection.reportruntimeinstanceconnected

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedResult
import java.util.UUID

class PlatformReportRuntimeInstanceConnectedAdapterTest {
    private val runtimeInfrastructureId = UUID.fromString("11111111-1111-4111-8111-111111111111")
    private val runtimeAgentId = UUID.fromString("22222222-2222-4222-8222-222222222222")
    private val organizationId = UUID.fromString("33333333-3333-4333-8333-333333333333")

    @Test
    fun reportsRuntimeConnectionToPlatform() {
        val client = CapturingPlatformClient()
        val adapter = PlatformReportRuntimeInstanceConnectedAdapter(
            client,
            RuntimePlatformConnectionReportingProperties(
                enabled = true,
                agentInstallMode = "MANUAL_GUIDED",
                organizationId = organizationId.toString(),
                runtimeName = "local-runtime",
                runtimeAgentEndpoint = "http://localhost:8082",
                endpointScope = "LOCAL"
            )
        )

        val result = adapter.execute(input())

        assertTrue(result is ReportRuntimeInstanceConnectedResult.Succeeded)

        val request = client.requests.single()
        assertEquals(runtimeInfrastructureId, request.runtimeInfrastructureId)
        assertEquals(runtimeAgentId, request.runtimeAgentId)
        assertEquals("MANUAL_GUIDED", request.agentInstallMode)
        assertEquals(organizationId, request.organizationId)
        assertEquals("local-runtime", request.runtimeName)
        assertEquals("http://localhost:8082", request.runtimeAgentEndpoint)
        assertEquals("LOCAL", request.endpointScope)
    }

    @Test
    fun rejectsWhenRequiredPlatformReportingConfigurationIsMissing() {
        val client = CapturingPlatformClient()
        val adapter = PlatformReportRuntimeInstanceConnectedAdapter(
            client,
            RuntimePlatformConnectionReportingProperties(
                enabled = true,
                agentInstallMode = "MANUAL_GUIDED",
                organizationId = null,
                runtimeName = "local-runtime",
                runtimeAgentEndpoint = "http://localhost:8082",
                endpointScope = "LOCAL"
            )
        )

        val result = adapter.execute(input())

        assertTrue(result is ReportRuntimeInstanceConnectedResult.Rejected)
        result as ReportRuntimeInstanceConnectedResult.Rejected
        assertFalse(result.retryable ?: true)
        assertTrue(result.failureReason.contains("runtime-agent.platform-connection-reporting.organization-id"))
        assertTrue(client.requests.isEmpty())
    }

    private fun input(): ReportRuntimeInstanceConnectedInput =
        ReportRuntimeInstanceConnectedInput(
            runtimeInfrastructureId = runtimeInfrastructureId,
            runtimeAgentId = runtimeAgentId,
            runtimeAgentEndpoint = "http://localhost:8082",
            endpointScope = "LOCAL",
            runtimePlatformConnectionReady = false,
            platformApiReachable = false,
            agentAuthenticationSucceeded = false,
            controlChannelEstablished = false,
            heartbeatAccepted = false
        )

    private class CapturingPlatformClient : PlatformRuntimeConnectionReportingClient {
        val requests = mutableListOf<RecordRuntimeConnectionEstablishedRequest>()

        override fun recordRuntimeConnectionEstablished(
            request: RecordRuntimeConnectionEstablishedRequest
        ): RecordRuntimeConnectionEstablishedResponse {
            requests += request
            return RecordRuntimeConnectionEstablishedResponse(
                runtimeInfrastructureId = request.runtimeInfrastructureId,
                runtimeAgentId = request.runtimeAgentId,
                agentInstallMode = request.agentInstallMode,
                organizationId = request.organizationId,
                runtimeName = request.runtimeName,
                runtimeAgentEndpoint = request.runtimeAgentEndpoint,
                endpointScope = request.endpointScope
            )
        }
    }
}
