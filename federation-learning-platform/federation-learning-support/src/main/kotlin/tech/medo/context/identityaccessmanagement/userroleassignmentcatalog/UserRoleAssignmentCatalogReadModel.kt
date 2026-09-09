package tech.medo.identityaccessmanagement.userroleassignmentcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;

import tech.jhipster.service.filter.StringFilter


data class UserRoleAssignmentCatalogReadModelKey(
    var userAccountId: UUID? = null,
    var roleCode: String? = null
) : java.io.Serializable

class UserRoleAssignmentCatalogReadModelQuery

class UserRoleAssignmentCatalogReadModelCriteria {
    var userAccountId: StringFilter? = null
    var username: StringFilter? = null
    var roleCode: StringFilter? = null
    var roleName: StringFilter? = null
}


class UserRoleAssignmentCatalogReadModelProjection : MetadataProjection {
    var userAccountId: UUID? = null
    var username: String? = null
    var roleCode: String? = null
    var roleName: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun UserRoleAssignmentCatalogReadModelProjection.toReadModel(): UserRoleAssignmentCatalogReadModel =
    UserRoleAssignmentCatalogReadModel(
    userAccountId = userAccountId,
    username = username,
    roleCode = roleCode,
    roleName = roleName,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface UserRoleAssignmentCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<UserRoleAssignmentCatalogReadModel>
    fun findAllByCriteria(criteria: UserRoleAssignmentCatalogReadModelCriteria?, pageable: Pageable): Page<UserRoleAssignmentCatalogReadModel>
    fun findById(id: UserRoleAssignmentCatalogReadModelKey): UserRoleAssignmentCatalogReadModel?
    fun findProjectionById(id: UserRoleAssignmentCatalogReadModelKey): UserRoleAssignmentCatalogReadModelProjection?
    fun findProjectionsByUserAccountId(userAccountId: UUID): List<UserRoleAssignmentCatalogReadModelProjection>
    fun findProjectionsByRoleCode(roleCode: String): List<UserRoleAssignmentCatalogReadModelProjection>
    fun save(projection: UserRoleAssignmentCatalogReadModelProjection)
}

data class UserRoleAssignmentCatalogReadModel(
    val userAccountId: UUID?,
    val username: String?,
    val roleCode: String?,
    val roleName: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
