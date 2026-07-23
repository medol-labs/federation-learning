package tech.medo.modelrepository.modelartifactcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class ModelArtifactCatalogReadModelQuery

class ModelArtifactCatalogReadModelProjection : MetadataProjection {
    var modelVersionId: UUID? = null
    var modelArtifactRef: String? = null
    var modelRepositoryRef: String? = null
    var modelFormat: String? = null
    var modelHash: String? = null
    var modelSignatureRef: String? = null
    var modelSizeBytes: Int? = null
    var sourceType: String? = null
    var trainingJobId: UUID? = null
    var roundId: UUID? = null
    var trainingJobObjective: String? = null
    var state: ModelArtifactStateEnum? = null
    var registeredAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun ModelArtifactCatalogReadModelProjection.toReadModel(): ModelArtifactCatalogReadModel =
    ModelArtifactCatalogReadModel(
    modelVersionId = modelVersionId,
    modelArtifactRef = modelArtifactRef,
    modelRepositoryRef = modelRepositoryRef,
    modelFormat = modelFormat,
    modelHash = modelHash,
    modelSignatureRef = modelSignatureRef,
    modelSizeBytes = modelSizeBytes,
    sourceType = sourceType,
    trainingJobId = trainingJobId,
    roundId = roundId,
    trainingJobObjective = trainingJobObjective,
    state = state,
    registeredAt = registeredAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface ModelArtifactCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<ModelArtifactCatalogReadModel>
    fun findById(id: UUID): ModelArtifactCatalogReadModel?
    fun findProjectionById(id: UUID): ModelArtifactCatalogReadModelProjection?
    fun save(projection: ModelArtifactCatalogReadModelProjection)
}

data class ModelArtifactCatalogReadModel(
    val modelVersionId: UUID?,
    val modelArtifactRef: String?,
    val modelRepositoryRef: String?,
    val modelFormat: String?,
    val modelHash: String?,
    val modelSignatureRef: String?,
    val modelSizeBytes: Int?,
    val sourceType: String?,
    val trainingJobId: UUID?,
    val roundId: UUID?,
    val trainingJobObjective: String?,
    val state: ModelArtifactStateEnum?,
    val registeredAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
