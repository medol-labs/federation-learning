package tech.medo.runtimeagentoperations.acceptexecutionplan

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
class AcceptExecutionPlanResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('accept_execution_plan:execute')")
    @PostMapping("/acceptexecutionplan")
    fun AcceptExecutionPlan(
        @Valid @RequestBody command: AcceptExecutionPlanCommand,
        request: HttpServletRequest
    ): CompletableFuture<AcceptExecutionPlanCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
