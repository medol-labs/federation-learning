package tech.medo.runtimemonitoring.detectruntimeagentoffline

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
class DetectRuntimeAgentOfflineResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('detect_runtime_agent_offline:execute')")
    @PostMapping("/detectruntimeagentoffline")
    fun DetectRuntimeAgentOffline(
        @Valid @RequestBody command: DetectRuntimeAgentOfflineCommand,
        request: HttpServletRequest
    ): CompletableFuture<DetectRuntimeAgentOfflineCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
