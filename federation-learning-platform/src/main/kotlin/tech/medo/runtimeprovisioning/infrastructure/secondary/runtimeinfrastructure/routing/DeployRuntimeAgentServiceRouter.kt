package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.routing

import org.slf4j.LoggerFactory
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentInput
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentService
import tech.medo.runtimeprovisioning.deployruntimeagent.DeployRuntimeAgentResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class DeployRuntimeAgentServiceRouter(private val adapters: ObjectProvider<DeployRuntimeAgentService>) : DeployRuntimeAgentService {
    override fun supports(input: DeployRuntimeAgentInput): Boolean = true

    override fun execute(input: DeployRuntimeAgentInput): DeployRuntimeAgentResult {
        log.debug(
            "Routing DeployRuntimeAgent request runtimeInfrastructureId={}, runtimeAgentId={}",
            input.runtimeInfrastructureId,
            input.runtimeAgentId
        )
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        log.debug(
            "DeployRuntimeAgent adapter candidates count={}, adapters={}",
            candidates.size,
            candidates.map { it::class.simpleName }
        )
        return when (candidates.size) {
            1 -> try {
                val adapter = candidates.first()
                log.debug("DeployRuntimeAgent selected adapter={}", adapter::class.simpleName)
                adapter.execute(input).also {
                    log.debug("DeployRuntimeAgent adapter={} returned result={}", adapter::class.simpleName, it::class.simpleName)
                }
            } catch (ex: Exception) {
                log.warn("DeployRuntimeAgent adapter failed", ex)
                DeployRuntimeAgentResult.Unavailable(
                    failureReason = ex.message ?: "DeployRuntimeAgentService is unavailable."
                )
            }
            0 -> {
                log.debug("No DeployRuntimeAgent adapter supports runtimeInfrastructureId={}", input.runtimeInfrastructureId)
                DeployRuntimeAgentResult.Unavailable(
                    failureReason = "No DeployRuntimeAgentService adapter supports the requested input."
                )
            }
            else -> {
                log.debug("Multiple DeployRuntimeAgent adapters support runtimeInfrastructureId={}", input.runtimeInfrastructureId)
                DeployRuntimeAgentResult.Unavailable(
                    failureReason = "Multiple DeployRuntimeAgentService adapters support the requested input."
                )
            }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeployRuntimeAgentServiceRouter::class.java)
    }
}
