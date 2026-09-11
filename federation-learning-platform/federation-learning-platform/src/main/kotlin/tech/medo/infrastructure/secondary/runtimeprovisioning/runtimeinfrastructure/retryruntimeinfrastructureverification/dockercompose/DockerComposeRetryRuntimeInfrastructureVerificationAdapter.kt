package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.retryruntimeinfrastructureverification.dockercompose

import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeinfrastructure.verifyruntimeinfrastructure.dockercompose.DockerComposeVerifyRuntimeInfrastructureAdapter
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationResult
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationService
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput

@Component
class DockerComposeRetryRuntimeInfrastructureVerificationAdapter(
    private val verifyAdapter: DockerComposeVerifyRuntimeInfrastructureAdapter
) : RetryRuntimeInfrastructureVerificationService {
    override fun supports(input: RetryRuntimeInfrastructureVerificationInput): Boolean =
        verifyAdapter.supports(input.toVerifyInput())

    override fun execute(input: RetryRuntimeInfrastructureVerificationInput): RetryRuntimeInfrastructureVerificationResult =
        when (val result = verifyAdapter.verify(input.toVerifyInput())) {
            is RuntimeInfrastructureVerification.Succeeded ->
                RetryRuntimeInfrastructureVerificationResult.Succeeded(
                    observedNodeCount = result.observedNodeCount
                )

            is RuntimeInfrastructureVerification.Rejected ->
                RetryRuntimeInfrastructureVerificationResult.Rejected(
                    observedNodeCount = result.observedNodeCount,
                    failureReason = result.failureReason
                )

            is RuntimeInfrastructureVerification.Unavailable ->
                RetryRuntimeInfrastructureVerificationResult.Unavailable(
                    failureReason = result.failureReason
                )
        }

    private fun RetryRuntimeInfrastructureVerificationInput.toVerifyInput(): RuntimeInfrastructureVerificationInput =
        RuntimeInfrastructureVerificationInput(
            runtimeInfrastructureId = runtimeInfrastructureId,
            runtimeInstallationPlanId = runtimeInstallationPlanId,
            organizationId = organizationId,
            organizationName = organizationName,
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            runtimeInfrastructurePackageName = runtimeInfrastructurePackageName,
            runtimeInfrastructurePackageVersion = runtimeInfrastructurePackageVersion,
            runtimeEnvironmentType = runtimeEnvironmentType,
            runtimeName = runtimeName,
            expectedNodeCount = expectedNodeCount,
            runtimeAgentId = runtimeAgentId
        )
}
