package tech.medo.infrastructure.secondary.runtimeagentoperations.agentruntimeinfrastructureconnection.reportruntimeinstanceconnected

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("runtime-agent.platform-connection-reporting")
data class RuntimePlatformConnectionReportingProperties(
    val enabled: Boolean = true,
    val platformUrl: String = "http://localhost:8080",
    val agentInstallMode: String = "MANUAL_GUIDED",
    val organizationId: String? = null,
    val runtimeName: String? = null,
    val runtimeAgentEndpoint: String? = null,
    val endpointScope: String = "LOCAL"
)
