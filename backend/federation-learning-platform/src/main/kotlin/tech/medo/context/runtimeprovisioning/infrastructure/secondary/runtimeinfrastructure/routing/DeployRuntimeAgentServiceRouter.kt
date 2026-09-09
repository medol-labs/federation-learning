package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.routing

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
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                DeployRuntimeAgentResult.Unavailable(
                    failureReason = ex.message ?: "DeployRuntimeAgentService is unavailable."
                )
            }
            0 -> DeployRuntimeAgentResult.Unavailable(
                failureReason = "No DeployRuntimeAgentService adapter supports the requested input."
            )
            else -> DeployRuntimeAgentResult.Unavailable(
                failureReason = "Multiple DeployRuntimeAgentService adapters support the requested input."
            )
        }
    }
}
