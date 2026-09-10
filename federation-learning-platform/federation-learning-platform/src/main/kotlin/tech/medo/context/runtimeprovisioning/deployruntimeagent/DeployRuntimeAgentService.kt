package tech.medo.runtimeprovisioning.deployruntimeagent

import java.util.UUID;

interface DeployRuntimeAgentService {
    fun supports(input: DeployRuntimeAgentInput): Boolean = true
    fun execute(input: DeployRuntimeAgentInput): DeployRuntimeAgentResult
}

data class DeployRuntimeAgentInput(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val runtimeInstallationPlanId: UUID,
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

sealed interface DeployRuntimeAgentResult {
    data class Succeeded(
        val agentVersion: String
    ) : DeployRuntimeAgentResult

    data class Rejected(
        val failureReason: String
    ) : DeployRuntimeAgentResult

    data class Unavailable(
        val failureReason: String
    ) : DeployRuntimeAgentResult
}
