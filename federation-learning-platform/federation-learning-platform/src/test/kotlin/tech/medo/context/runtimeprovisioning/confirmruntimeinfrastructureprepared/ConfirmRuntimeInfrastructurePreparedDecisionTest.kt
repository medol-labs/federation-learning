package tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.confirmruntimeinfrastructureprepared.ConfirmRuntimeInfrastructurePreparedCommand
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePreparedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState
import java.util.UUID

class ConfirmRuntimeInfrastructurePreparedDecisionTest {
    @Test
    fun ConfirmRuntimeInfrastructurePrepared() {
        val state = RuntimeInfrastructureState()
        state.evolve(
            RuntimeInfrastructureRegisteredEvent(
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
            expectedNodeCount = 0,
            runtimeAgentId = java.util.UUID.randomUUID()
            )
        )

        val command = ConfirmRuntimeInfrastructurePreparedCommand(
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
            expectedNodeCount = 0,
            runtimeAgentId = java.util.UUID.randomUUID(),
            preparedNodeCount = 1,
            preparationNotes = "K3s agent joined and node scheduling labels were applied."
        )

        val events = (object : ConfirmRuntimeInfrastructurePreparedDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<RuntimeInfrastructurePreparedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("runtime-infra-1".toByteArray()), event.runtimeInfrastructureId)
        assertEquals(command.runtimeInstallationPlanId, event.runtimeInstallationPlanId)
        assertEquals(command.organizationId, event.organizationId)
        assertEquals(command.organizationName, event.organizationName)
        assertEquals(command.runtimeInfrastructurePackageId, event.runtimeInfrastructurePackageId)
        assertEquals(command.runtimeInfrastructurePackageName, event.runtimeInfrastructurePackageName)
        assertEquals(command.runtimeInfrastructurePackageVersion, event.runtimeInfrastructurePackageVersion)
        assertEquals(command.runtimeEnvironmentType, event.runtimeEnvironmentType)
        assertEquals(command.runtimeName, event.runtimeName)
        assertEquals(command.agentInstallMode, event.agentInstallMode)
        assertEquals(command.expectedNodeCount, event.expectedNodeCount)
        assertEquals(command.runtimeAgentId, event.runtimeAgentId)
        assertEquals(1, event.preparedNodeCount)
        assertEquals("K3s agent joined and node scheduling labels were applied.", event.preparationNotes)
    }
}
