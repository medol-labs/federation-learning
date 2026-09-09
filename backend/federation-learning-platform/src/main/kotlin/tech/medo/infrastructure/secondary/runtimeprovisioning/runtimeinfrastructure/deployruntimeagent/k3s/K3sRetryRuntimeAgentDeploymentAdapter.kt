package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.RuntimeProvisioningLookup
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentInput
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentResult
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentService

@Component
class K3sRetryRuntimeAgentDeploymentAdapter(
    private val properties: K3sRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: K3sCommandRunner
) : RetryRuntimeAgentDeploymentService {
    override fun supports(input: RetryRuntimeAgentDeploymentInput): Boolean {
        val supported = properties.enabled && lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?.let(lookup::findPackage)
            ?.let { isK3sPackage(it, properties) } == true
        log.debug(
            "K3S retry supports runtimeInfrastructureId={}, runtimeAgentId={}, enabled={}, supported={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            properties.enabled,
            supported
        )
        return supported
    }

    override fun execute(input: RetryRuntimeAgentDeploymentInput): RetryRuntimeAgentDeploymentResult {
        log.debug(
            "Retrying runtime agent deployment with K3S runtimeInfrastructureId={}, runtimeAgentId={}, namespace={}",
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
            log.debug("K3S runtime agent deployment retry unavailable during apply: {}", failure)
            return RetryRuntimeAgentDeploymentResult.Unavailable(failure)
        }

        val rollout = runner.run(
            properties,
            listOf("rollout", "status", "deployment/${manifest.deploymentName}", "-n", properties.namespace)
        )
        if (!rollout.succeeded) {
            val failure = k3sCommandFailure("Kubernetes API await Deployment ${manifest.deploymentName} in ${properties.namespace}", rollout, properties)
            log.debug("K3S runtime agent deployment retry unavailable during rollout: {}", failure)
            return RetryRuntimeAgentDeploymentResult.Unavailable(failure)
        }

        log.debug(
            "K3S runtime agent deployment retried runtimeInfrastructureId={}, runtimeAgentId={}, deployment={}, agentVersion={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId,
            manifest.deploymentName,
            properties.agentVersion
        )
        return RetryRuntimeAgentDeploymentResult.Succeeded(
            agentVersion = properties.agentVersion
        )
    }

    private fun rejected(failureReason: String): RetryRuntimeAgentDeploymentResult.Rejected {
        log.debug("K3S runtime agent deployment retry rejected: {}", failureReason)
        return RetryRuntimeAgentDeploymentResult.Rejected(failureReason)
    }

    companion object {
        private val log = LoggerFactory.getLogger(K3sRetryRuntimeAgentDeploymentAdapter::class.java)
    }
}
