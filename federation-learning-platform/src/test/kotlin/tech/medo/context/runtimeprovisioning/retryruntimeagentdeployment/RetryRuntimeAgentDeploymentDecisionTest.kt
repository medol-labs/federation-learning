package tech.medo.runtimeprovisioning.retryruntimeagentdeployment

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentCommand
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetryFailedEvent

import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentResult
import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;
import java.time.LocalDateTime

class RetryRuntimeAgentDeploymentDecisionTest {
    @Test
    fun RuntimeAgentDeploymentRetrySucceededAfterFailedDeployment() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeAgentInstallationFailedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            failureReason = ""
            )
        )

        val command = RetryRuntimeAgentDeploymentCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            currentRuntimeInfrastructureState = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED,
            retryReason = "Retry after fixing registry credentials."
        )

        val events = (object : RetryRuntimeAgentDeploymentDecision {}).decide(
            command,
            state = state,
            portResult = RetryRuntimeAgentDeploymentResult.Succeeded(
                agentVersion = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentDeploymentRetrySucceededEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
    }

    @Test
    fun RuntimeAgentDeploymentRetrySucceededAfterOffline() {
        val state = RuntimeInfrastructureState()


        val command = RetryRuntimeAgentDeploymentCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-3".toByteArray()),
            currentRuntimeInfrastructureState = RuntimeInfrastructureStateEnum.OFFLINE,
            retryReason = "Reconnect runtime agent."
        )

        val events = (object : RetryRuntimeAgentDeploymentDecision {}).decide(
            command,
            state = state,
            portResult = RetryRuntimeAgentDeploymentResult.Succeeded(
                agentVersion = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentDeploymentRetrySucceededEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-3".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
    }

    @Test
    fun RuntimeAgentDeploymentRetryFailed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeAgentInstallationFailedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-4".toByteArray()),
            failureReason = ""
            )
        )

        val command = RetryRuntimeAgentDeploymentCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-4".toByteArray()),
            currentRuntimeInfrastructureState = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED,
            retryReason = "Retry after transient failure."
        )

        val events = (object : RetryRuntimeAgentDeploymentDecision {}).decide(
            command,
            state = state,
            portResult = RetryRuntimeAgentDeploymentResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentDeploymentRetryFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-4".toByteArray()), event.runtimeInfrastructureId)
    }
}
