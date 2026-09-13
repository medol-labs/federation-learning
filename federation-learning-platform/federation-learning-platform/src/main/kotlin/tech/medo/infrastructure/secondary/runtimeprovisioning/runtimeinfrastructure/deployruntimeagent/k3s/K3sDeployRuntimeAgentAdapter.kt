package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentInput
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentService
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.RuntimeProvisioningLookup

@Component
class K3sDeployRuntimeAgentAdapter(
    private val properties: K3sRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: K3sCommandRunner
) : DeployRuntimeAgentService {
    override fun supports(input: DeployRuntimeAgentInput): Boolean {
        val supported = properties.enabled && lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?.let(lookup::findPackage)
            ?.let { isK3sPackage(it, properties) } == true
        log.debug(
            "K3S deploy supports runtimeInfrastructureId={}, runtimeAgentId={}, enabled={}, supported={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.enabled,
            supported
        )
        return supported
    }

    override fun execute(input: DeployRuntimeAgentInput): DeployRuntimeAgentResult {
        log.debug(
            "Deploying runtime agent with K3S runtimeInfrastructureId={}, runtimeAgentId={}, namespace={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.namespace
        )
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return rejected("Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}.")
        val runtimePackage = lookup.findPackage(plan)
            ?: return rejected("Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}.")
        if (!isK3sPackage(runtimePackage, properties)) {
            return rejected("Runtime infrastructure package is not a K3S target.")
        }
        if (plan.organizationId == null) {
            return rejected("Runtime installation plan organizationId is required for platform-managed K3S runtime agent startup.")
        }
        val manifest = prepareRuntimeAgentManifest(
            properties,
            input.runtimeAgentId,
            input.runtimeInfrastructureId,
            plan
        )

        val apply = runner.run(
            properties,
            listOf("apply", "-n", properties.namespace, "-f", manifest.manifestFile)
        )
        if (!apply.succeeded) {
            val failure = k3sCommandFailure("Kubernetes API apply ${manifest.manifestFile} in ${properties.namespace}", apply, properties)
            log.debug("K3S runtime agent deployment unavailable during apply: {}", failure)
            return DeployRuntimeAgentResult.Unavailable(failure)
        }

        for (dependencyDeploymentName in manifest.dependencyDeploymentNames) {
            val rollout = runner.run(
                properties,
                listOf("rollout", "status", "deployment/$dependencyDeploymentName", "-n", properties.namespace)
            )
            if (!rollout.succeeded) {
                val failure = k3sCommandFailure("Kubernetes API await Deployment $dependencyDeploymentName in ${properties.namespace}", rollout, properties)
                log.debug("K3S runtime agent dependency deployment unavailable during rollout: {}", failure)
                return DeployRuntimeAgentResult.Unavailable(failure)
            }
        }

        val agentRollout = runner.run(
            properties,
            listOf("rollout", "status", "deployment/${manifest.deploymentName}", "-n", properties.namespace)
        )
        if (!agentRollout.succeeded) {
            val failure = k3sCommandFailure("Kubernetes API await Deployment ${manifest.deploymentName} in ${properties.namespace}", agentRollout, properties)
            log.debug("K3S runtime agent deployment unavailable during rollout: {}", failure)
            return DeployRuntimeAgentResult.Unavailable(failure)
        }

        for (frontendDeploymentName in manifest.frontendDeploymentNames) {
            val rollout = runner.run(
                properties,
                listOf("rollout", "status", "deployment/$frontendDeploymentName", "-n", properties.namespace)
            )
            if (!rollout.succeeded) {
                val failure = k3sCommandFailure("Kubernetes API await Deployment $frontendDeploymentName in ${properties.namespace}", rollout, properties)
                log.debug("K3S runtime agent frontend unavailable during rollout: {}", failure)
                return DeployRuntimeAgentResult.Unavailable(failure)
            }
        }

        log.debug(
            "K3S runtime agent deployed runtimeInfrastructureId={}, runtimeAgentId={}, deployment={}, agentVersion={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            manifest.deploymentName,
            properties.agentVersion
        )
        return DeployRuntimeAgentResult.Succeeded(
            agentVersion = properties.agentVersion
        )
    }

    private fun rejected(failureReason: String): DeployRuntimeAgentResult.Rejected {
        log.debug("K3S runtime agent deployment rejected: {}", failureReason)
        return DeployRuntimeAgentResult.Rejected(failureReason)
    }

    companion object {
        private val log = LoggerFactory.getLogger(K3sDeployRuntimeAgentAdapter::class.java)
    }
}
