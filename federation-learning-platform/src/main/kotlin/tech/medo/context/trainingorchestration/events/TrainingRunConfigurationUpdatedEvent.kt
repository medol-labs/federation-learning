package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class TrainingRunConfigurationUpdatedEvent(
    @EventTag(key = "trainingRunConfigurationId")
    val trainingRunConfigurationId: UUID,
    val federationId: UUID,
    val featureSchemaId: UUID,
    val initialModelVersionId: UUID,
    val initialModelArtifactUri: String,
    val initialModelRepositoryName: String,
    val initialModelFormat: String,
    val initialModelHash: String,
    val initialModelSignatureUri: String?,
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
)
