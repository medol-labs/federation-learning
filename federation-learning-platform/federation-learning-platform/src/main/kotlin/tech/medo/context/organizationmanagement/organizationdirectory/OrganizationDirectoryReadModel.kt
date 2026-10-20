package tech.medo.organizationmanagement.organizationdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.organizationmanagement.domain.types.OrganizationType
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum
import java.time.LocalDateTime
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class OrganizationDirectoryReadModelQuery

class OrganizationDirectoryReadModelCriteria {
    var organizationId: StringFilter? = null
    var organizationName: StringFilter? = null
    var organizationType: Filter<OrganizationType>? = null
    var state: Filter<OrganizationStateEnum>? = null
    var approvedDatasetCount: IntegerFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class OrganizationDirectoryReadModelProjection : MetadataProjection {
    var organizationId: UUID? = null
    var organizationName: String? = null
    var organizationType: OrganizationType? = null
    var state: OrganizationStateEnum? = null
    var approvedDatasetCount: Int? = null
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

fun OrganizationDirectoryReadModelProjection.toReadModel(): OrganizationDirectoryReadModel =
    OrganizationDirectoryReadModel(
    organizationId = organizationId,
    organizationName = organizationName,
    organizationType = organizationType,
    state = state,
    approvedDatasetCount = approvedDatasetCount,
    projectionUpdatedAt = projectionUpdatedAt,
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
    fun findByIdWithAccessPolicy(id: UUID): OrganizationDirectoryReadModel?
    fun findProjectionById(id: UUID): OrganizationDirectoryReadModelProjection?
    fun save(projection: OrganizationDirectoryReadModelProjection)
}

data class OrganizationDirectoryReadModel(
    val organizationId: UUID?,
    val organizationName: String?,
    val organizationType: OrganizationType?,
    val state: OrganizationStateEnum?,
    val approvedDatasetCount: Int?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
