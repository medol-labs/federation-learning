package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingrunconfigurationcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum;

import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModel
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelCriteria
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelProjection
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.toReadModel

@Repository
class JpaTrainingRunConfigurationCatalogReadModelRepository(
    private val jpaRepository: SpringDataTrainingRunConfigurationCatalogReadModelRepository,
    private val queryService: TrainingRunConfigurationCatalogReadModelQueryService
) : TrainingRunConfigurationCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: TrainingRunConfigurationCatalogReadModelCriteria?, pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

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
            it.initialModelId = this@toProjection.initialModelId
            it.initialModelName = this@toProjection.initialModelName
            it.initialModelVersion = this@toProjection.initialModelVersion
            it.federationName = this@toProjection.federationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.initialModelArtifactUri = this@toProjection.initialModelArtifactUri
            it.initialModelRegistryRef = this@toProjection.initialModelRegistryRef
            it.initialModelFormat = this@toProjection.initialModelFormat
            it.initialModelArtifactDigest = this@toProjection.initialModelArtifactDigest
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
            it.minimumAccuracy = this@toProjection.minimumAccuracy
            it.minimumFairnessScore = this@toProjection.minimumFairnessScore
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
            it.initialModelId = this@toEntity.initialModelId
            it.initialModelName = this@toEntity.initialModelName
            it.initialModelVersion = this@toEntity.initialModelVersion
            it.federationName = this@toEntity.federationName
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.initialModelArtifactUri = this@toEntity.initialModelArtifactUri
            it.initialModelRegistryRef = this@toEntity.initialModelRegistryRef
            it.initialModelFormat = this@toEntity.initialModelFormat
            it.initialModelArtifactDigest = this@toEntity.initialModelArtifactDigest
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
            it.minimumAccuracy = this@toEntity.minimumAccuracy
            it.minimumFairnessScore = this@toEntity.minimumFairnessScore
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
