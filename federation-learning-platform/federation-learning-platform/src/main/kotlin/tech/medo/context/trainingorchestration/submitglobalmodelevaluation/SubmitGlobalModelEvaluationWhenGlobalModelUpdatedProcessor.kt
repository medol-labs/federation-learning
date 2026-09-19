package tech.medo.trainingorchestration.submitglobalmodelevaluation

import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-training-orchestration-submit-global-model-evaluation")
@Component
class SubmitGlobalModelEvaluationWhenGlobalModelUpdatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: GlobalModelUpdatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SubmitGlobalModelEvaluationCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, trainingJobObjective = event.trainingJobObjective, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, maxRounds = event.maxRounds, minimumAccuracy = event.minimumAccuracy, aggregatedModelId = event.aggregatedModelId, modelPlugin = event.modelPlugin, aggregatedModelArtifactUri = event.aggregatedModelArtifactUri, aggregatedModelRegistryRef = event.aggregatedModelRegistryRef, modelFormat = event.modelFormat, modelArtifactDigest = event.modelArtifactDigest, aggregatedModelSignatureUri = event.aggregatedModelSignatureUri)).resultMessage
}
