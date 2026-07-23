package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingrunconfigurationcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModel
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelProjection
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.toReadModel

@Repository
class JpaTrainingRunConfigurationCatalogReadModelRepository(private val jpaRepository: SpringDataTrainingRunConfigurationCatalogReadModelRepository) : TrainingRunConfigurationCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): TrainingRunConfigurationCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): TrainingRunConfigurationCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: TrainingRunConfigurationCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun TrainingRunConfigurationCatalogReadModelEntity.toProjection(): TrainingRunConfigurationCatalogReadModelProjection =
        TrainingRunConfigurationCatalogReadModelProjection().also {
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.federationId = this@toProjection.federationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.initialModelVersionId = this@toProjection.initialModelVersionId
            it.federationName = this@toProjection.federationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.initialModelArtifactUri = this@toProjection.initialModelArtifactUri
            it.initialModelRepositoryName = this@toProjection.initialModelRepositoryName
            it.initialModelFormat = this@toProjection.initialModelFormat
            it.initialModelHash = this@toProjection.initialModelHash
            it.initialModelSignatureUri = this@toProjection.initialModelSignatureUri
            it.strategyName = this@toProjection.strategyName
            it.aggregationAlgorithm = this@toProjection.aggregationAlgorithm
            it.maxRounds = this@toProjection.maxRounds
            it.minimumNodesPerRound = this@toProjection.minimumNodesPerRound
            it.roundTimeoutSeconds = this@toProjection.roundTimeoutSeconds
            it.nodeResponseTimeoutSeconds = this@toProjection.nodeResponseTimeoutSeconds
            it.localEpochs = this@toProjection.localEpochs
            it.batchSize = this@toProjection.batchSize
            it.learningRate = this@toProjection.learningRate
            it.optimizer = this@toProjection.optimizer
            it.lossFunction = this@toProjection.lossFunction
            it.gradientClippingNorm = this@toProjection.gradientClippingNorm
            it.secureAggregationRequired = this@toProjection.secureAggregationRequired
            it.differentialPrivacyEnabled = this@toProjection.differentialPrivacyEnabled
            it.dpNoiseMultiplier = this@toProjection.dpNoiseMultiplier
            it.dpClipNorm = this@toProjection.dpClipNorm
            it.minimumAccuracy = this@toProjection.minimumAccuracy
            it.minimumFairnessScore = this@toProjection.minimumFairnessScore
            it.failureToleranceRatio = this@toProjection.failureToleranceRatio
            it.updateReason = this@toProjection.updateReason
            it.lockedByTrainingJobId = this@toProjection.lockedByTrainingJobId
            it.state = this@toProjection.state
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun TrainingRunConfigurationCatalogReadModelProjection.toEntity(): TrainingRunConfigurationCatalogReadModelEntity =
        TrainingRunConfigurationCatalogReadModelEntity().also {
            it.trainingRunConfigurationId = this@toEntity.trainingRunConfigurationId
            it.federationId = this@toEntity.federationId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.initialModelVersionId = this@toEntity.initialModelVersionId
            it.federationName = this@toEntity.federationName
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.initialModelArtifactUri = this@toEntity.initialModelArtifactUri
            it.initialModelRepositoryName = this@toEntity.initialModelRepositoryName
            it.initialModelFormat = this@toEntity.initialModelFormat
            it.initialModelHash = this@toEntity.initialModelHash
            it.initialModelSignatureUri = this@toEntity.initialModelSignatureUri
            it.strategyName = this@toEntity.strategyName
            it.aggregationAlgorithm = this@toEntity.aggregationAlgorithm
            it.maxRounds = this@toEntity.maxRounds
            it.minimumNodesPerRound = this@toEntity.minimumNodesPerRound
            it.roundTimeoutSeconds = this@toEntity.roundTimeoutSeconds
            it.nodeResponseTimeoutSeconds = this@toEntity.nodeResponseTimeoutSeconds
            it.localEpochs = this@toEntity.localEpochs
            it.batchSize = this@toEntity.batchSize
            it.learningRate = this@toEntity.learningRate
            it.optimizer = this@toEntity.optimizer
            it.lossFunction = this@toEntity.lossFunction
            it.gradientClippingNorm = this@toEntity.gradientClippingNorm
            it.secureAggregationRequired = this@toEntity.secureAggregationRequired
            it.differentialPrivacyEnabled = this@toEntity.differentialPrivacyEnabled
            it.dpNoiseMultiplier = this@toEntity.dpNoiseMultiplier
            it.dpClipNorm = this@toEntity.dpClipNorm
            it.minimumAccuracy = this@toEntity.minimumAccuracy
            it.minimumFairnessScore = this@toEntity.minimumFairnessScore
            it.failureToleranceRatio = this@toEntity.failureToleranceRatio
            it.updateReason = this@toEntity.updateReason
            it.lockedByTrainingJobId = this@toEntity.lockedByTrainingJobId
            it.state = this@toEntity.state
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
