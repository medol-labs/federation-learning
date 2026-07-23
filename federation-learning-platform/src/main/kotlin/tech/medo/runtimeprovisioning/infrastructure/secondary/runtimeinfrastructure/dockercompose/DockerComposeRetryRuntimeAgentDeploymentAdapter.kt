package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentInput
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentResult
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentService

@Component
class DockerComposeRetryRuntimeAgentDeploymentAdapter(
    private val properties: DockerComposeRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: DockerComposeCommandRunner
) : RetryRuntimeAgentDeploymentService {
    override fun supports(input: RetryRuntimeAgentDeploymentInput): Boolean =
        properties.enabled &&
            (lookup.isDockerComposeRuntimeInfrastructure(input.runtimeInfrastructureId, properties) ?: true)

    override fun execute(input: RetryRuntimeAgentDeploymentInput): RetryRuntimeAgentDeploymentResult {
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return RetryRuntimeAgentDeploymentResult.Rejected(
                failureReason = "Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}."
            )
        val runtimePackage = lookup.findPackage(plan)
            ?: return RetryRuntimeAgentDeploymentResult.Rejected(
                failureReason = "Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}."
            )
        if (!isDockerComposePackage(runtimePackage.runtimeDeploymentTargetType, runtimePackage.runtimeEnvironmentType)) {
            return RetryRuntimeAgentDeploymentResult.Rejected(
                failureReason = "Runtime infrastructure package is not a Docker Compose target."
            )
        }

        val result = runner.run(properties, listOf("up", "-d", properties.agentServiceName))
        if (!result.succeeded) {
            return RetryRuntimeAgentDeploymentResult.Unavailable(commandFailure(result))
        }

        return RetryRuntimeAgentDeploymentResult.Succeeded(
            runtimeAgentId = input.runtimeAgentId,
            agentVersion = properties.agentVersion
        )
    }

    private fun isDockerComposePackage(targetType: String?, environmentType: String?): Boolean =
        properties.supportedDeploymentTargetTypes.any { it.equals(targetType, ignoreCase = true) } ||
            properties.supportedEnvironmentTypes.any { it.equals(environmentType, ignoreCase = true) }

    private fun commandFailure(result: DockerComposeCommandResult): String =
        if (result.timedOut) {
            "docker compose up timed out after ${properties.commandTimeout}."
        } else {
            "docker compose up failed with exitCode=${result.exitCode}: ${result.output}"
        }
}
