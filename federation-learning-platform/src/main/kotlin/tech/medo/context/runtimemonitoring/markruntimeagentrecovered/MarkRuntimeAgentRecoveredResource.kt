package tech.medo.runtimemonitoring.markruntimeagentrecovered

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
class MarkRuntimeAgentRecoveredResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('mark_runtime_agent_recovered:execute')")
    @PostMapping("/markruntimeagentrecovered")
    fun MarkRuntimeAgentRecovered(
        @Valid @RequestBody command: MarkRuntimeAgentRecoveredCommand,
        request: HttpServletRequest
    ): CompletableFuture<MarkRuntimeAgentRecoveredCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
