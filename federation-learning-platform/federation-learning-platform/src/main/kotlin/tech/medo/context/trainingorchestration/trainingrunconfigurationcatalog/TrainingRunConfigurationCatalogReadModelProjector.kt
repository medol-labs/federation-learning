package tech.medo.trainingorchestration.trainingrunconfigurationcatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
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
            entity.configurationName = event.configurationName
            entity.federationId = event.federationId
            entity.featureSchemaId = event.featureSchemaId
            entity.initialModelId = event.initialModelId
            entity.initialModelName = event.initialModelName
            entity.initialModelVersion = event.initialModelVersion
            entity.federationName = event.federationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.initialModelArtifactUri = event.initialModelArtifactUri
            entity.initialModelRegistryRef = event.initialModelRegistryRef
            entity.initialModelFormat = event.initialModelFormat
            entity.initialModelArtifactDigest = event.initialModelArtifactDigest
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
            entity.minimumAccuracy = event.minimumAccuracy
            entity.minimumFairnessScore = event.minimumFairnessScore
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
            entity.configurationName = event.configurationName
            entity.federationId = event.federationId
            entity.featureSchemaId = event.featureSchemaId
            entity.initialModelId = event.initialModelId
            entity.initialModelName = event.initialModelName
            entity.initialModelVersion = event.initialModelVersion
            entity.federationName = event.federationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.initialModelArtifactUri = event.initialModelArtifactUri
            entity.initialModelRegistryRef = event.initialModelRegistryRef
            entity.initialModelFormat = event.initialModelFormat
            entity.initialModelArtifactDigest = event.initialModelArtifactDigest
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
            entity.minimumAccuracy = event.minimumAccuracy
            entity.minimumFairnessScore = event.minimumFairnessScore
            entity.updateReason = event.updateReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: TrainingRunConfigurationLockedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.trainingRunConfigurationId) ?: TrainingRunConfigurationCatalogReadModelProjection().apply {
                this.trainingRunConfigurationId = event.trainingRunConfigurationId
        }
            entity.trainingRunConfigurationId = event.trainingRunConfigurationId
            entity.state = TrainingRunConfigurationStateEnum.LOCKED
            entity.lockedByTrainingJobId = event.trainingJobId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
