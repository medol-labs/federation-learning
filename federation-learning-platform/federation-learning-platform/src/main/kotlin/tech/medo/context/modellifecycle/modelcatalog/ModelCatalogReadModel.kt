package tech.medo.modellifecycle.modelcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.modellifecycle.domain.states.ModelStateEnum;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.StringFilter
import java.time.LocalDateTime


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
    var experimentId: StringFilter? = null
    var hyperparameterSnapshotId: StringFilter? = null
    var reproducibilityManifestId: StringFilter? = null
    var modelCardId: StringFilter? = null
}


class ModelCatalogReadModelProjection : MetadataProjection {
    override var projectionUpdatedAt: LocalDateTime? = null
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
    var experimentId: UUID? = null
    var hyperparameterSnapshotId: UUID? = null
    var reproducibilityManifestId: UUID? = null
    var modelCardId: UUID? = null
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
    experimentId = experimentId,
    hyperparameterSnapshotId = hyperparameterSnapshotId,
    reproducibilityManifestId = reproducibilityManifestId,
    modelCardId = modelCardId,
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
    val experimentId: UUID?,
    val hyperparameterSnapshotId: UUID?,
    val reproducibilityManifestId: UUID?,
    val modelCardId: UUID?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
