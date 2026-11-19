package tech.medo.trainingorchestration.recoverhistoricaltraininground

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
class RecoverHistoricalTrainingRoundResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('recover_historical_training_round:execute')")
    @PostMapping("/recoverhistoricaltraininground")
    fun RecoverHistoricalTrainingRound(
        @Valid @RequestBody command: RecoverHistoricalTrainingRoundCommand,
        request: HttpServletRequest
    ): CompletableFuture<RecoverHistoricalTrainingRoundCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
