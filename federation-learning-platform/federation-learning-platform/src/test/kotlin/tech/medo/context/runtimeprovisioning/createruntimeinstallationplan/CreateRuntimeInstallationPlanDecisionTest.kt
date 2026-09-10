package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand
import tech.medo.runtimeprovisioning.runtimeinstallationplan.RuntimeInstallationPlanOrganizationIdReservationState
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanOrganizationIdReservedEvent
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanResult
import java.util.UUID

class CreateRuntimeInstallationPlanDecisionTest {
    @Test
    fun RejectDuplicateRuntimeInstallationPlanOrganization() {
        val runtimeInstallationPlanOrganizationIdReservation = RuntimeInstallationPlanOrganizationIdReservationState()
        runtimeInstallationPlanOrganizationIdReservation.evolve(
            RuntimeInstallationPlanOrganizationIdReservedEvent(
                runtimeInstallationPlanId = java.util.UUID.randomUUID(),
                organizationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
                normalizedName = UUID.fromString("11111111-1111-4111-8111-111111111111").toString().trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : CreateRuntimeInstallationPlanDecision {}).decide(
                        CreateRuntimeInstallationPlanCommand(
                        runtimeInstallationPlanId = java.util.UUID.randomUUID(),
                        runtimeInfrastructureId = java.util.UUID.randomUUID(),
                        organizationId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
                        organizationName = null,
                        runtimeInfrastructurePackageId = java.util.UUID.randomUUID(),
                        runtimeInfrastructurePackageName = null,
                        runtimeInfrastructurePackageVersion = null,
                        runtimeEnvironmentType = null,
                        runtimeName = "",
                        agentInstallMode = "",
                        expectedNodeCount = 0
                        ),
                            runtimeInstallationPlanOrganizationIdReservation = runtimeInstallationPlanOrganizationIdReservation,
                            portResult = CreateRuntimeInstallationPlanResult.Succeeded(
                            bootstrapCommand = "",
                            nodeLabelCommand = "",
                            nodeTaintCommand = "",
                            runtimeAgentNodeSelectorYaml = "",
                            runtimeAgentTolerationsYaml = "",
                            bootstrapConfigYaml = ""
                        )
                    )
        }
    }
}
