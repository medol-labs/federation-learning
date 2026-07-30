package tech.medo.runtimeprovisioning.deployruntimeagent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent

import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import java.util.UUID;
import java.time.LocalDateTime

class DeployRuntimeAgentDecisionTest {
    @Test
    fun RuntimeAgentDeploymentSucceeded() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureVerifiedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "",
            observedNodeCount = 0
            )
        )

        val command = DeployRuntimeAgentCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray())
        )

        val events = DeployRuntimeAgentDecision().decide(
            command,
            state = state,
            portResult = DeployRuntimeAgentResult.Succeeded(
                agentVersion = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentInstallationSucceededEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
    }

    @Test
    fun RuntimeAgentDeploymentFailed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureVerifiedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "",
            observedNodeCount = 0
            )
        )

        val command = DeployRuntimeAgentCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray())
        )

        val events = DeployRuntimeAgentDecision().decide(
            command,
            state = state,
            portResult = DeployRuntimeAgentResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentInstallationFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()), event.runtimeInfrastructureId)
    }
}
