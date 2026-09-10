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
import java.util.UUID
import java.time.LocalDateTime

class DeployRuntimeAgentDecisionTest {
    @Test
    fun RuntimeAgentDeploymentSucceeded() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureVerifiedEvent(
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
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
            observedNodeCount = 0
            )
        )

        val command = DeployRuntimeAgentCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeEnvironmentType = null,
            runtimeName = "",
            agentInstallMode = "",
            expectedNodeCount = 0
        )

        val events = (object : DeployRuntimeAgentDecision {}).decide(
            command,
            state = state,
            portResult = DeployRuntimeAgentResult.Succeeded(
                agentVersion = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentInstallationSucceededEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeInstallationPlanId, event.runtimeInstallationPlanId)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(command.runtimeInfrastructurePackageId, event.runtimeInfrastructurePackageId)
        assertEquals(command.runtimeInfrastructurePackageName, event.runtimeInfrastructurePackageName)
        assertEquals(command.runtimeInfrastructurePackageVersion, event.runtimeInfrastructurePackageVersion)
        assertEquals(command.runtimeEnvironmentType, event.runtimeEnvironmentType)
        assertEquals(command.runtimeName, event.runtimeName)
        assertEquals(command.agentInstallMode, event.agentInstallMode)
        assertEquals(command.expectedNodeCount, event.expectedNodeCount)
    }

    @Test
    fun RuntimeAgentDeploymentFailed() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureVerifiedEvent(
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
            observedNodeCount = 0
            )
        )

        val command = DeployRuntimeAgentCommand(
            runtimeAgentId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()),
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeEnvironmentType = null,
            runtimeName = "",
            agentInstallMode = "",
            expectedNodeCount = 0
        )

        val events = (object : DeployRuntimeAgentDecision {}).decide(
            command,
            state = state,
            portResult = DeployRuntimeAgentResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<RuntimeAgentInstallationFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-2".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeInstallationPlanId, event.runtimeInstallationPlanId)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(command.runtimeInfrastructurePackageId, event.runtimeInfrastructurePackageId)
        assertEquals(command.runtimeInfrastructurePackageName, event.runtimeInfrastructurePackageName)
        assertEquals(command.runtimeInfrastructurePackageVersion, event.runtimeInfrastructurePackageVersion)
        assertEquals(command.runtimeEnvironmentType, event.runtimeEnvironmentType)
        assertEquals(command.runtimeName, event.runtimeName)
        assertEquals(command.agentInstallMode, event.agentInstallMode)
        assertEquals(command.expectedNodeCount, event.expectedNodeCount)
    }
}
