package tech.medo.trainingorchestration.selecttrainingroundparticipants

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
class SelectTrainingRoundParticipantsResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('select_training_round_participants:execute')")
    @PostMapping("/selecttrainingroundparticipants")
    fun SelectTrainingRoundParticipants(
        @Valid @RequestBody command: SelectTrainingRoundParticipantsCommand,
        request: HttpServletRequest
    ): CompletableFuture<SelectTrainingRoundParticipantsCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
