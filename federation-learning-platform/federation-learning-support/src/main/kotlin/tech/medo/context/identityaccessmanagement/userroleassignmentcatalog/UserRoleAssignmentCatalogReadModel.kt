package tech.medo.identityaccessmanagement.userroleassignmentcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import tech.jhipster.service.filter.RangeFilter
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
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class UserRoleAssignmentCatalogReadModelProjection : MetadataProjection {
    var userAccountId: UUID? = null
    var username: String? = null
    var roleCode: String? = null
    var roleName: String? = null
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

fun UserRoleAssignmentCatalogReadModelProjection.toReadModel(): UserRoleAssignmentCatalogReadModel =
    UserRoleAssignmentCatalogReadModel(
    userAccountId = userAccountId,
    username = username,
    roleCode = roleCode,
    roleName = roleName,
    projectionUpdatedAt = projectionUpdatedAt,
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
    fun findByIdWithAccessPolicy(id: UserRoleAssignmentCatalogReadModelKey): UserRoleAssignmentCatalogReadModel?
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
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
