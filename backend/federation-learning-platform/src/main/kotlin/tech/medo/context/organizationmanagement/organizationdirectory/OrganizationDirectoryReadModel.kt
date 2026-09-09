package tech.medo.organizationmanagement.organizationdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.organizationmanagement.domain.types.OrganizationType;
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum;

import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.StringFilter


class OrganizationDirectoryReadModelQuery

class OrganizationDirectoryReadModelCriteria {
    var organizationId: StringFilter? = null
    var organizationName: StringFilter? = null
    var organizationType: Filter<OrganizationType>? = null
    var state: Filter<OrganizationStateEnum>? = null
    var approvedDatasetCount: IntegerFilter? = null
}


class OrganizationDirectoryReadModelProjection : MetadataProjection {
    var organizationId: UUID? = null
    var organizationName: String? = null
    var organizationType: OrganizationType? = null
    var state: OrganizationStateEnum? = null
    var approvedDatasetCount: Int? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun OrganizationDirectoryReadModelProjection.toReadModel(): OrganizationDirectoryReadModel =
    OrganizationDirectoryReadModel(
    organizationId = organizationId,
    organizationName = organizationName,
    organizationType = organizationType,
    state = state,
    approvedDatasetCount = approvedDatasetCount,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface OrganizationDirectoryReadModelRepository {
    fun findAll(pageable: Pageable): Page<OrganizationDirectoryReadModel>
    fun findAllByCriteria(criteria: OrganizationDirectoryReadModelCriteria?, pageable: Pageable): Page<OrganizationDirectoryReadModel>
    fun findById(id: UUID): OrganizationDirectoryReadModel?
    fun findProjectionById(id: UUID): OrganizationDirectoryReadModelProjection?
    fun save(projection: OrganizationDirectoryReadModelProjection)
}

data class OrganizationDirectoryReadModel(
    val organizationId: UUID?,
    val organizationName: String?,
    val organizationType: OrganizationType?,
    val state: OrganizationStateEnum?,
    val approvedDatasetCount: Int?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
