package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent



import java.util.UUID;


class CreateRuntimeInstallationPlanDecisionTest {
    @Test
    fun CreateRuntimeInstallationPlanEmitsRuntimeInstallationPlanCreatedEvent() {
        val events = (object : CreateRuntimeInstallationPlanDecision {}).decide(
            CreateRuntimeInstallationPlanCommand(
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeName = "",
            agentInstallMode = "",
            expectedNodeCount = 0
            )
        )

        assertTrue(events.any { it is RuntimeInstallationPlanCreatedEvent })
    }
}
