package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class PersonalizedModelResultRecordedEvent(
    @EventTag(key = "personalizedModelId")
    val personalizedModelId: UUID,
    val modelUpdateSubmissionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val personalizationMode: String?,
    val personalizationScope: String?,
    val personalizedArtifactPolicy: String?,
    val personalizationMetric: String?,
    val minimumPersonalizationLift: BigDecimal?,
    val personalizationTargetType: String?,
    val personalizationTargetId: UUID?,
    val personalizationCohortId: UUID?,
    val personalizationProfileId: UUID?,
    val personalizedModelArtifactRef: String,
    val personalizedModelArtifactDigest: String,
    val personalizedMetricsArtifactRef: String?,
    val personalizedAccuracy: BigDecimal?,
    val personalizationLift: BigDecimal?
)
