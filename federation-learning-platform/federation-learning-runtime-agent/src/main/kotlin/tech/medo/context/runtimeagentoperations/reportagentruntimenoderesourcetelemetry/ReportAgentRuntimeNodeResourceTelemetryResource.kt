package tech.medo.runtimeagentoperations.reportagentruntimenoderesourcetelemetry

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.metadata.MetadataFactory


import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/agentruntimenoderesourcetelemetry")
class ReportAgentRuntimeNodeResourceTelemetryResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('report_agent_runtime_node_resource_telemetry:execute')")
    @PostMapping("/reportagentruntimenoderesourcetelemetry")
    fun ReportAgentRuntimeNodeResourceTelemetry(
        @Valid @RequestBody command: ReportAgentRuntimeNodeResourceTelemetryCommand,
        request: HttpServletRequest
    ): CompletableFuture<ReportAgentRuntimeNodeResourceTelemetryCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
