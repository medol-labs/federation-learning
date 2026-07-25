package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.verifyruntimeinfrastructure.k3s

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.dockercompose.RuntimeProvisioningLookup
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.K3sCommandRunner
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.K3sRuntimeInfrastructureProperties
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.isK3sPackage
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.deployruntimeagent.k3s.k3sCommandFailure
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureService

@Component
class K3sVerifyRuntimeInfrastructureAdapter(
    private val properties: K3sRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: K3sCommandRunner
) : VerifyRuntimeInfrastructureService {
    override fun supports(input: RuntimeInfrastructureVerificationInput): Boolean {
        val supported = properties.enabled && lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?.let(lookup::findPackage)
            ?.let { isK3sPackage(it, properties) } == true
        log.debug(
            "K3S verify supports runtimeInfrastructureId={}, enabled={}, supported={}",
            input.runtimeInfrastructureId,
            properties.enabled,
            supported
        )
        return supported
    }

    override fun verify(input: RuntimeInfrastructureVerificationInput): RuntimeInfrastructureVerification {
        log.debug("Verifying K3S runtime infrastructure runtimeInfrastructureId={}", input.runtimeInfrastructureId)
        val plan = lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?: return rejected("Runtime installation plan was not found for runtimeInfrastructureId=${input.runtimeInfrastructureId}.")
        val runtimePackage = lookup.findPackage(plan)
            ?: return rejected("Runtime infrastructure package was not found for runtimeInfrastructurePackageId=${plan.runtimeInfrastructurePackageId}.")
        if (!isK3sPackage(runtimePackage, properties)) {
            return rejected("Runtime infrastructure package is not a K3S target.")
        }

        val version = runner.run(properties, listOf("version", "--client"))
        if (!version.succeeded) {
            val failure = k3sCommandFailure("kubectl version --client", version, properties)
            log.debug("K3S verification unavailable: {}", failure)
            return RuntimeInfrastructureVerification.Unavailable(failure)
        }

        val nodes = runner.run(properties, listOf("get", "nodes", "-o", "name"))
        if (!nodes.succeeded) {
            return rejected(k3sCommandFailure("kubectl get nodes -o name", nodes, properties))
        }

        val observedNodeCount = nodes.output
            .lineSequence()
            .count { it.isNotBlank() }
        if (observedNodeCount <= 0) {
            return rejected("K3S cluster has no observable nodes.")
        }

        log.debug(
            "K3S runtime infrastructure verified runtimeInfrastructureId={}, observedNodeCount={}",
            input.runtimeInfrastructureId,
            observedNodeCount
        )
        return RuntimeInfrastructureVerification.Succeeded(
            agentInstallMode = plan.agentInstallMode ?: input.agentInstallMode,
            observedNodeCount = observedNodeCount
        )
    }

    private fun rejected(failureReason: String): RuntimeInfrastructureVerification.Rejected {
        log.debug("K3S verification rejected: {}", failureReason)
        return RuntimeInfrastructureVerification.Rejected(observedNodeCount = 0, failureReason = failureReason)
    }

    companion object {
        private val log = LoggerFactory.getLogger(K3sVerifyRuntimeInfrastructureAdapter::class.java)
    }
}
