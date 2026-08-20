package tech.medo.trainingorchestration.trainingrunconfigurationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.StringFilter


class TrainingRunConfigurationCatalogReadModelQuery

class TrainingRunConfigurationCatalogReadModelCriteria {
    var trainingRunConfigurationId: StringFilter? = null
    var federationId: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var initialModelId: StringFilter? = null
    var initialModelName: StringFilter? = null
    var initialModelVersion: StringFilter? = null
    var federationName: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var initialModelArtifactUri: StringFilter? = null
    var initialModelRegistryRef: StringFilter? = null
    var initialModelFormat: StringFilter? = null
    var initialModelArtifactDigest: StringFilter? = null
    var initialModelSignatureUri: StringFilter? = null
    var strategyName: StringFilter? = null
    var aggregationAlgorithm: StringFilter? = null
    var maxRounds: IntegerFilter? = null
    var minimumNodesPerRound: IntegerFilter? = null
    var roundTimeoutSeconds: IntegerFilter? = null
    var nodeResponseTimeoutSeconds: IntegerFilter? = null
    var localEpochs: IntegerFilter? = null
    var batchSize: IntegerFilter? = null
    var learningRate: BigDecimalFilter? = null
    var optimizer: StringFilter? = null
    var lossFunction: StringFilter? = null
    var gradientClippingNorm: BigDecimalFilter? = null
    var secureAggregationRequired: BooleanFilter? = null
    var differentialPrivacyEnabled: BooleanFilter? = null
    var dpNoiseMultiplier: BigDecimalFilter? = null
    var dpClipNorm: BigDecimalFilter? = null
    var minimumAccuracy: BigDecimalFilter? = null
    var minimumFairnessScore: BigDecimalFilter? = null
    var failureToleranceRatio: BigDecimalFilter? = null
    var updateReason: StringFilter? = null
    var lockedByTrainingJobId: StringFilter? = null
    var state: Filter<TrainingRunConfigurationStateEnum>? = null
}


class TrainingRunConfigurationCatalogReadModelProjection : MetadataProjection {
    var trainingRunConfigurationId: UUID? = null
    var federationId: UUID? = null
    var featureSchemaId: UUID? = null
    var initialModelId: UUID? = null
    var initialModelName: String? = null
    var initialModelVersion: String? = null
    var federationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
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
    var differentialPrivacyEnabled: Boolean? = null
    var dpNoiseMultiplier: BigDecimal? = null
    var dpClipNorm: BigDecimal? = null
    var minimumAccuracy: BigDecimal? = null
    var minimumFairnessScore: BigDecimal? = null
    var failureToleranceRatio: BigDecimal? = null
    var updateReason: String? = null
    var lockedByTrainingJobId: UUID? = null
    var state: TrainingRunConfigurationStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun TrainingRunConfigurationCatalogReadModelProjection.toReadModel(): TrainingRunConfigurationCatalogReadModel =
    TrainingRunConfigurationCatalogReadModel(
    trainingRunConfigurationId = trainingRunConfigurationId,
    federationId = federationId,
    featureSchemaId = featureSchemaId,
    initialModelId = initialModelId,
    initialModelName = initialModelName,
    initialModelVersion = initialModelVersion,
    federationName = federationName,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    initialModelArtifactUri = initialModelArtifactUri,
    initialModelRegistryRef = initialModelRegistryRef,
    initialModelFormat = initialModelFormat,
    initialModelArtifactDigest = initialModelArtifactDigest,
    initialModelSignatureUri = initialModelSignatureUri,
    strategyName = strategyName,
    aggregationAlgorithm = aggregationAlgorithm,
    maxRounds = maxRounds,
    minimumNodesPerRound = minimumNodesPerRound,
    roundTimeoutSeconds = roundTimeoutSeconds,
    nodeResponseTimeoutSeconds = nodeResponseTimeoutSeconds,
    localEpochs = localEpochs,
    batchSize = batchSize,
    learningRate = learningRate,
    optimizer = optimizer,
    lossFunction = lossFunction,
    gradientClippingNorm = gradientClippingNorm,
    secureAggregationRequired = secureAggregationRequired,
    differentialPrivacyEnabled = differentialPrivacyEnabled,
    dpNoiseMultiplier = dpNoiseMultiplier,
    dpClipNorm = dpClipNorm,
    minimumAccuracy = minimumAccuracy,
    minimumFairnessScore = minimumFairnessScore,
    failureToleranceRatio = failureToleranceRatio,
    updateReason = updateReason,
    lockedByTrainingJobId = lockedByTrainingJobId,
    state = state,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface TrainingRunConfigurationCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel>
    fun findAllByCriteria(criteria: TrainingRunConfigurationCatalogReadModelCriteria?, pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel>
    fun findById(id: UUID): TrainingRunConfigurationCatalogReadModel?
    fun findProjectionById(id: UUID): TrainingRunConfigurationCatalogReadModelProjection?
    fun save(projection: TrainingRunConfigurationCatalogReadModelProjection)
}

data class TrainingRunConfigurationCatalogReadModel(
    val trainingRunConfigurationId: UUID?,
    val federationId: UUID?,
    val featureSchemaId: UUID?,
    val initialModelId: UUID?,
    val initialModelName: String?,
    val initialModelVersion: String?,
    val federationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val initialModelArtifactUri: String?,
    val initialModelRegistryRef: String?,
    val initialModelFormat: String?,
    val initialModelArtifactDigest: String?,
    val initialModelSignatureUri: String?,
    val strategyName: String?,
    val aggregationAlgorithm: String?,
    val maxRounds: Int?,
    val minimumNodesPerRound: Int?,
    val roundTimeoutSeconds: Int?,
    val nodeResponseTimeoutSeconds: Int?,
    val localEpochs: Int?,
    val batchSize: Int?,
    val learningRate: BigDecimal?,
    val optimizer: String?,
    val lossFunction: String?,
    val gradientClippingNorm: BigDecimal?,
    val secureAggregationRequired: Boolean?,
    val differentialPrivacyEnabled: Boolean?,
    val dpNoiseMultiplier: BigDecimal?,
    val dpClipNorm: BigDecimal?,
    val minimumAccuracy: BigDecimal?,
    val minimumFairnessScore: BigDecimal?,
    val failureToleranceRatio: BigDecimal?,
    val updateReason: String?,
    val lockedByTrainingJobId: UUID?,
    val state: TrainingRunConfigurationStateEnum?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
