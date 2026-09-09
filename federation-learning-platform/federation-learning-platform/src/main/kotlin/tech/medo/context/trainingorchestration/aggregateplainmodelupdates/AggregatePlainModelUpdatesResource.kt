package tech.medo.trainingorchestration.aggregateplainmodelupdates

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
class AggregatePlainModelUpdatesResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('aggregate_plain_model_updates:execute')")
    @PostMapping("/aggregateplainmodelupdates")
    fun AggregatePlainModelUpdates(
        @Valid @RequestBody command: AggregatePlainModelUpdatesCommand,
        request: HttpServletRequest
    ): CompletableFuture<AggregatePlainModelUpdatesCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
