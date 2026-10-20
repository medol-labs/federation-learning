package tech.medo.runtimeagentoperations.agentruntimeidentitycatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class AgentRuntimeIdentityCatalogReadModelQuery

class AgentRuntimeIdentityCatalogReadModelCriteria {
    var runtimeId: StringFilter? = null
    var runtimeInfrastructureId: StringFilter? = null
    var runtimeAgentId: StringFilter? = null
    var organizationId: StringFilter? = null
    var organizationName: StringFilter? = null
    var runtimeName: StringFilter? = null
    var identityStatus: StringFilter? = null
    var activatedAt: RangeFilter<LocalDateTime>? = null
    var revokedAt: RangeFilter<LocalDateTime>? = null
    var syncedAt: RangeFilter<LocalDateTime>? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class AgentRuntimeIdentityCatalogReadModelProjection : MetadataProjection {
    var runtimeId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var identityStatus: String? = null
    var activatedAt: LocalDateTime? = null
    var revokedAt: LocalDateTime? = null
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

fun AgentRuntimeIdentityCatalogReadModelProjection.toReadModel(): AgentRuntimeIdentityCatalogReadModel =
    AgentRuntimeIdentityCatalogReadModel(
    runtimeId = runtimeId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    runtimeAgentId = runtimeAgentId,
    organizationId = organizationId,
    organizationName = organizationName,
    runtimeName = runtimeName,
    identityStatus = identityStatus,
    activatedAt = activatedAt,
    revokedAt = revokedAt,
    syncedAt = syncedAt,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentRuntimeIdentityCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<AgentRuntimeIdentityCatalogReadModel>
    fun findAllByCriteria(criteria: AgentRuntimeIdentityCatalogReadModelCriteria?, pageable: Pageable): Page<AgentRuntimeIdentityCatalogReadModel>
    fun findById(id: UUID): AgentRuntimeIdentityCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): AgentRuntimeIdentityCatalogReadModel?
    fun findProjectionById(id: UUID): AgentRuntimeIdentityCatalogReadModelProjection?
    fun save(projection: AgentRuntimeIdentityCatalogReadModelProjection)
}

data class AgentRuntimeIdentityCatalogReadModel(
    val runtimeId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val runtimeAgentId: UUID?,
    val organizationId: UUID?,
    val organizationName: String?,
    val runtimeName: String?,
    val identityStatus: String?,
    val activatedAt: LocalDateTime?,
    val revokedAt: LocalDateTime?,
    val syncedAt: LocalDateTime?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
