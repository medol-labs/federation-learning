package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentInput
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentService

@Component
class DockerComposeDeployRuntimeAgentAdapter(
    private val properties: DockerComposeRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: DockerComposeCommandRunner
) : DeployRuntimeAgentService {
    override fun supports(input: DeployRuntimeAgentInput): Boolean =
        properties.enabled &&
            (lookup.isDockerComposeRuntimeInfrastructure(input.runtimeInfrastructureId, properties) ?: true)

    override fun execute(input: DeployRuntimeAgentInput): DeployRuntimeAgentResult {
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return DeployRuntimeAgentResult.Rejected(
                failureReason = "Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}."
            )
        val runtimePackage = lookup.findPackage(plan)
            ?: return DeployRuntimeAgentResult.Rejected(
                failureReason = "Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}."
            )
        if (!isDockerComposePackage(runtimePackage.runtimeDeploymentTargetType, runtimePackage.runtimeEnvironmentType)) {
            return DeployRuntimeAgentResult.Rejected(
                failureReason = "Runtime infrastructure package is not a Docker Compose target."
            )
        }

        val result = runner.run(properties, listOf("up", "-d", properties.agentServiceName))
        if (!result.succeeded) {
            return DeployRuntimeAgentResult.Unavailable(commandFailure(result))
        }

        return DeployRuntimeAgentResult.Succeeded(
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
