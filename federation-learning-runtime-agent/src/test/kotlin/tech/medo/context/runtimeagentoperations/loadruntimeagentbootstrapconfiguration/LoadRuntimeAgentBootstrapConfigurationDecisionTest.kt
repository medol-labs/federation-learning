package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadFailedEvent
import java.time.LocalDateTime
import java.util.UUID

class LoadRuntimeAgentBootstrapConfigurationDecisionTest {
    @Test
    fun LoadRuntimeAgentBootstrapConfiguration() {


        val command = LoadRuntimeAgentBootstrapConfigurationCommand(
            bootstrapRequestId = UUID.randomUUID()
        )

        val events = (object : LoadRuntimeAgentBootstrapConfigurationDecision {}).decide(
            command,
            portResult = LoadRuntimeAgentBootstrapConfigurationResult.Succeeded(
                runtimeAgentId = UUID.randomUUID(),
                runtimeInfrastructureId = UUID.randomUUID(),
                agentVersion = "",
                bootstrapConfigurationLoaded = false
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        events.filterIsInstance<RuntimeAgentBootstrapConfigurationLoadedEvent>().single()
    }

    @Test
    fun RejectMissingRuntimeAgentBootstrapConfiguration() {


        val command = LoadRuntimeAgentBootstrapConfigurationCommand(
            bootstrapRequestId = UUID.randomUUID()
        )

        val events = (object : LoadRuntimeAgentBootstrapConfigurationDecision {}).decide(
            command,
            portResult = LoadRuntimeAgentBootstrapConfigurationResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentBootstrapConfigurationLoadFailedEvent>().single()
        assertEquals(command.bootstrapRequestId, event.bootstrapRequestId)
    }
}
