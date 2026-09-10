package tech.medo.trainingorchestration.aggregateplainmodelupdates

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class AggregatePlainUpdatesWhenQuorumReachedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ModelUpdateSubmissionAcceptedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.plainAggregationReady == true) {
            commandGateway.send(AggregatePlainModelUpdatesCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, aggregationAlgorithm = event.aggregationAlgorithm!!, modelUpdateArtifactRefs = event.acceptedModelUpdateArtifactRefs)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
