package tech.medo.modellifecycle.modelversioncatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum;


class ModelVersionCatalogReadModelQuery

class ModelVersionCatalogReadModelProjection : MetadataProjection {
    var modelVersionId: UUID? = null
    var trainingJobId: UUID? = null
    var finalRoundId: UUID? = null
    var modelArtifactId: UUID? = null
    var trainingJobObjective: String? = null
    var modelHash: String? = null
    var evaluationReportId: UUID? = null
    var finalGlobalAccuracy: BigDecimal? = null
    var state: ModelVersionStateEnum? = null
    var releaseChannel: String? = null
    var productionStage: String? = null
    var previousModelVersionId: UUID? = null
    var experimentId: UUID? = null
    var hyperparameterSnapshotId: UUID? = null
    var reproducibilityManifestId: UUID? = null
    var modelCardId: UUID? = null
    var baselineModelVersionId: UUID? = null
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

fun ModelVersionCatalogReadModelProjection.toReadModel(): ModelVersionCatalogReadModel =
    ModelVersionCatalogReadModel(
    modelVersionId = modelVersionId,
    trainingJobId = trainingJobId,
    finalRoundId = finalRoundId,
    modelArtifactId = modelArtifactId,
    trainingJobObjective = trainingJobObjective,
    modelHash = modelHash,
    evaluationReportId = evaluationReportId,
    finalGlobalAccuracy = finalGlobalAccuracy,
    state = state,
    releaseChannel = releaseChannel,
    productionStage = productionStage,
    previousModelVersionId = previousModelVersionId,
    experimentId = experimentId,
    hyperparameterSnapshotId = hyperparameterSnapshotId,
    reproducibilityManifestId = reproducibilityManifestId,
    modelCardId = modelCardId,
    baselineModelVersionId = baselineModelVersionId,
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

interface ModelVersionCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<ModelVersionCatalogReadModel>
    fun findById(id: UUID): ModelVersionCatalogReadModel?
    fun findProjectionById(id: UUID): ModelVersionCatalogReadModelProjection?
    fun save(projection: ModelVersionCatalogReadModelProjection)
}

data class ModelVersionCatalogReadModel(
    val modelVersionId: UUID?,
    val trainingJobId: UUID?,
    val finalRoundId: UUID?,
    val modelArtifactId: UUID?,
    val trainingJobObjective: String?,
    val modelHash: String?,
    val evaluationReportId: UUID?,
    val finalGlobalAccuracy: BigDecimal?,
    val state: ModelVersionStateEnum?,
    val releaseChannel: String?,
    val productionStage: String?,
    val previousModelVersionId: UUID?,
    val experimentId: UUID?,
    val hyperparameterSnapshotId: UUID?,
    val reproducibilityManifestId: UUID?,
    val modelCardId: UUID?,
    val baselineModelVersionId: UUID?,
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
