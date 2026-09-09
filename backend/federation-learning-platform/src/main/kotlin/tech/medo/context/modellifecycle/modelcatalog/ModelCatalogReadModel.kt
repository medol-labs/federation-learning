package tech.medo.modellifecycle.modelcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.modellifecycle.domain.states.ModelStateEnum;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.StringFilter


class ModelCatalogReadModelQuery

class ModelCatalogReadModelCriteria {
    var modelId: StringFilter? = null
    var trainingJobId: StringFilter? = null
    var finalRoundId: StringFilter? = null
    var modelArtifactId: StringFilter? = null
    var trainingJobObjective: StringFilter? = null
    var modelArtifactDigest: StringFilter? = null
    var evaluationReportId: StringFilter? = null
    var finalGlobalAccuracy: BigDecimalFilter? = null
    var state: Filter<ModelStateEnum>? = null
    var releaseChannel: StringFilter? = null
    var productionStage: StringFilter? = null
    var previousModelId: StringFilter? = null
    var experimentId: StringFilter? = null
    var hyperparameterSnapshotId: StringFilter? = null
    var reproducibilityManifestId: StringFilter? = null
    var modelCardId: StringFilter? = null
    var baselineModelId: StringFilter? = null
    var hasEvaluationPackage: BooleanFilter? = null
    var approvalStatus: StringFilter? = null
    var releaseStatus: StringFilter? = null
    var isProduction: BooleanFilter? = null
    var canRecordEvaluationPackage: BooleanFilter? = null
    var canApprove: BooleanFilter? = null
    var canPromoteToProduction: BooleanFilter? = null
    var canRollback: BooleanFilter? = null
    var canRetire: BooleanFilter? = null
    var blockedReason: StringFilter? = null
}


class ModelCatalogReadModelProjection : MetadataProjection {
    var modelId: UUID? = null
    var trainingJobId: UUID? = null
    var finalRoundId: UUID? = null
    var modelArtifactId: UUID? = null
    var trainingJobObjective: String? = null
    var modelArtifactDigest: String? = null
    var evaluationReportId: UUID? = null
    var finalGlobalAccuracy: BigDecimal? = null
    var state: ModelStateEnum? = null
    var releaseChannel: String? = null
    var productionStage: String? = null
    var previousModelId: UUID? = null
    var experimentId: UUID? = null
    var hyperparameterSnapshotId: UUID? = null
    var reproducibilityManifestId: UUID? = null
    var modelCardId: UUID? = null
    var baselineModelId: UUID? = null
    var hasEvaluationPackage: Boolean? = null
    var approvalStatus: String? = null
    var releaseStatus: String? = null
    var isProduction: Boolean? = null
    var canRecordEvaluationPackage: Boolean? = null
    var canApprove: Boolean? = null
    var canPromoteToProduction: Boolean? = null
    var canRollback: Boolean? = null
    var canRetire: Boolean? = null
    var blockedReason: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun ModelCatalogReadModelProjection.toReadModel(): ModelCatalogReadModel =
    ModelCatalogReadModel(
    modelId = modelId,
    trainingJobId = trainingJobId,
    finalRoundId = finalRoundId,
    modelArtifactId = modelArtifactId,
    trainingJobObjective = trainingJobObjective,
    modelArtifactDigest = modelArtifactDigest,
    evaluationReportId = evaluationReportId,
    finalGlobalAccuracy = finalGlobalAccuracy,
    state = state,
    releaseChannel = releaseChannel,
    productionStage = productionStage,
    previousModelId = previousModelId,
    experimentId = experimentId,
    hyperparameterSnapshotId = hyperparameterSnapshotId,
    reproducibilityManifestId = reproducibilityManifestId,
    modelCardId = modelCardId,
    baselineModelId = baselineModelId,
    hasEvaluationPackage = hasEvaluationPackage,
    approvalStatus = approvalStatus,
    releaseStatus = releaseStatus,
    isProduction = isProduction,
    canRecordEvaluationPackage = canRecordEvaluationPackage,
    canApprove = canApprove,
    canPromoteToProduction = canPromoteToProduction,
    canRollback = canRollback,
    canRetire = canRetire,
    blockedReason = blockedReason,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface ModelCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<ModelCatalogReadModel>
    fun findAllByCriteria(criteria: ModelCatalogReadModelCriteria?, pageable: Pageable): Page<ModelCatalogReadModel>
    fun findById(id: UUID): ModelCatalogReadModel?
    fun findProjectionById(id: UUID): ModelCatalogReadModelProjection?
    fun save(projection: ModelCatalogReadModelProjection)
}

data class ModelCatalogReadModel(
    val modelId: UUID?,
    val trainingJobId: UUID?,
    val finalRoundId: UUID?,
    val modelArtifactId: UUID?,
    val trainingJobObjective: String?,
    val modelArtifactDigest: String?,
    val evaluationReportId: UUID?,
    val finalGlobalAccuracy: BigDecimal?,
    val state: ModelStateEnum?,
    val releaseChannel: String?,
    val productionStage: String?,
    val previousModelId: UUID?,
    val experimentId: UUID?,
    val hyperparameterSnapshotId: UUID?,
    val reproducibilityManifestId: UUID?,
    val modelCardId: UUID?,
    val baselineModelId: UUID?,
    val hasEvaluationPackage: Boolean?,
    val approvalStatus: String?,
    val releaseStatus: String?,
    val isProduction: Boolean?,
    val canRecordEvaluationPackage: Boolean?,
    val canApprove: Boolean?,
    val canPromoteToProduction: Boolean?,
    val canRollback: Boolean?,
    val canRetire: Boolean?,
    val blockedReason: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
