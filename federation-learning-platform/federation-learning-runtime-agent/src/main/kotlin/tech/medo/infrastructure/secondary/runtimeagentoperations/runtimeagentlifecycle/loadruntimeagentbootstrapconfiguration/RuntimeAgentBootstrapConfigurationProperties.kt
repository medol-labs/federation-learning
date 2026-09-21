package tech.medo.infrastructure.secondary.runtimeagentoperations.runtimeagentlifecycle.loadruntimeagentbootstrapconfiguration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("runtime-agent.bootstrap")
data class RuntimeAgentBootstrapConfigurationProperties(
    val runtimeAgentId: String? = null,
    val runtimeInfrastructureId: String? = null,
    val agentVersion: String = "local-dev",
    val runtimeAgentEndpoint: String? = null,
    val endpointScope: String = "LOCAL",
    val enabled: Boolean = true
)
