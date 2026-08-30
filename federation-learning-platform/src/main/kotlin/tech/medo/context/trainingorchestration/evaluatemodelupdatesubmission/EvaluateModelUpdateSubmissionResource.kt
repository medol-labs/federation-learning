package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

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
@RequestMapping("/traininground")
class EvaluateModelUpdateSubmissionResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('evaluate_model_update_submission:execute')")
    @PostMapping("/evaluatemodelupdatesubmission")
    fun EvaluateModelUpdateSubmission(
        @Valid @RequestBody command: EvaluateModelUpdateSubmissionCommand,
        request: HttpServletRequest
    ): CompletableFuture<EvaluateModelUpdateSubmissionCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
