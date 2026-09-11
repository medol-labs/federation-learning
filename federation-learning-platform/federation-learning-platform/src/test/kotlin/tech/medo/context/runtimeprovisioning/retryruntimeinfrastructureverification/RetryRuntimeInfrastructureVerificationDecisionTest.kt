package tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetryFailedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationResult
import java.util.UUID
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum
import java.time.LocalDateTime

class RetryRuntimeInfrastructureVerificationDecisionTest {
    @Test
    fun RuntimeInfrastructureVerificationRetrySucceeded() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureVerificationFailedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeEnvironmentType = null,
            runtimeName = "",
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "",
            expectedNodeCount = 0,
            observedNodeCount = null,
            failureReason = ""
            )
        )

        val command = RetryRuntimeInfrastructureVerificationCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeEnvironmentType = null,
            runtimeName = "",
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "",
            expectedNodeCount = 0,
            currentRuntimeInfrastructureState = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED,
            retryReason = "Retry after applying runtime scheduling labels and RBAC."
        )

        val events = (object : RetryRuntimeInfrastructureVerificationDecision {}).decide(
            command,
            state = state,
            portResult = RetryRuntimeInfrastructureVerificationResult.Succeeded(
                observedNodeCount = 0
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeInfrastructureVerificationRetrySucceededEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeInstallationPlanId, event.runtimeInstallationPlanId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(command.runtimeInfrastructurePackageId, event.runtimeInfrastructurePackageId)
        assertEquals(command.runtimeInfrastructurePackageName, event.runtimeInfrastructurePackageName)
        assertEquals(command.runtimeInfrastructurePackageVersion, event.runtimeInfrastructurePackageVersion)
        assertEquals(command.runtimeEnvironmentType, event.runtimeEnvironmentType)
        assertEquals(command.runtimeName, event.runtimeName)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
        assertEquals(command.agentInstallMode, event.agentInstallMode)
        assertEquals(command.expectedNodeCount, event.expectedNodeCount)
        assertEquals("Retry after applying runtime scheduling labels and RBAC.", event.retryReason)
    }

    @Test
    fun RuntimeInfrastructureVerificationRetryFailed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureVerificationFailedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-3".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeEnvironmentType = null,
            runtimeName = "",
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "",
            expectedNodeCount = 0,
            observedNodeCount = null,
            failureReason = ""
            )
        )

        val command = RetryRuntimeInfrastructureVerificationCommand(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-3".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeEnvironmentType = null,
            runtimeName = "",
            runtimeAgentId = java.util.UUID.randomUUID(),
            agentInstallMode = "",
            expectedNodeCount = 0,
            currentRuntimeInfrastructureState = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED,
            retryReason = "Retry after adding the participant node."
        )

        val events = (object : RetryRuntimeInfrastructureVerificationDecision {}).decide(
            command,
            state = state,
            portResult = RetryRuntimeInfrastructureVerificationResult.Rejected(
                observedNodeCount = null,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeInfrastructureVerificationRetryFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-3".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeInstallationPlanId, event.runtimeInstallationPlanId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(command.runtimeInfrastructurePackageId, event.runtimeInfrastructurePackageId)
        assertEquals(command.runtimeInfrastructurePackageName, event.runtimeInfrastructurePackageName)
        assertEquals(command.runtimeInfrastructurePackageVersion, event.runtimeInfrastructurePackageVersion)
        assertEquals(command.runtimeEnvironmentType, event.runtimeEnvironmentType)
        assertEquals(command.runtimeName, event.runtimeName)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
        assertEquals(command.agentInstallMode, event.agentInstallMode)
        assertEquals(command.expectedNodeCount, event.expectedNodeCount)
        assertEquals("Retry after adding the participant node.", event.retryReason)
    }
}
