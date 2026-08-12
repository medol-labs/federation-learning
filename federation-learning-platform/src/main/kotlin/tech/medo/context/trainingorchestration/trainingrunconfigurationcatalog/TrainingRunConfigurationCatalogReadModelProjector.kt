package tech.medo.trainingorchestration.trainingrunconfigurationcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.federationmanagement.events.FederationCreatedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.events.TrainingRunConfigurationUpdatedEvent
import tech.medo.trainingorchestration.events.TrainingRunConfigurationLockedEvent
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum


@Component
class TrainingRunConfigurationCatalogReadModelProjector(private val repository: TrainingRunConfigurationCatalogReadModelRepository) {
    private val log = LoggerFactory.getLogger(TrainingRunConfigurationCatalogReadModelProjector::class.java)

    @EventHandler
    fun on(event: FederationCreatedEvent) {
        // Skipped: FederationCreatedEvent does not provide enough key fields to locate TrainingRunConfigurationCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: FeatureSchemaDefinedEvent) {
        // Skipped: FeatureSchemaDefinedEvent does not provide enough key fields to locate TrainingRunConfigurationCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: TrainingRunConfigurationDefinedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingRunConfigurationId) ?: TrainingRunConfigurationCatalogReadModelProjection().apply {
                this.trainingRunConfigurationId = event.trainingRunConfigurationId
        }
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.federationId = event.federationId
            entity.featureSchemaId = event.featureSchemaId
            entity.initialModelVersionId = event.initialModelVersionId
            entity.initialModelArtifactUri = event.initialModelArtifactUri
            entity.initialModelRepositoryName = event.initialModelRepositoryName
            entity.initialModelFormat = event.initialModelFormat
            entity.initialModelHash = event.initialModelHash
            entity.initialModelSignatureUri = event.initialModelSignatureUri
            entity.strategyName = event.strategyName
            entity.aggregationAlgorithm = event.aggregationAlgorithm
            entity.maxRounds = event.maxRounds
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.roundTimeoutSeconds = event.roundTimeoutSeconds
            entity.nodeResponseTimeoutSeconds = event.nodeResponseTimeoutSeconds
            entity.localEpochs = event.localEpochs
            entity.batchSize = event.batchSize
            entity.learningRate = event.learningRate
            entity.optimizer = event.optimizer
            entity.lossFunction = event.lossFunction
            entity.gradientClippingNorm = event.gradientClippingNorm
            entity.secureAggregationRequired = event.secureAggregationRequired
            entity.differentialPrivacyEnabled = event.differentialPrivacyEnabled
            entity.dpNoiseMultiplier = event.dpNoiseMultiplier
            entity.dpClipNorm = event.dpClipNorm
            entity.minimumAccuracy = event.minimumAccuracy
            entity.minimumFairnessScore = event.minimumFairnessScore
            entity.failureToleranceRatio = event.failureToleranceRatio
            entity.state = TrainingRunConfigurationStateEnum.DRAFT
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRunConfigurationUpdatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingRunConfigurationId) ?: TrainingRunConfigurationCatalogReadModelProjection().apply {
                this.trainingRunConfigurationId = event.trainingRunConfigurationId
        }
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.federationId = event.federationId
            entity.featureSchemaId = event.featureSchemaId
            entity.initialModelVersionId = event.initialModelVersionId
            entity.initialModelArtifactUri = event.initialModelArtifactUri
            entity.initialModelRepositoryName = event.initialModelRepositoryName
            entity.initialModelFormat = event.initialModelFormat
            entity.initialModelHash = event.initialModelHash
            entity.initialModelSignatureUri = event.initialModelSignatureUri
            entity.strategyName = event.strategyName
            entity.aggregationAlgorithm = event.aggregationAlgorithm
            entity.maxRounds = event.maxRounds
            entity.minimumNodesPerRound = event.minimumNodesPerRound
            entity.roundTimeoutSeconds = event.roundTimeoutSeconds
            entity.nodeResponseTimeoutSeconds = event.nodeResponseTimeoutSeconds
            entity.localEpochs = event.localEpochs
            entity.batchSize = event.batchSize
            entity.learningRate = event.learningRate
            entity.optimizer = event.optimizer
            entity.lossFunction = event.lossFunction
            entity.gradientClippingNorm = event.gradientClippingNorm
            entity.secureAggregationRequired = event.secureAggregationRequired
            entity.differentialPrivacyEnabled = event.differentialPrivacyEnabled
            entity.dpNoiseMultiplier = event.dpNoiseMultiplier
            entity.dpClipNorm = event.dpClipNorm
            entity.minimumAccuracy = event.minimumAccuracy
            entity.minimumFairnessScore = event.minimumFairnessScore
            entity.failureToleranceRatio = event.failureToleranceRatio
            entity.updateReason = event.updateReason
            entity.state = TrainingRunConfigurationStateEnum.DRAFT
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRunConfigurationLockedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingRunConfigurationId)
        if (entity == null) {
            log.warn(
                "Skip projecting training run configuration lock because configuration projection is missing. trainingRunConfigurationId={}, trainingJobId={}",
                event.trainingRunConfigurationId,
                event.trainingJobId
            )
            return
        }
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.state = TrainingRunConfigurationStateEnum.LOCKED
            entity.lockedByTrainingJobId = event.trainingJobId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
