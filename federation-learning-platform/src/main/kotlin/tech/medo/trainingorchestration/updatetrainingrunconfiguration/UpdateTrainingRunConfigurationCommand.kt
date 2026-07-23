package tech.medo.trainingorchestration.updatetrainingrunconfiguration

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class UpdateTrainingRunConfigurationCommand(
    val trainingRunConfigurationId: UUID,
    val federationId: UUID,
    val featureSchemaId: UUID,
    val initialModelVersionId: UUID,
    val strategyName: String,
    val aggregationAlgorithm: String,
    val maxRounds: Int,
    val minimumNodesPerRound: Int,
    val roundTimeoutSeconds: Int,
    val nodeResponseTimeoutSeconds: Int,
    val localEpochs: Int,
    val batchSize: Int,
    val learningRate: BigDecimal,
    val optimizer: String,
    val lossFunction: String,
    val gradientClippingNorm: BigDecimal?,
    val secureAggregationRequired: Boolean,
    val differentialPrivacyEnabled: Boolean,
    val dpNoiseMultiplier: BigDecimal?,
    val dpClipNorm: BigDecimal?,
    val minimumAccuracy: BigDecimal,
    val minimumFairnessScore: BigDecimal?,
    val failureToleranceRatio: BigDecimal,
    val updateReason: String?
) {
    @TargetEntityId
    val selection: TrainingRunConfigurationSelection = TrainingRunConfigurationSelection(trainingRunConfigurationId = trainingRunConfigurationId)

}
