package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureService

@Component
class DockerComposeVerifyRuntimeInfrastructureAdapter(
    private val properties: DockerComposeRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: DockerComposeCommandRunner
) : VerifyRuntimeInfrastructureService {
    override fun supports(input: RuntimeInfrastructureVerificationInput): Boolean =
        properties.enabled &&
            (lookup.isDockerComposeRuntimeInfrastructure(input.runtimeInfrastructureId, properties) ?: true)

    override fun verify(input: RuntimeInfrastructureVerificationInput): RuntimeInfrastructureVerification {
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = "Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}."
            )
        val runtimePackage = lookup.findPackage(plan)
            ?: return RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = "Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}."
            )
        if (!isDockerComposePackage(runtimePackage.runtimeDeploymentTargetType, runtimePackage.runtimeEnvironmentType)) {
            return RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = "Runtime infrastructure package is not a Docker Compose target."
            )
        }

        val version = runner.run(properties, listOf("version"))
        if (!version.succeeded) {
            return RuntimeInfrastructureVerification.Unavailable(commandFailure("docker compose version", version))
        }

        val config = runner.run(properties, listOf("config", "--services"))
        if (!config.succeeded) {
            return RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = commandFailure("docker compose config --services", config)
            )
        }

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
}
