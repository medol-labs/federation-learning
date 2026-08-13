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
    var modelId: UUID? = null
    var modelName: String? = null
    var modelVersion: String? = null
    var sourceType: String? = null
    var modelArtifactUri: String? = null
    var modelRegistryRef: String? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    var modelSignatureUri: String? = null
    var modelSizeBytes: Int? = null
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
    modelId = modelId,
    modelName = modelName,
    modelVersion = modelVersion,
    sourceType = sourceType,
    modelArtifactUri = modelArtifactUri,
    modelRegistryRef = modelRegistryRef,
    modelFormat = modelFormat,
    modelArtifactDigest = modelArtifactDigest,
    modelSignatureUri = modelSignatureUri,
    modelSizeBytes = modelSizeBytes,
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
    val modelId: UUID?,
    val modelName: String?,
    val modelVersion: String?,
    val sourceType: String?,
    val modelArtifactUri: String?,
    val modelRegistryRef: String?,
    val modelFormat: String?,
    val modelArtifactDigest: String?,
    val modelSignatureUri: String?,
    val modelSizeBytes: Int?,
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
