package tech.medo.runtimeagentoperations.agentorganizationdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class AgentOrganizationDirectoryReadModelQuery

class AgentOrganizationDirectoryReadModelCriteria {
    var organizationId: StringFilter? = null
    var organizationName: StringFilter? = null
    var organizationType: StringFilter? = null
    var state: StringFilter? = null
    var syncedAt: RangeFilter<LocalDateTime>? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class AgentOrganizationDirectoryReadModelProjection : MetadataProjection {
    var organizationId: UUID? = null
    var organizationName: String? = null
    var organizationType: String? = null
    var state: String? = null
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

fun AgentOrganizationDirectoryReadModelProjection.toReadModel(): AgentOrganizationDirectoryReadModel =
    AgentOrganizationDirectoryReadModel(
    organizationId = organizationId,
    organizationName = organizationName,
    organizationType = organizationType,
    state = state,
    syncedAt = syncedAt,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentOrganizationDirectoryReadModelRepository {
    fun findAll(pageable: Pageable): Page<AgentOrganizationDirectoryReadModel>
    fun findAllByCriteria(criteria: AgentOrganizationDirectoryReadModelCriteria?, pageable: Pageable): Page<AgentOrganizationDirectoryReadModel>
    fun findById(id: UUID): AgentOrganizationDirectoryReadModel?
    fun findByIdWithAccessPolicy(id: UUID): AgentOrganizationDirectoryReadModel?
    fun findProjectionById(id: UUID): AgentOrganizationDirectoryReadModelProjection?
    fun save(projection: AgentOrganizationDirectoryReadModelProjection)
}

data class AgentOrganizationDirectoryReadModel(
    val organizationId: UUID?,
    val organizationName: String?,
    val organizationType: String?,
    val state: String?,
    val syncedAt: LocalDateTime?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
