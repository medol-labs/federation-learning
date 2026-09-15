package tech.medo.runtimeagentoperations.agentfeatureschemacatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class AgentFeatureSchemaCatalogReadModelQuery

class AgentFeatureSchemaCatalogReadModelCriteria {
    var featureSchemaId: StringFilter? = null
    var featureDomain: StringFilter? = null
    var featureSchemaVersion: StringFilter? = null
    var schemaStatus: StringFilter? = null
    var syncedAt: RangeFilter<LocalDateTime>? = null
}


class AgentFeatureSchemaCatalogReadModelProjection : MetadataProjection {
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var schemaStatus: String? = null
    var syncedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AgentFeatureSchemaCatalogReadModelProjection.toReadModel(): AgentFeatureSchemaCatalogReadModel =
    AgentFeatureSchemaCatalogReadModel(
    featureSchemaId = featureSchemaId,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    schemaStatus = schemaStatus,
    syncedAt = syncedAt,
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
    fun findProjectionById(id: UUID): AgentFeatureSchemaCatalogReadModelProjection?
    fun save(projection: AgentFeatureSchemaCatalogReadModelProjection)
}

data class AgentFeatureSchemaCatalogReadModel(
    val featureSchemaId: UUID?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val schemaStatus: String?,
    val syncedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
