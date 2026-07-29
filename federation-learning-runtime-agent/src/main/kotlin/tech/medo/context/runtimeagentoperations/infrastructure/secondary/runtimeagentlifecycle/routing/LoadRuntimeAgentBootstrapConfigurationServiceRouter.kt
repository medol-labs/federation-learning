package tech.medo.runtimeagentoperations.infrastructure.secondary.runtimeagentlifecycle.routing

import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationInput
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationService
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class LoadRuntimeAgentBootstrapConfigurationServiceRouter(private val adapters: ObjectProvider<LoadRuntimeAgentBootstrapConfigurationService>) : LoadRuntimeAgentBootstrapConfigurationService {
    override fun supports(input: LoadRuntimeAgentBootstrapConfigurationInput): Boolean = true

    override fun execute(input: LoadRuntimeAgentBootstrapConfigurationInput): LoadRuntimeAgentBootstrapConfigurationResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                LoadRuntimeAgentBootstrapConfigurationResult.Unavailable(
                    failureReason = ex.message ?: "LoadRuntimeAgentBootstrapConfigurationService is unavailable."
                )
            }
            0 -> LoadRuntimeAgentBootstrapConfigurationResult.Unavailable(
                failureReason = "No LoadRuntimeAgentBootstrapConfigurationService adapter supports the requested input."
            )
            else -> LoadRuntimeAgentBootstrapConfigurationResult.Unavailable(
                failureReason = "Multiple LoadRuntimeAgentBootstrapConfigurationService adapters support the requested input."
            )
        }
    }
}
