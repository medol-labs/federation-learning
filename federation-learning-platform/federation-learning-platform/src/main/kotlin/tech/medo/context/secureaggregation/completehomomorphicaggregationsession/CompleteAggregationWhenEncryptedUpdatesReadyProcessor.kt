package tech.medo.secureaggregation.completehomomorphicaggregationsession

import tech.medo.secureaggregation.events.EncryptedModelUpdateReceivedEvent
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-secure-aggregation-complete-homomorphic-aggregation-session")
@Component
class CompleteAggregationWhenEncryptedUpdatesReadyProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: EncryptedModelUpdateReceivedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.receivedEncryptedUpdateCount >= event.selectedParticipantCount) {
            commandGateway.send(CompleteHomomorphicAggregationSessionCommand(secureAggregationSessionId = event.secureAggregationSessionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, encryptedUpdateArtifactRefs = event.receivedEncryptedUpdateArtifactRefs)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
