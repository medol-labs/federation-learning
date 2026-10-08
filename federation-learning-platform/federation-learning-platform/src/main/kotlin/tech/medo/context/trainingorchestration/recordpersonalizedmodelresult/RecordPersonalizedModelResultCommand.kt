package tech.medo.trainingorchestration.recordpersonalizedmodelresult

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.personalizedmodelresult.PersonalizedModelResultSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class RecordPersonalizedModelResultCommand(
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
) {
    @TargetEntityId
    val selection: PersonalizedModelResultSelection = PersonalizedModelResultSelection(personalizedModelId = personalizedModelId)


}
