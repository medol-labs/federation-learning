package tech.medo.runtimeagentoperations.agentorganizationdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class AgentOrganizationDirectoryReadModelQuery

class AgentOrganizationDirectoryReadModelCriteria {
    var organizationId: StringFilter? = null
    var organizationName: StringFilter? = null
    var organizationType: StringFilter? = null
    var state: StringFilter? = null
    var syncedAt: RangeFilter<LocalDateTime>? = null
}


class AgentOrganizationDirectoryReadModelProjection : MetadataProjection {
    var organizationId: UUID? = null
    var organizationName: String? = null
    var organizationType: String? = null
    var state: String? = null
    var syncedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AgentOrganizationDirectoryReadModelProjection.toReadModel(): AgentOrganizationDirectoryReadModel =
    AgentOrganizationDirectoryReadModel(
    organizationId = organizationId,
    organizationName = organizationName,
    organizationType = organizationType,
    state = state,
    syncedAt = syncedAt,
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
    fun findProjectionById(id: UUID): AgentOrganizationDirectoryReadModelProjection?
    fun save(projection: AgentOrganizationDirectoryReadModelProjection)
}

data class AgentOrganizationDirectoryReadModel(
    val organizationId: UUID?,
    val organizationName: String?,
    val organizationType: String?,
    val state: String?,
    val syncedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
