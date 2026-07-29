package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent

import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import java.util.UUID;
import java.time.LocalDateTime

class VerifyRuntimeInfrastructureDecisionTest {
    @Test
    fun RuntimeInfrastructureVerificationPassed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureRegisteredEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            runtimeAgentId = java.util.UUID.randomUUID()
            )
        )

        val command = VerifyRuntimeInfrastructureCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "PLATFORM_MANAGED",
            verificationPassed = true,
            observedNodeCount = 3,
            failureReason = null
        )

        val events = VerifyRuntimeInfrastructureDecision().decide(
            command,
            state = state,
            portResult = RuntimeInfrastructureVerification.Succeeded(
                agentInstallMode = "PLATFORM_MANAGED",
                observedNodeCount = 3
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeInfrastructureVerifiedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
        assertEquals("PLATFORM_MANAGED", event.agentInstallMode)
        assertEquals(3, event.observedNodeCount)
    }

    @Test
    fun RuntimeInfrastructureVerificationFailed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureRegisteredEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeAgentId = java.util.UUID.randomUUID()
            )
        )

        val command = VerifyRuntimeInfrastructureCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "",
            verificationPassed = false,
            observedNodeCount = 0,
            failureReason = "Runtime infrastructure is unreachable."
        )

        val events = VerifyRuntimeInfrastructureDecision().decide(
            command,
            state = state,
            portResult = RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = "Runtime infrastructure is unreachable."
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeInfrastructureVerificationFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(0, event.observedNodeCount)
        assertEquals("Runtime infrastructure is unreachable.", event.failureReason)
    }
}
