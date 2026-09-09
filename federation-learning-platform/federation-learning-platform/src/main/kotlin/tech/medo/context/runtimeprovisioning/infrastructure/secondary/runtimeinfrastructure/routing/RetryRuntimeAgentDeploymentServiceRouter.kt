package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.routing

import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentInput
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentService
import tech.medo.runtimeprovisioning.retryruntimeagentdeployment.RetryRuntimeAgentDeploymentResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RetryRuntimeAgentDeploymentServiceRouter(private val adapters: ObjectProvider<RetryRuntimeAgentDeploymentService>) : RetryRuntimeAgentDeploymentService {
    override fun supports(input: RetryRuntimeAgentDeploymentInput): Boolean = true

    override fun execute(input: RetryRuntimeAgentDeploymentInput): RetryRuntimeAgentDeploymentResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                RetryRuntimeAgentDeploymentResult.Unavailable(
                    failureReason = ex.message ?: "RetryRuntimeAgentDeploymentService is unavailable."
                )
            }
            0 -> RetryRuntimeAgentDeploymentResult.Unavailable(
                failureReason = "No RetryRuntimeAgentDeploymentService adapter supports the requested input."
            )
            else -> RetryRuntimeAgentDeploymentResult.Unavailable(
                failureReason = "Multiple RetryRuntimeAgentDeploymentService adapters support the requested input."
            )
        }
    }
}
