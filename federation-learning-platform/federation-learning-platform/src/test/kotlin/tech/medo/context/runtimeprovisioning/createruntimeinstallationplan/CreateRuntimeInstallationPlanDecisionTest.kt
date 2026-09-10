package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanResult
import java.util.UUID

class CreateRuntimeInstallationPlanDecisionTest {
    @Test
    fun CreateRuntimeInstallationPlanEmitsRuntimeInstallationPlanCreatedEvent() {
        val events = (object : CreateRuntimeInstallationPlanDecision {}).decide(
            CreateRuntimeInstallationPlanCommand(
            runtimeInstallationPlanId = java.util.UUID.randomUUID(),
            runtimeInfrastructureId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            organizationName = null,
            runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
            runtimeInfrastructurePackageName = null,
            runtimeInfrastructurePackageVersion = null,
            runtimeEnvironmentType = null,
            runtimeName = "",
            agentInstallMode = "",
            expectedNodeCount = 0
            ),
            portResult = CreateRuntimeInstallationPlanResult.Succeeded(
                bootstrapCommand = "",
                nodeLabelCommand = "",
                nodeTaintCommand = "",
                runtimeAgentNodeSelectorYaml = "",
                runtimeAgentTolerationsYaml = "",
                bootstrapConfigYaml = ""
            )
        )

        assertTrue(events.any { it is RuntimeInstallationPlanCreatedEvent })
    }
}
