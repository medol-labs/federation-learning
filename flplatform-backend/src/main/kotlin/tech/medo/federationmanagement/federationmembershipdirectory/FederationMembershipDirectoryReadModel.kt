package tech.medo.federationmanagement.federationmembershipdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


data class FederationMembershipDirectoryReadModelKey(
    var federationId: UUID? = null,
    var organizationId: UUID? = null
) : java.io.Serializable

class FederationMembershipDirectoryReadModelQuery

class FederationMembershipDirectoryReadModelProjection : MetadataProjection {
    var federationId: UUID? = null
    var organizationId: UUID? = null
    var federationName: String? = null
    var organizationName: String? = null
    var membershipStatus: String? = null
    var invitationNote: String? = null
    var approvalNote: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
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
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface FederationMembershipDirectoryReadModelRepository {
    fun findAll(pageable: Pageable): Page<FederationMembershipDirectoryReadModel>
    fun findById(id: FederationMembershipDirectoryReadModelKey): FederationMembershipDirectoryReadModel?
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
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
