package tech.medo.modelrepository.registerfederatedmodelartifact

import tech.medo.trainingorchestration.events.GlobalModelUpdatedEvent
import tech.medo.modelrepository.registerfederatedmodelartifact.RegisterFederatedModelArtifactCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-model-repository-register-federated-model-artifact")
@Component
class RegisterFederatedModelWhenGlobalModelUpdatedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: GlobalModelUpdatedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(RegisterFederatedModelArtifactCommand(modelId = event.aggregatedModelId, modelName = event.aggregatedModelName, modelPlugin = event.modelPlugin, modelVersion = event.aggregatedModelVersion, modelDescription = event.aggregatedModelDescription, sourceType = event.modelSourceType, modelArtifactUri = event.aggregatedModelArtifactUri, modelRegistryRef = event.aggregatedModelRegistryRef, modelFormat = event.modelFormat, modelArtifactDigest = event.modelArtifactDigest, modelSignatureUri = event.aggregatedModelSignatureUri, modelSizeBytes = event.aggregatedModelSizeBytes, trainingJobId = event.trainingJobId, trainingJobObjective = event.trainingJobObjective, roundId = event.roundId)).resultMessage
}
