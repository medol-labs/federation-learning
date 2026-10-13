package tech.medo.modelrepository.registerpersonalizedmodelartifact

import tech.medo.trainingorchestration.events.PersonalizedModelResultRecordedEvent
import tech.medo.modelrepository.registerpersonalizedmodelartifact.RegisterPersonalizedModelArtifactCommand
import java.util.UUID;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-model-repository-register-personalized-model-artifact")
@Component
class RegisterPersonalizedModelWhenResultRecordedProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: PersonalizedModelResultRecordedEvent): java.util.concurrent.CompletableFuture<*> =
        commandGateway.send(RegisterPersonalizedModelArtifactCommand(modelId = event.personalizedModelId, modelPlugin = "" /* TODO: provide modelPlugin */, modelScope = event.personalizationScope!!, modelArtifactUri = event.personalizedModelArtifactRef, modelFormat = "" /* TODO: provide modelFormat */, modelArtifactDigest = event.personalizedModelArtifactDigest, personalizedModelStorageLocation = event.personalizedModelStorageLocation, platformManagedArtifactRef = event.platformManagedArtifactRef, trainingJobId = event.trainingJobId, trainingJobObjective = "" /* TODO: provide trainingJobObjective */, roundId = event.roundId, personalizationMode = event.personalizationMode, personalizationTargetType = event.personalizationTargetType, personalizationTargetId = event.personalizationTargetId, personalizationCohortId = event.personalizationCohortId, baseGlobalModelId = null /* TODO: provide baseGlobalModelId */)).resultMessage
}
