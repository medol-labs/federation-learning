package tech.medo.runtimemonitoring.recordruntimetelemetry

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
@RequestMapping("/noderuntimehealth")
class RecordRuntimeTelemetryResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('record_runtime_telemetry:execute')")
    @PostMapping("/recordruntimetelemetry")
    fun RecordRuntimeTelemetry(
        @Valid @RequestBody command: RecordRuntimeTelemetryCommand,
        request: HttpServletRequest
    ): CompletableFuture<RecordRuntimeTelemetryCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
