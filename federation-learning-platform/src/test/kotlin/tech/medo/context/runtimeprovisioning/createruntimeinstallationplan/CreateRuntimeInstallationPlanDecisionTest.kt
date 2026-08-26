package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import java.util.UUID

class CreateRuntimeInstallationPlanDecisionTest {
    @Test
    fun CreateRuntimeInstallationPlanEmitsRuntimeInstallationPlanCreatedEvent() {
        val runtimeInstallationPlanId = UUID.nameUUIDFromBytes("runtime-installation-plan-1".toByteArray())
        val events = (object : CreateRuntimeInstallationPlanDecision {}).decide(
            CreateRuntimeInstallationPlanCommand(
                runtimeInstallationPlanId = runtimeInstallationPlanId,
                organizationId = UUID.randomUUID(),
                runtimeInfrastructurePackageId = UUID.randomUUID(),
                runtimeName = "",
                agentInstallMode = "",
                expectedNodeCount = 0
            )
        )

        val event = events.filterIsInstance<RuntimeInstallationPlanCreatedEvent>().single()
        assertEquals(
            UUID.nameUUIDFromBytes("runtime-infrastructure:$runtimeInstallationPlanId".toByteArray()),
            event.runtimeInfrastructureId
        )
    }
}
