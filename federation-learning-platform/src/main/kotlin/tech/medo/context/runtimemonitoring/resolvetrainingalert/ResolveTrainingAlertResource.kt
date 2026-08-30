package tech.medo.runtimemonitoring.resolvetrainingalert

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
@RequestMapping("/trainingalert")
class ResolveTrainingAlertResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('resolve_training_alert:execute')")
    @PostMapping("/resolvetrainingalert")
    fun ResolveTrainingAlert(
        @Valid @RequestBody command: ResolveTrainingAlertCommand,
        request: HttpServletRequest
    ): CompletableFuture<ResolveTrainingAlertCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
