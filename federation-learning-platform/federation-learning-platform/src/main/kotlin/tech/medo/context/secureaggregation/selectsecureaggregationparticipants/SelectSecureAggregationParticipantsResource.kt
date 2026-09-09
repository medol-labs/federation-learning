package tech.medo.secureaggregation.selectsecureaggregationparticipants

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
@RequestMapping("/secureaggregationsession")
class SelectSecureAggregationParticipantsResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('select_secure_aggregation_participants:execute')")
    @PostMapping("/selectsecureaggregationparticipants")
    fun SelectSecureAggregationParticipants(
        @Valid @RequestBody command: SelectSecureAggregationParticipantsCommand,
        request: HttpServletRequest
    ): CompletableFuture<SelectSecureAggregationParticipantsCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
