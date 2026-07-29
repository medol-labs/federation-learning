package tech.medo.infrastructure.secondary.runtimeagentoperations.runtimeagentlifecycle.loadruntimeagentbootstrapconfiguration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationInput
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationResult
import java.util.UUID

class ConfiguredLoadRuntimeAgentBootstrapConfigurationAdapterTest {
    private val runtimeAgentId = UUID.fromString("11111111-1111-4111-8111-111111111111")
    private val runtimeInfrastructureId = UUID.fromString("22222222-2222-4222-8222-222222222222")

    @Test
    fun loadsCompleteBootstrapConfiguration() {
        val adapter = ConfiguredLoadRuntimeAgentBootstrapConfigurationAdapter(
            RuntimeAgentBootstrapConfigurationProperties(
                runtimeAgentId = runtimeAgentId.toString(),
                runtimeInfrastructureId = runtimeInfrastructureId.toString(),
                agentVersion = "test-agent",
                enabled = true
            )
        )

        val result = adapter.execute(input())

        assertTrue(result is LoadRuntimeAgentBootstrapConfigurationResult.Succeeded)
        result as LoadRuntimeAgentBootstrapConfigurationResult.Succeeded
        assertEquals(runtimeAgentId, result.runtimeAgentId)
        assertEquals(runtimeInfrastructureId, result.runtimeInfrastructureId)
        assertEquals("test-agent", result.agentVersion)
        assertTrue(result.bootstrapConfigurationLoaded)
    }

    @Test
    fun rejectsIncompleteBootstrapConfiguration() {
        val adapter = ConfiguredLoadRuntimeAgentBootstrapConfigurationAdapter(
            RuntimeAgentBootstrapConfigurationProperties(
                runtimeAgentId = runtimeAgentId.toString(),
                runtimeInfrastructureId = null,
                agentVersion = "test-agent",
                enabled = true
            )
        )

        val result = adapter.execute(input())

        assertTrue(result is LoadRuntimeAgentBootstrapConfigurationResult.Rejected)
        result as LoadRuntimeAgentBootstrapConfigurationResult.Rejected
        assertEquals(input().bootstrapRequestId, result.bootstrapRequestId)
        assertTrue(result.failureReason.contains("runtime-agent.bootstrap.runtime-infrastructure-id"))
    }

    private fun input(): LoadRuntimeAgentBootstrapConfigurationInput =
        LoadRuntimeAgentBootstrapConfigurationInput(
            bootstrapRequestId = UUID.fromString("33333333-3333-4333-8333-333333333333")
        )
}
