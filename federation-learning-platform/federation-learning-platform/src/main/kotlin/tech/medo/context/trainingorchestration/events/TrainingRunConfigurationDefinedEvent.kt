package tech.medo.trainingorchestration.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class TrainingRunConfigurationDefinedEvent(
    @EventTag(key = "trainingRunConfigurationId")
    val trainingRunConfigurationId: UUID,
    val configurationName: String,
    val federationId: UUID,
    val federationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val initialModelId: UUID,
    val initialModelName: String?,
    val initialModelVersion: String?,
    val initialModelArtifactUri: String,
    val initialModelRegistryRef: String,
    val initialModelFormat: String,
    val initialModelArtifactDigest: String,
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
    val minimumAccuracy: BigDecimal,
    val minimumFairnessScore: BigDecimal?
)
