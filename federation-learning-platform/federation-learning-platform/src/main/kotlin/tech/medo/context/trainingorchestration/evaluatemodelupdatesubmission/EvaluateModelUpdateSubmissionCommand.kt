package tech.medo.trainingorchestration.evaluatemodelupdatesubmission

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.traininground.TrainingRoundSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class EvaluateModelUpdateSubmissionCommand(
    val modelUpdateSubmissionId: UUID,
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
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
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val updateProtectionType: String,
    val personalizedArtifactAvailable: Boolean,
    val personalizedModelId: UUID?,
    val personalizedModelArtifactRef: String?,
    val personalizedModelArtifactDigest: String?,
    val personalizedMetricsArtifactRef: String?,
    val personalizedAccuracy: BigDecimal?,
    val personalizationLift: BigDecimal?,
    val anomalyScore: BigDecimal = java.math.BigDecimal.ZERO
) {

    @TargetEntityId
    val selection: TrainingRoundSelection = TrainingRoundSelection(trainingJobId = trainingJobId)



}
