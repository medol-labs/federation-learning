package tech.medo.organizationmanagement.userorganizationmembershipdirectory

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.organizationmanagement.domain.states.UserOrganizationMembershipStateEnum;

import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.StringFilter


class UserOrganizationMembershipDirectoryReadModelQuery

class UserOrganizationMembershipDirectoryReadModelCriteria {
    var userOrganizationMembershipId: StringFilter? = null
    var userAccountId: StringFilter? = null
    var username: StringFilter? = null
    var organizationId: StringFilter? = null
    var organizationName: StringFilter? = null
    var organizationUserRole: StringFilter? = null
    var state: Filter<UserOrganizationMembershipStateEnum>? = null
}


class UserOrganizationMembershipDirectoryReadModelProjection : MetadataProjection {
    var userOrganizationMembershipId: UUID? = null
    var userAccountId: UUID? = null
    var username: String? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var organizationUserRole: String? = null
    var state: UserOrganizationMembershipStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun UserOrganizationMembershipDirectoryReadModelProjection.toReadModel(): UserOrganizationMembershipDirectoryReadModel =
    UserOrganizationMembershipDirectoryReadModel(
    userOrganizationMembershipId = userOrganizationMembershipId,
    userAccountId = userAccountId,
    username = username,
    organizationId = organizationId,
    organizationName = organizationName,
    organizationUserRole = organizationUserRole,
    state = state,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface UserOrganizationMembershipDirectoryReadModelRepository {
    fun findAll(pageable: Pageable): Page<UserOrganizationMembershipDirectoryReadModel>
    fun findAllByCriteria(criteria: UserOrganizationMembershipDirectoryReadModelCriteria?, pageable: Pageable): Page<UserOrganizationMembershipDirectoryReadModel>
    fun findById(id: UUID): UserOrganizationMembershipDirectoryReadModel?
    fun findProjectionById(id: UUID): UserOrganizationMembershipDirectoryReadModelProjection?
    fun save(projection: UserOrganizationMembershipDirectoryReadModelProjection)
}

data class UserOrganizationMembershipDirectoryReadModel(
    val userOrganizationMembershipId: UUID?,
    val userAccountId: UUID?,
    val username: String?,
    val organizationId: UUID?,
    val organizationName: String?,
    val organizationUserRole: String?,
    val state: UserOrganizationMembershipStateEnum?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
