package tech.medo.trainingorchestration.trainingrunconfiguration

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.events.TrainingRunConfigurationUpdatedEvent
import tech.medo.trainingorchestration.events.TrainingRunConfigurationLockedEvent
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = TrainingRunConfigurationTags.TRAINING_RUN_CONFIGURATION_ID)
class TrainingRunConfigurationState @EntityCreator constructor() {

    var currentState: TrainingRunConfigurationStateEnum? = null
    var trainingRunConfigurationId: UUID? = null
    var configurationName: String? = null
    var federationId: UUID? = null
    var federationName: String? = null
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var initialModelId: UUID? = null
    var initialModelName: String? = null
    var initialModelVersion: String? = null
    var initialModelArtifactUri: String? = null
    var initialModelRegistryRef: String? = null
    var initialModelFormat: String? = null
    var initialModelArtifactDigest: String? = null
    var initialModelSignatureUri: String? = null
    var strategyName: String? = null
    var aggregationAlgorithm: String? = null
    var maxRounds: Int? = null
    var minimumNodesPerRound: Int? = null
    var roundTimeoutSeconds: Int? = null
    var nodeResponseTimeoutSeconds: Int? = null
    var localEpochs: Int? = null
    var batchSize: Int? = null
    var learningRate: BigDecimal? = null
    var optimizer: String? = null
    var lossFunction: String? = null
    var gradientClippingNorm: BigDecimal? = null
    var secureAggregationRequired: Boolean? = null
    var minimumAccuracy: BigDecimal? = null
    var minimumFairnessScore: BigDecimal? = null
    var updateReason: String? = null
    var trainingJobId: UUID? = null

    @EventSourcingHandler
    fun evolve(event: TrainingRunConfigurationDefinedEvent): TrainingRunConfigurationState = apply {
        currentState = TrainingRunConfigurationStateEnum.DRAFT
        trainingRunConfigurationId = event.trainingRunConfigurationId
        configurationName = event.configurationName
        federationId = event.federationId
        federationName = event.federationName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        initialModelId = event.initialModelId
        initialModelName = event.initialModelName
        initialModelVersion = event.initialModelVersion
        initialModelArtifactUri = event.initialModelArtifactUri
        initialModelRegistryRef = event.initialModelRegistryRef
        initialModelFormat = event.initialModelFormat
        initialModelArtifactDigest = event.initialModelArtifactDigest
        initialModelSignatureUri = event.initialModelSignatureUri
        strategyName = event.strategyName
        aggregationAlgorithm = event.aggregationAlgorithm
        maxRounds = event.maxRounds
        minimumNodesPerRound = event.minimumNodesPerRound
        roundTimeoutSeconds = event.roundTimeoutSeconds
        nodeResponseTimeoutSeconds = event.nodeResponseTimeoutSeconds
        localEpochs = event.localEpochs
        batchSize = event.batchSize
        learningRate = event.learningRate
        optimizer = event.optimizer
        lossFunction = event.lossFunction
        gradientClippingNorm = event.gradientClippingNorm
        secureAggregationRequired = event.secureAggregationRequired
        minimumAccuracy = event.minimumAccuracy
        minimumFairnessScore = event.minimumFairnessScore
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRunConfigurationUpdatedEvent): TrainingRunConfigurationState = apply {
        trainingRunConfigurationId = event.trainingRunConfigurationId
        configurationName = event.configurationName
        federationId = event.federationId
        federationName = event.federationName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        initialModelId = event.initialModelId
        initialModelName = event.initialModelName
        initialModelVersion = event.initialModelVersion
        initialModelArtifactUri = event.initialModelArtifactUri
        initialModelRegistryRef = event.initialModelRegistryRef
        initialModelFormat = event.initialModelFormat
        initialModelArtifactDigest = event.initialModelArtifactDigest
        initialModelSignatureUri = event.initialModelSignatureUri
        strategyName = event.strategyName
        aggregationAlgorithm = event.aggregationAlgorithm
        maxRounds = event.maxRounds
        minimumNodesPerRound = event.minimumNodesPerRound
        roundTimeoutSeconds = event.roundTimeoutSeconds
        nodeResponseTimeoutSeconds = event.nodeResponseTimeoutSeconds
        localEpochs = event.localEpochs
        batchSize = event.batchSize
        learningRate = event.learningRate
        optimizer = event.optimizer
        lossFunction = event.lossFunction
        gradientClippingNorm = event.gradientClippingNorm
        secureAggregationRequired = event.secureAggregationRequired
        minimumAccuracy = event.minimumAccuracy
        minimumFairnessScore = event.minimumFairnessScore
        updateReason = event.updateReason
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRunConfigurationLockedEvent): TrainingRunConfigurationState = apply {
        currentState = TrainingRunConfigurationStateEnum.LOCKED
        trainingRunConfigurationId = event.trainingRunConfigurationId
        trainingJobId = event.trainingJobId
    }
}
