package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.k3s

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentInput
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentService
import tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose.RuntimeProvisioningLookup

@Component
class K3sDeployRuntimeAgentAdapter(
    private val properties: K3sRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: K3sCommandRunner
) : DeployRuntimeAgentService {
    override fun supports(input: DeployRuntimeAgentInput): Boolean =
        properties.enabled && lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?.let(lookup::findPackage)
            ?.let { isK3sPackage(it, properties) } == true

    override fun execute(input: DeployRuntimeAgentInput): DeployRuntimeAgentResult {
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return DeployRuntimeAgentResult.Rejected(
                failureReason = "Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}."
            )
        val runtimePackage = lookup.findPackage(plan)
            ?: return DeployRuntimeAgentResult.Rejected(
                failureReason = "Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}."
            )
        if (!isK3sPackage(runtimePackage, properties)) {
            return DeployRuntimeAgentResult.Rejected(
                failureReason = "Runtime infrastructure package is not a K3S target."
            )
        }

        val apply = runner.run(
            properties,
            listOf("apply", "-n", properties.namespace, "-f", properties.agentManifestFile)
        )
        if (!apply.succeeded) {
            return DeployRuntimeAgentResult.Unavailable(
                k3sCommandFailure("kubectl apply -n ${properties.namespace} -f ${properties.agentManifestFile}", apply, properties)
            )
        }

        val rollout = runner.run(
            properties,
            listOf("rollout", "status", "deployment/${properties.agentDeploymentName}", "-n", properties.namespace)
        )
        if (!rollout.succeeded) {
            return DeployRuntimeAgentResult.Unavailable(
                k3sCommandFailure("kubectl rollout status deployment/${properties.agentDeploymentName} -n ${properties.namespace}", rollout, properties)
            )
        }

        return DeployRuntimeAgentResult.Succeeded(
            runtimeAgentId = input.runtimeAgentId,
            agentVersion = properties.agentVersion
        )
    }
}
