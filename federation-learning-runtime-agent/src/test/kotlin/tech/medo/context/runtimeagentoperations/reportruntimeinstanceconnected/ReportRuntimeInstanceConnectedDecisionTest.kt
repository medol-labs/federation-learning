package tech.medo.runtimeagentoperations.reportruntimeinstanceconnected

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedCommand
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent



import java.util.UUID;


class ReportRuntimeInstanceConnectedDecisionTest {
    @Test
    fun ReportRuntimeInstanceConnectedEmitsAgentRuntimeConnectionEstablishedEvent() {
        val events = ReportRuntimeInstanceConnectedDecision().decide(
            ReportRuntimeInstanceConnectedCommand(
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimePlatformConnectionReady = false,
            platformApiReachable = false,
            agentAuthenticationSucceeded = false,
            controlChannelEstablished = false,
            heartbeatAccepted = false
            )
        )

        assertTrue(events.any { it is AgentRuntimeConnectionEstablishedEvent })
    }
}
