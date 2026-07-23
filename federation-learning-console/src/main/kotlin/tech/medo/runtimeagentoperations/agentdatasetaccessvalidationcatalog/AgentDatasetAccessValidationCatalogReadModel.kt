package tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class AgentDatasetAccessValidationCatalogReadModelQuery

class AgentDatasetAccessValidationCatalogReadModelProjection : MetadataProjection {
    var datasetAccessValidationId: UUID? = null
    var runtimeDatasetBindingId: UUID? = null
    var datasetId: UUID? = null
    var runtimeId: UUID? = null
    var datasetName: String? = null
    var readable: Boolean? = null
    var schemaReadable: Boolean? = null
    var sampleBatchReadable: Boolean? = null
    var validationStatus: String? = null
    var failureReason: String? = null
    var validatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AgentDatasetAccessValidationCatalogReadModelProjection.toReadModel(): AgentDatasetAccessValidationCatalogReadModel =
    AgentDatasetAccessValidationCatalogReadModel(
    datasetAccessValidationId = datasetAccessValidationId,
    runtimeDatasetBindingId = runtimeDatasetBindingId,
    datasetId = datasetId,
    runtimeId = runtimeId,
    datasetName = datasetName,
    readable = readable,
    schemaReadable = schemaReadable,
    sampleBatchReadable = sampleBatchReadable,
    validationStatus = validationStatus,
    failureReason = failureReason,
    validatedAt = validatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentDatasetAccessValidationCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<AgentDatasetAccessValidationCatalogReadModel>
    fun findById(id: UUID): AgentDatasetAccessValidationCatalogReadModel?
    fun findProjectionById(id: UUID): AgentDatasetAccessValidationCatalogReadModelProjection?
    fun save(projection: AgentDatasetAccessValidationCatalogReadModelProjection)
}

data class AgentDatasetAccessValidationCatalogReadModel(
    val datasetAccessValidationId: UUID?,
    val runtimeDatasetBindingId: UUID?,
    val datasetId: UUID?,
    val runtimeId: UUID?,
    val datasetName: String?,
    val readable: Boolean?,
    val schemaReadable: Boolean?,
    val sampleBatchReadable: Boolean?,
    val validationStatus: String?,
    val failureReason: String?,
    val validatedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
