package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose

import org.slf4j.LoggerFactory
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
    override fun supports(input: RetryRuntimeAgentDeploymentInput): Boolean {
        val supported = properties.enabled &&
            (lookup.isDockerComposeRuntimeInfrastructure(input.runtimeInfrastructureId, properties) ?: true)
        log.debug(
            "Docker Compose retry supports runtimeInfrastructureId={}, runtimeAgentId={}, enabled={}, supported={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.enabled,
            supported
        )
        return supported
    }

    override fun execute(input: RetryRuntimeAgentDeploymentInput): RetryRuntimeAgentDeploymentResult {
        log.debug(
            "Retrying runtime agent deployment with Docker Compose runtimeInfrastructureId={}, runtimeAgentId={}, service={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.agentServiceName
        )
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return rejected("Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}.")
        val runtimePackage = lookup.findPackage(plan)
            ?: return rejected("Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}.")
        if (!isDockerComposePackage(runtimePackage.runtimeDeploymentTargetType, runtimePackage.runtimeEnvironmentType)) {
            return rejected("Runtime infrastructure package is not a Docker Compose target.")
        }

        val result = runner.run(properties, listOf("up", "-d", properties.agentServiceName))
        if (!result.succeeded) {
            val failure = commandFailure(result)
            log.debug("Docker Compose runtime agent deployment retry unavailable: {}", failure)
            return RetryRuntimeAgentDeploymentResult.Unavailable(failure)
        }

        log.debug(
            "Docker Compose runtime agent deployment retried runtimeInfrastructureId={}, runtimeAgentId={}, agentVersion={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.agentVersion
        )
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

    private fun rejected(failureReason: String): RetryRuntimeAgentDeploymentResult.Rejected {
        log.debug("Docker Compose runtime agent deployment retry rejected: {}", failureReason)
        return RetryRuntimeAgentDeploymentResult.Rejected(failureReason)
    }

    companion object {
        private val log = LoggerFactory.getLogger(DockerComposeRetryRuntimeAgentDeploymentAdapter::class.java)
    }
}
