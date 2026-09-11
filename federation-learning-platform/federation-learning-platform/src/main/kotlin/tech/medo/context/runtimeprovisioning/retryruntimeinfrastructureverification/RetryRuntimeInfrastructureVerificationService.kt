package tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification

import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;

interface RetryRuntimeInfrastructureVerificationService {
    fun supports(input: RetryRuntimeInfrastructureVerificationInput): Boolean = true
    fun execute(input: RetryRuntimeInfrastructureVerificationInput): RetryRuntimeInfrastructureVerificationResult
}

data class RetryRuntimeInfrastructureVerificationInput(
    val runtimeInfrastructureId: UUID,
    val runtimeInstallationPlanId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeInfrastructurePackageId: UUID,
    val runtimeInfrastructurePackageName: String?,
    val runtimeInfrastructurePackageVersion: String?,
    val runtimeEnvironmentType: String?,
    val runtimeName: String,
    val runtimeAgentId: UUID,
    val agentInstallMode: String,
    val expectedNodeCount: Int,
    val currentRuntimeInfrastructureState: RuntimeInfrastructureStateEnum,
    val retryReason: String
)

sealed interface RetryRuntimeInfrastructureVerificationResult {
    data class Succeeded(
        val observedNodeCount: Int
    ) : RetryRuntimeInfrastructureVerificationResult

    data class Rejected(
        val observedNodeCount: Int?,
        val failureReason: String
    ) : RetryRuntimeInfrastructureVerificationResult

    data class Unavailable(
        val failureReason: String
    ) : RetryRuntimeInfrastructureVerificationResult
}
