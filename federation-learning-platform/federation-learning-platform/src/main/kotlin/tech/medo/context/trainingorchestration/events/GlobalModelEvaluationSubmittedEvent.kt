package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class GlobalModelEvaluationSubmittedEvent(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    @EventTag(key = "roundId")
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregatedModelId: UUID,
    val modelPlugin: String,
    val aggregatedModelArtifactUri: String,
    val aggregatedModelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val aggregatedModelSignatureUri: String?,
    val globalAccuracy: BigDecimal,
    val globalFairnessScore: BigDecimal?,
    val validationDatasetVersion: String,
    val validationDatasetDigest: String,
    val evaluatedSampleCount: Int,
    val truePositiveCount: Int,
    val trueNegativeCount: Int,
    val falsePositiveCount: Int,
    val falseNegativeCount: Int
)
