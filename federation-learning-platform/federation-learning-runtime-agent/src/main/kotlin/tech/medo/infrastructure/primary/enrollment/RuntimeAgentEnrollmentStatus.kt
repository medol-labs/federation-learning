package tech.medo.infrastructure.primary.enrollment

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.slf4j.LoggerFactory
import tech.medo.infrastructure.secondary.runtimeagentoperations.runtimeagentlifecycle.loadruntimeagentbootstrapconfiguration.RuntimeAgentBootstrapConfigurationProperties
import tech.medo.infrastructure.secondary.runtimeagentoperations.agentruntimeinfrastructureconnection.reportruntimeinstanceconnected.RuntimePlatformConnectionReportingProperties
import tech.medo.shared.security.MedolSecurityProperties
import java.net.URI
import java.util.UUID

@Component("runtimeAgentEnrollment")
class RuntimeAgentEnrollmentStatus(
    private val bootstrap: RuntimeAgentBootstrapConfigurationProperties,
    private val connection: RuntimePlatformConnectionReportingProperties,
    private val security: MedolSecurityProperties
) : HealthIndicator {
    fun missingConfiguration(): List<String> = buildList {
        if (!bootstrap.enabled) add("runtime-agent.bootstrap.enabled")
        if (!validId(bootstrap.runtimeAgentId)) add("runtime-agent.bootstrap.runtime-agent-id")
        if (!validId(bootstrap.runtimeInfrastructureId)) add("runtime-agent.bootstrap.runtime-infrastructure-id")
        if (!validUrl(bootstrap.runtimeAgentEndpoint)) add("runtime-agent.bootstrap.runtime-agent-endpoint")
        if (bootstrap.agentVersion.isBlank()) add("runtime-agent.bootstrap.agent-version")
        if (bootstrap.endpointScope.isBlank()) add("runtime-agent.bootstrap.endpoint-scope")
        if (!connection.enabled) add("runtime-agent.platform-connection-reporting.enabled")
        if (!validUrl(connection.platformUrl)) add("runtime-agent.platform-connection-reporting.platform-url")
        if (security.internalToken.isBlank()) add("medol.security.internal-token")
    }

    fun configured(): Boolean = missingConfiguration().isEmpty()

    @EventListener(ApplicationReadyEvent::class)
    fun reportEnrollmentStatus() {
        LoggerFactory.getLogger(javaClass).info(
            "Runtime agent enrollment status={}, missingConfiguration={}",
            if (configured()) "CONFIGURED" else "WAITING_FOR_CONFIGURATION",
            missingConfiguration()
        )
    }

    override fun health(): Health = Health.up()
        .withDetail("enrollmentStatus", if (configured()) "CONFIGURED" else "WAITING_FOR_CONFIGURATION")
        .withDetail("missingConfiguration", missingConfiguration())
        .build()

    private fun validId(value: String?): Boolean =
        value?.trim()?.let { runCatching { UUID.fromString(it) }.isSuccess } == true

    private fun validUrl(value: String?): Boolean = value?.trim()?.let {
        runCatching { URI(it).let { uri -> uri.scheme in setOf("http", "https") && !uri.host.isNullOrBlank() && uri.userInfo == null } }.getOrDefault(false)
    } == true
}
