package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePreparedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import java.util.UUID
import java.time.LocalDateTime

class VerifyRuntimeInfrastructureDecisionTest {
    @Test
    fun RuntimeInfrastructureVerificationPassed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructurePreparedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            preparedNodeCount = 0,
            preparationNotes = null
            )
        )

        val command = VerifyRuntimeInfrastructureCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID()
        )

        val events = (object : VerifyRuntimeInfrastructureDecision {}).decide(
            command,
            state = state,
            portResult = RuntimeInfrastructureVerification.Succeeded(
                agentInstallMode = "",
                observedNodeCount = 0
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeInfrastructureVerifiedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeInstallationPlanId, event.runtimeInstallationPlanId)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
    }

    @Test
    fun RuntimeInfrastructureVerificationFailed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructurePreparedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID(),
            preparedNodeCount = 0,
            preparationNotes = null
            )
        )

        val command = VerifyRuntimeInfrastructureCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            runtimeAgentId = java.util.UUID.randomUUID()
        )

        val events = (object : VerifyRuntimeInfrastructureDecision {}).decide(
            command,
            state = state,
            portResult = RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = null,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeInfrastructureVerificationFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeInstallationPlanId, event.runtimeInstallationPlanId)
    }
}
