package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationCommand
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadedEvent
import tech.medo.runtimeagentoperations.events.RuntimeAgentBootstrapConfigurationLoadFailedEvent


import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationResult
import java.util.UUID;
import java.time.LocalDateTime

class LoadRuntimeAgentBootstrapConfigurationDecisionTest {
    @Test
    fun LoadRuntimeAgentBootstrapConfiguration() {


        val command = LoadRuntimeAgentBootstrapConfigurationCommand(
            bootstrapRequestId = java.util.UUID.randomUUID()
        )

        val events = (object : LoadRuntimeAgentBootstrapConfigurationDecision {}).decide(
            command,
            portResult = LoadRuntimeAgentBootstrapConfigurationResult.Succeeded(
                runtimeAgentId = java.util.UUID.randomUUID(),
                runtimeInfrastructureId = java.util.UUID.randomUUID(),
                agentVersion = "",
                bootstrapConfigurationLoaded = false
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentBootstrapConfigurationLoadedEvent>().single()
        assertTrue(event is RuntimeAgentBootstrapConfigurationLoadedEvent)
    }

    @Test
    fun RejectMissingRuntimeAgentBootstrapConfiguration() {


        val command = LoadRuntimeAgentBootstrapConfigurationCommand(
            bootstrapRequestId = java.util.UUID.randomUUID()
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
