package tech.medo.trainingorchestration.completetraininground

import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-training-orchestration-complete-training-round")
@Component
class FinishRoundAfterGlobalModelEvaluatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: GlobalModelEvaluationSubmittedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(CompleteTrainingRoundCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, aggregatedModelId = event.aggregatedModelId, aggregatedModelArtifactUri = event.aggregatedModelArtifactUri, aggregatedModelRegistryRef = event.aggregatedModelRegistryRef, modelFormat = event.modelFormat, modelArtifactDigest = event.modelArtifactDigest, aggregatedModelSignatureUri = event.aggregatedModelSignatureUri, globalAccuracy = event.globalAccuracy)).resultMessage
}
