package tech.medo.runtimeprovisioning.retryruntimeagentdeployment

import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;

interface RetryRuntimeAgentDeploymentService {
    fun supports(input: RetryRuntimeAgentDeploymentInput): Boolean = true
    fun execute(input: RetryRuntimeAgentDeploymentInput): RetryRuntimeAgentDeploymentResult
}

data class RetryRuntimeAgentDeploymentInput(
    val runtimeAgentId: UUID,
    val runtimeInfrastructureId: UUID,
    val currentRuntimeInfrastructureState: RuntimeInfrastructureStateEnum,
    val retryReason: String
)

sealed interface RetryRuntimeAgentDeploymentResult {
    data class Succeeded(
        val agentVersion: String
    ) : RetryRuntimeAgentDeploymentResult

    data class Rejected(
        val failureReason: String
    ) : RetryRuntimeAgentDeploymentResult

    data class Unavailable(
        val failureReason: String
    ) : RetryRuntimeAgentDeploymentResult
}
