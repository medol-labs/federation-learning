package tech.medo.trainingorchestration.recordpersonalizedmodelresult

import tech.medo.trainingorchestration.events.ModelUpdateSubmissionAcceptedEvent
import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultCommand
import java.util.UUID;
import java.math.BigDecimal;
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.stereotype.Component

@Namespace("automation-training-orchestration-record-personalized-model-result")
@Component
class RecordPersonalizedModelWhenAcceptedUpdateContainsArtifactProcessor(private val commandGateway: CommandGateway) {
    @EventHandler
    fun on(event: ModelUpdateSubmissionAcceptedEvent): java.util.concurrent.CompletableFuture<*> =
        if (event.personalizedArtifactAvailable == true) {
            commandGateway.send(RecordPersonalizedModelResultCommand(personalizedModelId = event.personalizedModelId!!, modelUpdateSubmissionId = event.modelUpdateSubmissionId, trainingJobId = event.trainingJobId, trainingRunConfigurationId = event.trainingRunConfigurationId, roundId = event.roundId, roundNumber = event.roundNumber, runtimeId = event.runtimeId, featureSchemaId = event.featureSchemaId, personalizationMode = event.personalizationMode, personalizationScope = event.personalizationScope, personalizedArtifactPolicy = event.personalizedArtifactPolicy, personalizationMetric = event.personalizationMetric, minimumPersonalizationLift = event.minimumPersonalizationLift, personalizationTargetType = event.personalizationTargetType, personalizationTargetId = event.personalizationTargetId, personalizationCohortId = event.personalizationCohortId, personalizationProfileId = event.personalizationProfileId, personalizedModelArtifactRef = event.personalizedModelArtifactRef!!, personalizedModelArtifactDigest = event.personalizedModelArtifactDigest!!, personalizedModelStorageLocation = event.personalizedArtifactPolicy!!, platformManagedArtifactRef = event.personalizedArtifactPolicy, personalizedMetricsArtifactRef = event.personalizedMetricsArtifactRef, personalizedAccuracy = event.personalizedAccuracy, personalizationLift = event.personalizationLift)).resultMessage
        } else {
            java.util.concurrent.CompletableFuture.completedFuture(null)
        }
}
