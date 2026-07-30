package tech.medo.infrastructure.secondary.runtimeagentoperations.agentruntimeinfrastructureconnection.reportruntimeinstanceconnected

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@FeignClient(
    name = "federationLearningPlatformRuntimeConnectionReportingClient",
    url = "\${runtime-agent.platform-connection-reporting.platform-url:http://localhost:8080}"
)
interface PlatformRuntimeConnectionReportingClient {
    @PostMapping("/runtimeinfrastructure/recordruntimeconnectionestablished")
    fun recordRuntimeConnectionEstablished(
        @RequestBody request: RecordRuntimeConnectionEstablishedRequest
    ): RecordRuntimeConnectionEstablishedResponse
}

data class RecordRuntimeConnectionEstablishedRequest(
    val runtimeInfrastructureId: UUID,
    val runtimeAgentId: UUID,
    val agentInstallMode: String,
    val organizationId: UUID,
    val runtimeName: String
)

data class RecordRuntimeConnectionEstablishedResponse(
    val runtimeInfrastructureId: UUID? = null,
    val runtimeAgentId: UUID? = null,
    val agentInstallMode: String? = null,
    val organizationId: UUID? = null,
    val runtimeName: String? = null
)
