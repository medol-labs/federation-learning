package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import java.util.UUID;

interface CreateRuntimeInstallationPlanService {
    fun supports(input: CreateRuntimeInstallationPlanInput): Boolean = true
    fun execute(input: CreateRuntimeInstallationPlanInput): CreateRuntimeInstallationPlanResult
}

data class CreateRuntimeInstallationPlanInput(
    val runtimeInstallationPlanId: UUID,
    val runtimeInfrastructureId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeInfrastructurePackageId: UUID,
    val runtimeInfrastructurePackageName: String?,
    val runtimeInfrastructurePackageVersion: String?,
    val runtimeEnvironmentType: String?,
    val runtimeName: String,
    val agentInstallMode: String,
    val expectedNodeCount: Int
)

sealed interface CreateRuntimeInstallationPlanResult {
    data class Succeeded(
        val bootstrapCommand: String,
        val nodeLabelCommand: String,
        val nodeTaintCommand: String,
        val runtimeAgentNodeSelectorYaml: String,
        val runtimeAgentTolerationsYaml: String,
        val bootstrapConfigYaml: String
    ) : CreateRuntimeInstallationPlanResult


}
