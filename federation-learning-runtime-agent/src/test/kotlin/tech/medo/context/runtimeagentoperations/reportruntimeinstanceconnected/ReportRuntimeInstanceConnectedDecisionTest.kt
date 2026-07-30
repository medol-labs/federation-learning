package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand
import tech.medo.runtimeagentoperations.events.RuntimeInstanceSelfCheckPassedEvent
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionReportFailedEvent


import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedResult
import java.util.UUID;
import java.time.LocalDateTime

class ReportRuntimeInstanceConnectedDecisionTest {
    @Test
    fun ReportRuntimeInstanceConnected() {


        val command = ReportRuntimeInstanceConnectedCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray()),
            runtimePlatformConnectionReady = true,
            platformApiReachable = true,
            agentAuthenticationSucceeded = true,
            controlChannelEstablished = true,
            heartbeatAccepted = true
        )

        val events = ReportRuntimeInstanceConnectedDecision().decide(
            command,
            portResult = ReportRuntimeInstanceConnectedResult.Succeeded(

            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentRuntimeConnectionEstablishedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray()), event.runtimeAgentId)
        assertEquals(true, event.runtimePlatformConnectionReady)
        assertEquals(true, event.platformApiReachable)
        assertEquals(true, event.agentAuthenticationSucceeded)
        assertEquals(true, event.controlChannelEstablished)
        assertEquals(true, event.heartbeatAccepted)
    }

    @Test
    fun ReportRuntimeInstanceConnectionFailed() {


        val command = ReportRuntimeInstanceConnectedCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-2".toByteArray()),
            runtimePlatformConnectionReady = false,
            platformApiReachable = false,
            agentAuthenticationSucceeded = false,
            controlChannelEstablished = false,
            heartbeatAccepted = false
        )

        val events = ReportRuntimeInstanceConnectedDecision().decide(
            command,
            portResult = ReportRuntimeInstanceConnectedResult.Rejected(
                failureReason = "",
                retryable = null
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<AgentRuntimeConnectionReportFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(UUID.nameUUIDFromBytes("runtime-agent-2".toByteArray()), event.runtimeAgentId)
        assertEquals(false, event.runtimePlatformConnectionReady)
        assertEquals(false, event.platformApiReachable)
        assertEquals(false, event.agentAuthenticationSucceeded)
        assertEquals(false, event.controlChannelEstablished)
        assertEquals(false, event.heartbeatAccepted)
    }
}
