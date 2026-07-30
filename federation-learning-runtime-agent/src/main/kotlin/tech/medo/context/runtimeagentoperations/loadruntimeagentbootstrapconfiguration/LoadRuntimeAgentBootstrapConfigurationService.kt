package tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration

import java.util.UUID;

interface LoadRuntimeAgentBootstrapConfigurationService {
    fun supports(input: LoadRuntimeAgentBootstrapConfigurationInput): Boolean = true
    fun execute(input: LoadRuntimeAgentBootstrapConfigurationInput): LoadRuntimeAgentBootstrapConfigurationResult
}

data class LoadRuntimeAgentBootstrapConfigurationInput(
    val bootstrapRequestId: UUID
)

sealed interface LoadRuntimeAgentBootstrapConfigurationResult {
    data class Succeeded(
        val runtimeAgentId: UUID,
        val runtimeInfrastructureId: UUID,
        val agentVersion: String,
        val bootstrapConfigurationLoaded: Boolean
    ) : LoadRuntimeAgentBootstrapConfigurationResult

    data class Rejected(
        val failureReason: String
    ) : LoadRuntimeAgentBootstrapConfigurationResult

    data class Unavailable(
        val failureReason: String
    ) : LoadRuntimeAgentBootstrapConfigurationResult
}
