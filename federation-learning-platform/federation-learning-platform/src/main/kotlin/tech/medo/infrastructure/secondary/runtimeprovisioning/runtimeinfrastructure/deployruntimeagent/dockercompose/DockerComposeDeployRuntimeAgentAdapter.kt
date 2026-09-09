package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose

import org.slf4j.LoggerFactory
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
    override fun supports(input: DeployRuntimeAgentInput): Boolean {
        val supported = properties.enabled &&
            (lookup.isDockerComposeRuntimeInfrastructure(input.runtimeInfrastructureId, properties) ?: true)
        log.debug(
            "Docker Compose deploy supports runtimeInfrastructureId={}, runtimeAgentId={}, enabled={}, supported={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.enabled,
            supported
        )
        return supported
    }

    override fun execute(input: DeployRuntimeAgentInput): DeployRuntimeAgentResult {
        log.debug(
            "Deploying runtime agent with Docker Compose runtimeInfrastructureId={}, runtimeAgentId={}, service={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.agentServiceName
        )
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return rejected("Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}.")
        val runtimePackage = lookup.findPackage(plan)
            ?: return rejected("Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}.")
        if (!isDockerComposePackage(runtimePackage.runtimeEnvironmentType)) {
            return rejected("Runtime infrastructure package is not a Docker Compose target.")
        }

        val result = runner.run(properties, listOf("up", "-d", properties.agentServiceName))
        if (!result.succeeded) {
            val failure = commandFailure(result)
            log.debug("Docker Compose runtime agent deployment unavailable: {}", failure)
            return DeployRuntimeAgentResult.Unavailable(failure)
        }

        log.debug(
            "Docker Compose runtime agent deployed runtimeInfrastructureId={}, runtimeAgentId={}, agentVersion={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.agentVersion
        )
        return DeployRuntimeAgentResult.Succeeded(
            agentVersion = properties.agentVersion
        )
    }

    private fun isDockerComposePackage(environmentType: String?): Boolean =
        properties.supportedEnvironmentTypes.any { it.equals(environmentType, ignoreCase = true) }

    private fun commandFailure(result: DockerComposeCommandResult): String =
        if (result.timedOut) {
            "docker compose up timed out after ${properties.commandTimeout}."
        } else {
            "docker compose up failed with exitCode=${result.exitCode}: ${result.output}"
        }

    private fun rejected(failureReason: String): DeployRuntimeAgentResult.Rejected {
        log.debug("Docker Compose runtime agent deployment rejected: {}", failureReason)
        return DeployRuntimeAgentResult.Rejected(failureReason)
    }

    companion object {
        private val log = LoggerFactory.getLogger(DockerComposeDeployRuntimeAgentAdapter::class.java)
    }
}
