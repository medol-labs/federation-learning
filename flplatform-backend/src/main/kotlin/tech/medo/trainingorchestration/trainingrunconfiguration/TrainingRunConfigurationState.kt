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
    private var trainingRunConfigurationId: UUID? = null
    private var federationId: UUID? = null
    private var featureSchemaId: UUID? = null
    private var initialModelVersionId: UUID? = null
    private var initialModelArtifactUri: String? = null
    private var initialModelRepositoryName: String? = null
    private var initialModelFormat: String? = null
    private var initialModelHash: String? = null
    private var initialModelSignatureUri: String? = null
    private var strategyName: String? = null
    private var aggregationAlgorithm: String? = null
    private var maxRounds: Int? = null
    private var minimumNodesPerRound: Int? = null
    private var roundTimeoutSeconds: Int? = null
    private var nodeResponseTimeoutSeconds: Int? = null
    private var localEpochs: Int? = null
    private var batchSize: Int? = null
    private var learningRate: BigDecimal? = null
    private var optimizer: String? = null
    private var lossFunction: String? = null
    private var gradientClippingNorm: BigDecimal? = null
    private var secureAggregationRequired: Boolean? = null
    private var differentialPrivacyEnabled: Boolean? = null
    private var dpNoiseMultiplier: BigDecimal? = null
    private var dpClipNorm: BigDecimal? = null
    private var minimumAccuracy: BigDecimal? = null
    private var minimumFairnessScore: BigDecimal? = null
    private var failureToleranceRatio: BigDecimal? = null
    private var updateReason: String? = null
    private var trainingJobId: UUID? = null

    @EventSourcingHandler
    fun evolve(event: TrainingRunConfigurationDefinedEvent): TrainingRunConfigurationState = apply {
        currentState = TrainingRunConfigurationStateEnum.DRAFT
        trainingRunConfigurationId = event.trainingRunConfigurationId
        federationId = event.federationId
        featureSchemaId = event.featureSchemaId
        initialModelVersionId = event.initialModelVersionId
        initialModelArtifactUri = event.initialModelArtifactUri
        initialModelRepositoryName = event.initialModelRepositoryName
        initialModelFormat = event.initialModelFormat
        initialModelHash = event.initialModelHash
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
        differentialPrivacyEnabled = event.differentialPrivacyEnabled
        dpNoiseMultiplier = event.dpNoiseMultiplier
        dpClipNorm = event.dpClipNorm
        minimumAccuracy = event.minimumAccuracy
        minimumFairnessScore = event.minimumFairnessScore
        failureToleranceRatio = event.failureToleranceRatio
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRunConfigurationUpdatedEvent): TrainingRunConfigurationState = apply {
        currentState = TrainingRunConfigurationStateEnum.DRAFT
        trainingRunConfigurationId = event.trainingRunConfigurationId
        federationId = event.federationId
        featureSchemaId = event.featureSchemaId
        initialModelVersionId = event.initialModelVersionId
        initialModelArtifactUri = event.initialModelArtifactUri
        initialModelRepositoryName = event.initialModelRepositoryName
        initialModelFormat = event.initialModelFormat
        initialModelHash = event.initialModelHash
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
        differentialPrivacyEnabled = event.differentialPrivacyEnabled
        dpNoiseMultiplier = event.dpNoiseMultiplier
        dpClipNorm = event.dpClipNorm
        minimumAccuracy = event.minimumAccuracy
        minimumFairnessScore = event.minimumFairnessScore
        failureToleranceRatio = event.failureToleranceRatio
        updateReason = event.updateReason
    }

    @EventSourcingHandler
    fun evolve(event: TrainingRunConfigurationLockedEvent): TrainingRunConfigurationState = apply {
        currentState = TrainingRunConfigurationStateEnum.LOCKED
        trainingRunConfigurationId = event.trainingRunConfigurationId
        trainingJobId = event.trainingJobId
    }
}
