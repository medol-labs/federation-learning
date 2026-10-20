package tech.medo.runtimeagentoperations.agentfeatureschemacatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class AgentFeatureSchemaCatalogReadModelQuery

class AgentFeatureSchemaCatalogReadModelCriteria {
    var featureSchemaId: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var schemaStatus: StringFilter? = null
    var syncedAt: RangeFilter<LocalDateTime>? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class AgentFeatureSchemaCatalogReadModelProjection : MetadataProjection {
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var schemaStatus: String? = null
    var syncedAt: LocalDateTime? = null
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}

fun AgentFeatureSchemaCatalogReadModelProjection.toReadModel(): AgentFeatureSchemaCatalogReadModel =
    AgentFeatureSchemaCatalogReadModel(
    featureSchemaId = featureSchemaId,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    schemaStatus = schemaStatus,
    syncedAt = syncedAt,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentFeatureSchemaCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<AgentFeatureSchemaCatalogReadModel>
    fun findAllByCriteria(criteria: AgentFeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<AgentFeatureSchemaCatalogReadModel>
    fun findById(id: UUID): AgentFeatureSchemaCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): AgentFeatureSchemaCatalogReadModel?
    fun findProjectionById(id: UUID): AgentFeatureSchemaCatalogReadModelProjection?
    fun save(projection: AgentFeatureSchemaCatalogReadModelProjection)
}

data class AgentFeatureSchemaCatalogReadModel(
    val featureSchemaId: UUID?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val schemaStatus: String?,
    val syncedAt: LocalDateTime?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
