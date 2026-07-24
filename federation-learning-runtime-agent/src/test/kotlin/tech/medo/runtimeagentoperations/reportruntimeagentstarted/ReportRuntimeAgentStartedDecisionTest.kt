package tech.medo.runtimeagentoperations.reportruntimeagentstarted

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.reportruntimeagentstarted.ReportRuntimeAgentStartedCommand
import tech.medo.runtimeagentoperations.events.RuntimeAgentStartedEvent



import java.util.UUID;


class ReportRuntimeAgentStartedDecisionTest {
    @Test
    fun ReportRuntimeAgentStartedEmitsRuntimeAgentStartedEvent() {
        val events = ReportRuntimeAgentStartedDecision().decide(
            ReportRuntimeAgentStartedCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            agentVersion = ""
            )
        )

        assertTrue(events.any { it is RuntimeAgentStartedEvent })
    }
}
