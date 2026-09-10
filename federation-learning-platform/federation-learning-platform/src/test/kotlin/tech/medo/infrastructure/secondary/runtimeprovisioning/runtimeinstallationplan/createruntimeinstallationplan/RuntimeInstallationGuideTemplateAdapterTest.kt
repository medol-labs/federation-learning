package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinstallationplan.createruntimeinstallationplan

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanInput
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanResult
import java.util.UUID

class RuntimeInstallationGuideTemplateAdapterTest {
    private val runtimeInstallationPlanId = UUID.fromString("00000000-0000-0000-0000-000000000031")
    private val runtimeInfrastructureId = UUID.fromString("00000000-0000-0000-0000-000000000032")
    private val runtimeInfrastructurePackageId = UUID.fromString("00000000-0000-0000-0000-000000000033")
    private val organizationId = UUID.fromString("00000000-0000-0000-0000-000000000034")

    @Test
    fun `renders participant node installation guide values`() {
        val result = RuntimeInstallationGuideTemplateAdapter().execute(
            CreateRuntimeInstallationPlanInput(
                runtimeInstallationPlanId = runtimeInstallationPlanId,
                runtimeInfrastructureId = runtimeInfrastructureId,
                organizationId = organizationId,
                organizationName = "Hospital A",
                runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
                runtimeInfrastructurePackageName = "K3s Runtime Package",
                runtimeInfrastructurePackageVersion = "1.0.0",
                runtimeEnvironmentType = "K3S",
                runtimeName = "hospital-a-runtime",
                agentInstallMode = "PLATFORM_MANAGED",
                expectedNodeCount = 1
            )
        )

        require(result is CreateRuntimeInstallationPlanResult.Succeeded)
        assertTrue(result.bootstrapCommand.contains("kubectl label node <node-name>"))
        assertTrue(result.nodeLabelCommand.contains("medol.dev/organization-id=$organizationId"))
        assertTrue(result.nodeLabelCommand.contains("medol.dev/runtime-infrastructure-id=$runtimeInfrastructureId"))
        assertTrue(result.nodeTaintCommand.contains("medol.dev/runtime-only=true:NoSchedule"))
        assertTrue(result.runtimeAgentNodeSelectorYaml.contains("medol.dev/runtime-infrastructure-id: \"$runtimeInfrastructureId\""))
        assertTrue(result.runtimeAgentTolerationsYaml.contains("key: \"medol.dev/runtime-only\""))
        assertTrue(result.bootstrapConfigYaml.contains("kind: RuntimeParticipantNodeConfig"))
        assertTrue(result.bootstrapConfigYaml.contains("runtimeInfrastructureId: \"$runtimeInfrastructureId\""))
    }
}
