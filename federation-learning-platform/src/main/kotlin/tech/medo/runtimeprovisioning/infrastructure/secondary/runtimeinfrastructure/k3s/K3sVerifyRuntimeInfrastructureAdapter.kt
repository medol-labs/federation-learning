package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.k3s

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.dockercompose.RuntimeProvisioningLookup
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureService

@Component
class K3sVerifyRuntimeInfrastructureAdapter(
    private val properties: K3sRuntimeInfrastructureProperties,
    private val lookup: RuntimeProvisioningLookup,
    private val runner: K3sCommandRunner
) : VerifyRuntimeInfrastructureService {
    override fun supports(input: RuntimeInfrastructureVerificationInput): Boolean =
        properties.enabled && lookup.findPlanByRuntimeInfrastructureId(input.runtimeInfrastructureId)
            ?.let(lookup::findPackage)
            ?.let { isK3sPackage(it, properties) } == true

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
        if (!isK3sPackage(runtimePackage, properties)) {
            return RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = "Runtime infrastructure package is not a K3S target."
            )
        }

        val version = runner.run(properties, listOf("version", "--client"))
        if (!version.succeeded) {
            return RuntimeInfrastructureVerification.Unavailable(
                k3sCommandFailure("kubectl version --client", version, properties)
            )
        }

        val nodes = runner.run(properties, listOf("get", "nodes", "-o", "name"))
        if (!nodes.succeeded) {
            return RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = k3sCommandFailure("kubectl get nodes -o name", nodes, properties)
            )
        }

        val observedNodeCount = nodes.output
            .lineSequence()
            .count { it.isNotBlank() }
        if (observedNodeCount <= 0) {
            return RuntimeInfrastructureVerification.Rejected(
                observedNodeCount = 0,
                failureReason = "K3S cluster has no observable nodes."
            )
        }

        return RuntimeInfrastructureVerification.Succeeded(
            agentInstallMode = plan.agentInstallMode ?: input.agentInstallMode,
            observedNodeCount = observedNodeCount
        )
    }
}
