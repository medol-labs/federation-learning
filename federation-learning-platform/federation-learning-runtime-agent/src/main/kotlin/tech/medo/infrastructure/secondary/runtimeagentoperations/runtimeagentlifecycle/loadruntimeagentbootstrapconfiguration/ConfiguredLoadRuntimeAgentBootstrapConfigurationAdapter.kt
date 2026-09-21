package tech.medo.infrastructure.secondary.runtimeagentoperations.runtimeagentlifecycle.loadruntimeagentbootstrapconfiguration

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationInput
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationResult
import tech.medo.runtimeagentoperations.loadruntimeagentbootstrapconfiguration.LoadRuntimeAgentBootstrapConfigurationService
import java.util.UUID

@Component
class ConfiguredLoadRuntimeAgentBootstrapConfigurationAdapter(
    private val properties: RuntimeAgentBootstrapConfigurationProperties
) : LoadRuntimeAgentBootstrapConfigurationService {
    override fun execute(input: LoadRuntimeAgentBootstrapConfigurationInput): LoadRuntimeAgentBootstrapConfigurationResult {
        val missingFields = missingFields()
        if (missingFields.isNotEmpty()) {
            return LoadRuntimeAgentBootstrapConfigurationResult.Rejected(
                failureReason = "Runtime agent bootstrap configuration is incomplete: ${missingFields.joinToString(", ")}."
            )
        }

        return LoadRuntimeAgentBootstrapConfigurationResult.Succeeded(
            runtimeAgentId = configuredUuid(properties.runtimeAgentId)!!,
            runtimeInfrastructureId = configuredUuid(properties.runtimeInfrastructureId)!!,
            agentVersion = properties.agentVersion.trim(),
            runtimeAgentEndpoint = properties.runtimeAgentEndpoint!!.trim(),
            endpointScope = properties.endpointScope.trim(),
            bootstrapConfigurationLoaded = true
        )
    }

    private fun missingFields(): List<String> {
        val missing = mutableListOf<String>()
        if (!properties.enabled) missing += "runtime-agent.bootstrap.enabled"
        if (configuredUuid(properties.runtimeAgentId) == null) missing += "runtime-agent.bootstrap.runtime-agent-id"
        if (configuredUuid(properties.runtimeInfrastructureId) == null) missing += "runtime-agent.bootstrap.runtime-infrastructure-id"
        if (properties.agentVersion.isBlank()) missing += "runtime-agent.bootstrap.agent-version"
        if (properties.runtimeAgentEndpoint.isNullOrBlank()) missing += "runtime-agent.bootstrap.runtime-agent-endpoint"
        if (properties.endpointScope.isBlank()) missing += "runtime-agent.bootstrap.endpoint-scope"
        return missing
    }

    private fun configuredUuid(value: String?): UUID? =
        value?.trim()?.takeIf { it.isNotEmpty() }?.let { runCatching { UUID.fromString(it) }.getOrNull() }
}
