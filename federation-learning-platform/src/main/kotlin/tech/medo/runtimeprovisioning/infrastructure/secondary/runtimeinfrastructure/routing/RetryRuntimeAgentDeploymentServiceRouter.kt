package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.routing

import org.slf4j.LoggerFactory
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
        log.debug(
            "Routing RetryRuntimeAgentDeployment request runtimeInfrastructureId={}, runtimeAgentId={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId
        )
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        log.debug(
            "RetryRuntimeAgentDeployment adapter candidates count={}, adapters={}",
            candidates.size,
            candidates.map { it::class.simpleName }
        )
        return when (candidates.size) {
            1 -> try {
                val adapter = candidates.first()
                log.debug("RetryRuntimeAgentDeployment selected adapter={}", adapter::class.simpleName)
                adapter.execute(input).also {
                    log.debug("RetryRuntimeAgentDeployment adapter={} returned result={}", adapter::class.simpleName, it::class.simpleName)
                }
            } catch (ex: Exception) {
                log.warn("RetryRuntimeAgentDeployment adapter failed", ex)
                RetryRuntimeAgentDeploymentResult.Unavailable(
                    failureReason = ex.message ?: "RetryRuntimeAgentDeploymentService is unavailable."
                )
            }
            0 -> {
                log.debug("No RetryRuntimeAgentDeployment adapter supports runtimeInfrastructureId={}", input.runtimeInfrastructureId)
                RetryRuntimeAgentDeploymentResult.Unavailable(
                    failureReason = "No RetryRuntimeAgentDeploymentService adapter supports the requested input."
                )
            }
            else -> {
                log.debug("Multiple RetryRuntimeAgentDeployment adapters support runtimeInfrastructureId={}", input.runtimeInfrastructureId)
                RetryRuntimeAgentDeploymentResult.Unavailable(
                    failureReason = "Multiple RetryRuntimeAgentDeploymentService adapters support the requested input."
                )
            }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(RetryRuntimeAgentDeploymentServiceRouter::class.java)
    }
}
