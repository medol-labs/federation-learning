package tech.medo.trainingorchestration.submitglobalmodelevaluation

import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.trainingorchestration.submitglobalmodelevaluation.SubmitGlobalModelEvaluationCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class SubmitGlobalModelEvaluationWhenGlobalModelUpdatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: GlobalModelUpdatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(SubmitGlobalModelEvaluationCommand(trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, featureSchemaId = event.featureSchemaId, roundId = event.roundId, roundNumber = event.roundNumber, aggregatedModelId = event.aggregatedModelId, aggregatedModelArtifactUri = event.aggregatedModelArtifactUri, aggregatedModelRegistryRef = event.aggregatedModelRegistryRef, modelFormat = event.modelFormat, modelArtifactDigest = event.modelArtifactDigest, aggregatedModelSignatureUri = event.aggregatedModelSignatureUri)).resultMessage
}
