package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.verifyruntimeinfrastructure.dockercompose

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.DockerComposeCommandResult
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.DockerComposeCommandRunner
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.DockerComposeRuntimeInfrastructureProperties
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.RuntimeProvisioningLookup
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureService

@Component
class DockerComposeVerifyRuntimeInfrastructureAdapter(
    private val properties: DockerComposeRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: DockerComposeCommandRunner
) : VerifyRuntimeInfrastructureService {
    override fun supports(input: RuntimeInfrastructureVerificationInput): Boolean {
        val supported = properties.enabled &&
            (lookup.isDockerComposeRuntimeInfrastructure(input.runtimeInfrastructureId, properties) ?: true)
        log.debug(
            "Docker Compose verify supports runtimeInfrastructureId={}, enabled={}, supported={}",
            input.runtimeInfrastructureId,
            properties.enabled,
            supported
        )
        return supported
    }

    override fun verify(input: RuntimeInfrastructureVerificationInput): RuntimeInfrastructureVerification {
        log.debug("Verifying Docker Compose runtime infrastructure runtimeInfrastructureId={}", input.runtimeInfrastructureId)
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return rejected("Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}.")
        val runtimePackage = lookup.findPackage(plan)
            ?: return rejected("Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}.")
        if (!isDockerComposePackage(runtimePackage.runtimeDeploymentTargetType, runtimePackage.runtimeEnvironmentType)) {
            return rejected("Runtime infrastructure package is not a Docker Compose target.")
        }

        val version = runner.run(properties, listOf("version"))
        if (!version.succeeded) {
            val failure = commandFailure("docker compose version", version)
            log.debug("Docker Compose verification unavailable: {}", failure)
            return RuntimeInfrastructureVerification.Unavailable(failure)
        }

        val config = runner.run(properties, listOf("config", "--services"))
        if (!config.succeeded) {
            return rejected(commandFailure("docker compose config --services", config))
        }

        log.debug("Docker Compose runtime infrastructure verified runtimeInfrastructureId={}", input.runtimeInfrastructureId)
        return RuntimeInfrastructureVerification.Succeeded(
            agentInstallMode = plan.agentInstallMode ?: input.agentInstallMode,
            observedNodeCount = 1
        )
    }

    private fun isDockerComposePackage(targetType: String?, environmentType: String?): Boolean =
        properties.supportedDeploymentTargetTypes.any { it.equals(targetType, ignoreCase = true) } ||
            properties.supportedEnvironmentTypes.any { it.equals(environmentType, ignoreCase = true) }

    private fun commandFailure(command: String, result: DockerComposeCommandResult): String =
        if (result.timedOut) {
            "$command timed out after ${properties.commandTimeout}."
        } else {
            "$command failed with exitCode=${result.exitCode}: ${result.output}"
        }

    private fun rejected(failureReason: String): RuntimeInfrastructureVerification.Rejected {
        log.debug("Docker Compose verification rejected: {}", failureReason)
        return RuntimeInfrastructureVerification.Rejected(observedNodeCount = 0, failureReason = failureReason)
    }

    companion object {
        private val log = LoggerFactory.getLogger(DockerComposeVerifyRuntimeInfrastructureAdapter::class.java)
    }
}
