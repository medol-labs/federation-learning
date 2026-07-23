package tech.medo.secureaggregation.selectsecureaggregationparticipants

import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class SelectParticipantsWhenSessionCreatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: SecureAggregationSessionCreatedEvent): java.util.concurrent.CompletableFuture<SelectSecureAggregationParticipantsCommand> =
        commandGateway.send(SelectSecureAggregationParticipantsCommand(secureAggregationSessionId = event.secureAggregationSessionId, roundId = event.roundId, acceptedRuntimeIds = event.acceptedRuntimeIds, selectedRuntimeIds = emptyList() /* TODO: provide selectedRuntimeIds */, selectedParticipantCount = 0 /* TODO: provide selectedParticipantCount */)).resultMessage.thenApply { it.payload() as SelectSecureAggregationParticipantsCommand }
}
