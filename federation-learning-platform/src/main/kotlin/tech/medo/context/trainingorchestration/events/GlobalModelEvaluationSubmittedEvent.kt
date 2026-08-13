package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class GlobalModelEvaluationSubmittedEvent(
    @EventTag(key = "trainingJobId")
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val aggregatedModelId: UUID,
    val aggregatedModelArtifactUri: String,
    val aggregatedModelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val aggregatedModelSignatureUri: String?,
    val globalAccuracy: BigDecimal,
    val globalFairnessScore: BigDecimal
)
