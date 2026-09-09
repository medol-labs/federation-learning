package tech.medo.trainingorchestration.retrytrainingroundparticipantselection

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
class RetryTrainingRoundParticipantSelectionResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('retry_training_round_participant_selection:execute')")
    @PostMapping("/retrytrainingroundparticipantselection")
    fun RetryTrainingRoundParticipantSelection(
        @Valid @RequestBody command: RetryTrainingRoundParticipantSelectionCommand,
        request: HttpServletRequest
    ): CompletableFuture<RetryTrainingRoundParticipantSelectionCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
