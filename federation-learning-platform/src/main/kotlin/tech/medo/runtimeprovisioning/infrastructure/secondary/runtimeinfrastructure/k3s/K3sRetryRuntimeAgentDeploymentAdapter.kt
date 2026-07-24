package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.k3s

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose.RuntimeProvisioningLookup
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentInput
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentResult
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentService

@Component
class K3sRetryRuntimeAgentDeploymentAdapter(
    private val properties: K3sRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: K3sCommandRunner
) : RetryRuntimeAgentDeploymentService {
    override fun supports(input: RetryRuntimeAgentDeploymentInput): Boolean =
        properties.enabled && lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?.let(lookup::findPackage)
            ?.let { isK3sPackage(it, properties) } == true

    override fun execute(input: RetryRuntimeAgentDeploymentInput): RetryRuntimeAgentDeploymentResult {
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return RetryRuntimeAgentDeploymentResult.Rejected(
                failureReason = "Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}."
            )
        val runtimePackage = lookup.findPackage(plan)
            ?: return RetryRuntimeAgentDeploymentResult.Rejected(
                failureReason = "Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}."
            )
        if (!isK3sPackage(runtimePackage, properties)) {
            return RetryRuntimeAgentDeploymentResult.Rejected(
                failureReason = "Runtime infrastructure package is not a K3S target."
            )
        }

        val apply = runner.run(
            properties,
            listOf("apply", "-n", properties.namespace, "-f", properties.agentManifestFile)
        )
        if (!apply.succeeded) {
            return RetryRuntimeAgentDeploymentResult.Unavailable(
                k3sCommandFailure("kubectl apply -n ${properties.namespace} -f ${properties.agentManifestFile}", apply, properties)
            )
        }

        val rollout = runner.run(
            properties,
            listOf("rollout", "status", "deployment/${properties.agentDeploymentName}", "-n", properties.namespace)
        )
        if (!rollout.succeeded) {
            return RetryRuntimeAgentDeploymentResult.Unavailable(
                k3sCommandFailure("kubectl rollout status deployment/${properties.agentDeploymentName} -n ${properties.namespace}", rollout, properties)
            )
        }

        return RetryRuntimeAgentDeploymentResult.Succeeded(
            runtimeAgentId = input.runtimeAgentId,
            agentVersion = properties.agentVersion
        )
    }
}
