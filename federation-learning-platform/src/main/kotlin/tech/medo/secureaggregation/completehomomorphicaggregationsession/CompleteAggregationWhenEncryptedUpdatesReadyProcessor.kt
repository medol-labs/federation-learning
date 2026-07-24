package tech.medo.secureaggregation.completehomomorphicaggregationsession

import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class CompleteAggregationWhenEncryptedUpdatesReadyProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: EncryptedModelUpdateReceivedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(CompleteHomomorphicAggregationSessionCommand(secureAggregationSessionId = event.secureAggregationSessionId, trainingJobId = java.util.UUID.randomUUID() /* TODO: provide trainingJobId */, trainingRunConfigurationId = java.util.UUID.randomUUID() /* TODO: provide trainingRunConfigurationId */, featureSchemaId = java.util.UUID.randomUUID() /* TODO: provide featureSchemaId */, roundId = java.util.UUID.randomUUID() /* TODO: provide roundId */, aggregatedModelVersionId = java.util.UUID.randomUUID() /* TODO: provide aggregatedModelVersionId */, modelFormat = "" /* TODO: provide modelFormat */, modelHash = "" /* TODO: provide modelHash */)).resultMessage
}
