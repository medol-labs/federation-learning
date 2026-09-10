package tech.medo.trainingorchestration.completemodelaggregation

import tech.medo.trainingorchestration.events.PlainModelAggregationCompletedEvent
import tech.medo.trainingorchestration.completemodelaggregation.CompleteModelAggregationCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class CompletePlainAggregationWhenCompletedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: PlainModelAggregationCompletedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(CompleteModelAggregationCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, secureAggregationSessionId = null /* TODO: provide secureAggregationSessionId */, aggregatedModelId = event.aggregatedModelId, aggregatedModelName = event.aggregatedModelName, aggregatedModelVersion = event.aggregatedModelVersion, aggregatedModelDescription = event.aggregatedModelDescription, modelSourceType = event.modelSourceType, aggregatedModelArtifactUri = event.aggregatedModelArtifactUri, aggregatedModelRegistryRef = event.aggregatedModelRegistryRef, modelFormat = event.modelFormat, modelArtifactDigest = event.modelArtifactDigest, aggregatedModelSignatureUri = event.aggregatedModelSignatureUri, aggregatedModelSizeBytes = event.aggregatedModelSizeBytes)).resultMessage
}
