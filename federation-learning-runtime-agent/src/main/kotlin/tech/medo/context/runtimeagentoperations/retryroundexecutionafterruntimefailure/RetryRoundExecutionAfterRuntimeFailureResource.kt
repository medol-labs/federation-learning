package tech.medo.runtimeagentoperations.retryroundexecutionafterruntimefailure

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
@RequestMapping("/roundexecution")
class RetryRoundExecutionAfterRuntimeFailureResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('retry_round_execution_after_runtime_failure:execute')")
    @PostMapping("/retryroundexecutionafterruntimefailure")
    fun RetryRoundExecutionAfterRuntimeFailure(
        @Valid @RequestBody command: RetryRoundExecutionAfterRuntimeFailureCommand,
        request: HttpServletRequest
    ): CompletableFuture<RetryRoundExecutionAfterRuntimeFailureCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
