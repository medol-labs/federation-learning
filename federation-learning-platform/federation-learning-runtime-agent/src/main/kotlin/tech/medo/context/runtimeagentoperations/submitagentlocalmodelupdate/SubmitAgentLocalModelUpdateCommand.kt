package tech.medo.runtimeagentoperations.submitagentlocalmodelupdate

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class SubmitAgentLocalModelUpdateCommand(
    val modelUpdateSubmissionId: UUID = java.util.UUID.randomUUID(),
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val roundExecutionId: UUID,
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
    val runtimeEngineJobId: String,
    val localModelId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val updateProtectionType: String,
    val trainingLoss: BigDecimal,
    val personalizedArtifactAvailable: Boolean,
    val personalizedModelId: UUID?,
    val personalizedModelArtifactRef: String?,
    val personalizedModelArtifactDigest: String?,
    val personalizedMetricsArtifactRef: String?,
    val personalizedAccuracy: BigDecimal?,
    val personalizationLift: BigDecimal?
) {

    @TargetEntityId
    val selection: RoundExecutionSelection = RoundExecutionSelection(executionPlanId = executionPlanId)



}
