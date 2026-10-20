package tech.medo.federationmanagement.federationmembershipdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


data class FederationMembershipDirectoryReadModelKey(
    var federationId: UUID? = null,
    var organizationId: UUID? = null
) : java.io.Serializable

class FederationMembershipDirectoryReadModelQuery

class FederationMembershipDirectoryReadModelCriteria {
    var federationId: StringFilter? = null
    var organizationId: StringFilter? = null
    var federationName: StringFilter? = null
    var organizationName: StringFilter? = null
    var membershipStatus: StringFilter? = null
    var invitationNote: StringFilter? = null
    var approvalNote: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class FederationMembershipDirectoryReadModelProjection : MetadataProjection {
    var federationId: UUID? = null
    var organizationId: UUID? = null
    var federationName: String? = null
    var organizationName: String? = null
    var membershipStatus: String? = null
    var invitationNote: String? = null
    var approvalNote: String? = null
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

fun FederationMembershipDirectoryReadModelProjection.toReadModel(): FederationMembershipDirectoryReadModel =
    FederationMembershipDirectoryReadModel(
    federationId = federationId,
    organizationId = organizationId,
    federationName = federationName,
    organizationName = organizationName,
    membershipStatus = membershipStatus,
    invitationNote = invitationNote,
    approvalNote = approvalNote,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface FederationMembershipDirectoryReadModelRepository {
    fun findAll(pageable: Pageable): Page<FederationMembershipDirectoryReadModel>
    fun findAllByCriteria(criteria: FederationMembershipDirectoryReadModelCriteria?, pageable: Pageable): Page<FederationMembershipDirectoryReadModel>
    fun findById(id: FederationMembershipDirectoryReadModelKey): FederationMembershipDirectoryReadModel?
    fun findByIdWithAccessPolicy(id: FederationMembershipDirectoryReadModelKey): FederationMembershipDirectoryReadModel?
    fun findProjectionById(id: FederationMembershipDirectoryReadModelKey): FederationMembershipDirectoryReadModelProjection?
    fun findProjectionsByFederationId(federationId: UUID): List<FederationMembershipDirectoryReadModelProjection>
    fun findProjectionsByOrganizationId(organizationId: UUID): List<FederationMembershipDirectoryReadModelProjection>
    fun save(projection: FederationMembershipDirectoryReadModelProjection)
}

data class FederationMembershipDirectoryReadModel(
    val federationId: UUID?,
    val organizationId: UUID?,
    val federationName: String?,
    val organizationName: String?,
    val membershipStatus: String?,
    val invitationNote: String?,
    val approvalNote: String?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
