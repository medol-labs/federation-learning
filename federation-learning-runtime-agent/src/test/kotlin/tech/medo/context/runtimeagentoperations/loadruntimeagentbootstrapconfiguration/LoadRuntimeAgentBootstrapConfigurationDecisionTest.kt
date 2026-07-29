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
            bootstrapRequestId = UUID.nameUUIDFromBytes("bootstrap-request-1".toByteArray())
        )

        val events = LoadRuntimeAgentBootstrapConfigurationDecision().decide(
            command,
            portResult = LoadRuntimeAgentBootstrapConfigurationResult.Succeeded(
                runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray()),
                runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
                agentVersion = "local-dev",
                bootstrapConfigurationLoaded = true
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentBootstrapConfigurationLoadedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-agent-1".toByteArray()), event.runtimeAgentId)
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals("local-dev", event.agentVersion)
        assertEquals(true, event.bootstrapConfigurationLoaded)
    }

    @Test
    fun RejectMissingRuntimeAgentBootstrapConfiguration() {


        val command = LoadRuntimeAgentBootstrapConfigurationCommand(
            bootstrapRequestId = UUID.nameUUIDFromBytes("bootstrap-request-2".toByteArray())
        )

        val events = LoadRuntimeAgentBootstrapConfigurationDecision().decide(
            command,
            portResult = LoadRuntimeAgentBootstrapConfigurationResult.Rejected(
                bootstrapRequestId = UUID.nameUUIDFromBytes("bootstrap-request-2".toByteArray()),
                failureReason = "RUNTIME_AGENT_ID is missing."
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentBootstrapConfigurationLoadFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("bootstrap-request-2".toByteArray()), event.bootstrapRequestId)
        assertEquals("RUNTIME_AGENT_ID is missing.", event.failureReason)
    }
}
