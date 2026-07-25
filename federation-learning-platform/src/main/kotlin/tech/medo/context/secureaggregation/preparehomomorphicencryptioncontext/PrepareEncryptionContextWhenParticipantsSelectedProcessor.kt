package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import tech.medo.secureaggregation.events.SecureAggregationParticipantsSelectedEvent
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class PrepareEncryptionContextWhenParticipantsSelectedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: SecureAggregationParticipantsSelectedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(PrepareHomomorphicEncryptionContextCommand(secureAggregationSessionId = event.secureAggregationSessionId, encryptionScheme = "" /* TODO: provide encryptionScheme */, publicKeyVersion = "" /* TODO: provide publicKeyVersion */, encryptedParameterScale = 0 /* TODO: provide encryptedParameterScale */)).resultMessage
}
