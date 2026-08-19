package tech.medo.infrastructure.secondary.runtimeagentoperations.agentruntimeinfrastructureconnection.reportruntimeinstanceconnected

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedInput
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedResult
import tech.medo.runtimeagentoperations.reportruntimeinstanceconnected.ReportRuntimeInstanceConnectedService
import java.util.UUID

@Component
class PlatformReportRuntimeInstanceConnectedAdapter(
    private val client: PlatformRuntimeConnectionReportingClient,
    private val properties: RuntimePlatformConnectionReportingProperties
) : ReportRuntimeInstanceConnectedService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun supports(input: ReportRuntimeInstanceConnectedInput): Boolean = properties.enabled

    override fun execute(input: ReportRuntimeInstanceConnectedInput): ReportRuntimeInstanceConnectedResult {
        val organizationId = configuredUuid(properties.organizationId)
        val runtimeName = properties.runtimeName?.trim().orEmpty()
        val runtimeAgentEndpoint = properties.runtimeAgentEndpoint?.trim().orEmpty()
        val endpointScope = properties.endpointScope.trim()
        val missing = buildList {
            if (properties.agentInstallMode.isBlank()) add("runtime-agent.platform-connection-reporting.agent-install-mode")
            if (organizationId == null) add("runtime-agent.platform-connection-reporting.organization-id")
            if (runtimeName.isBlank()) add("runtime-agent.platform-connection-reporting.runtime-name")
            if (runtimeAgentEndpoint.isBlank()) add("runtime-agent.platform-connection-reporting.runtime-agent-endpoint")
            if (endpointScope.isBlank()) add("runtime-agent.platform-connection-reporting.endpoint-scope")
        }
        if (missing.isNotEmpty()) {
            return rejected(
                failureReason = "Runtime platform connection reporting configuration is incomplete: ${missing.joinToString(", ")}.",
                retryable = false
            )
        }

        val request = RecordRuntimeConnectionEstablishedRequest(
            runtimeInfrastructureId = input.runtimeInfrastructureId,
            runtimeAgentId = input.runtimeAgentId,
            agentInstallMode = properties.agentInstallMode.trim(),
            organizationId = organizationId!!,
            runtimeName = runtimeName,
            runtimeAgentEndpoint = runtimeAgentEndpoint,
            endpointScope = endpointScope
        )

        return try {
            logger.debug("Reporting runtime connection to platform. request={}", request)
            client.recordRuntimeConnectionEstablished(request)
            ReportRuntimeInstanceConnectedResult.Succeeded()
        } catch (ex: FeignException) {
            logger.warn(
                "Platform rejected runtime connection report. status={}, runtimeInfrastructureId={}, runtimeAgentId={}",
                ex.status(),
                input.runtimeInfrastructureId,
                input.runtimeAgentId,
                ex
            )
            rejected(
                failureReason = "Platform runtime connection report failed with status ${ex.status()}: ${ex.message}",
                retryable = ex.status() == -1 || ex.status() == 408 || ex.status() == 429 || ex.status() >= 500
            )
        } catch (ex: Exception) {
            logger.warn(
                "Platform runtime connection report unavailable. runtimeInfrastructureId={}, runtimeAgentId={}",
                input.runtimeInfrastructureId,
                input.runtimeAgentId,
                ex
            )
            rejected(
                failureReason = "Platform runtime connection report unavailable: ${ex.message ?: ex.javaClass.name}",
                retryable = true
            )
        }
    }

    private fun rejected(
        failureReason: String,
        retryable: Boolean
    ): ReportRuntimeInstanceConnectedResult.Rejected =
        ReportRuntimeInstanceConnectedResult.Rejected(
            failureReason = failureReason,
            retryable = retryable
        )

    private fun configuredUuid(value: String?): UUID? =
        value?.trim()?.takeIf { it.isNotEmpty() }?.let { runCatching { UUID.fromString(it) }.getOrNull() }
}
