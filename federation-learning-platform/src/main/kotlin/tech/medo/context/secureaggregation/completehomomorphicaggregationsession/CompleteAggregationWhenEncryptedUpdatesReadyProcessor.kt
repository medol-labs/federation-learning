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
        commandGateway.send(CompleteHomomorphicAggregationSessionCommand(secureAggregationSessionId = event.secureAggregationSessionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = 0 /* TODO: provide roundNumber */, aggregatedModelArtifactUri = "" /* TODO: provide aggregatedModelArtifactUri */, aggregatedModelRegistryRef = "" /* TODO: provide aggregatedModelRegistryRef */, modelFormat = "" /* TODO: provide modelFormat */, modelArtifactDigest = "" /* TODO: provide modelArtifactDigest */, aggregatedModelSignatureUri = null /* TODO: provide aggregatedModelSignatureUri */)).resultMessage
}
