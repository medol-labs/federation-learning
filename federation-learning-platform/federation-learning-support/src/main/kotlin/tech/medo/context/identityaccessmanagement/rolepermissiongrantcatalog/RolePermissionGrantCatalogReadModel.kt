package tech.medo.identityaccessmanagement.rolepermissiongrantcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;

import tech.jhipster.service.filter.StringFilter


data class RolePermissionGrantCatalogReadModelKey(
    var roleCode: String? = null,
    var permissionCode: String? = null
) : java.io.Serializable

class RolePermissionGrantCatalogReadModelQuery

class RolePermissionGrantCatalogReadModelCriteria {
    var roleId: StringFilter? = null
    var roleCode: StringFilter? = null
    var roleName: StringFilter? = null
    var permissionCode: StringFilter? = null
    var permissionName: StringFilter? = null
}


class RolePermissionGrantCatalogReadModelProjection : MetadataProjection {
    var roleId: UUID? = null
    var roleCode: String? = null
    var roleName: String? = null
    var permissionCode: String? = null
    var permissionName: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RolePermissionGrantCatalogReadModelProjection.toReadModel(): RolePermissionGrantCatalogReadModel =
    RolePermissionGrantCatalogReadModel(
    roleId = roleId,
    roleCode = roleCode,
    roleName = roleName,
    permissionCode = permissionCode,
    permissionName = permissionName,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RolePermissionGrantCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RolePermissionGrantCatalogReadModel>
    fun findAllByCriteria(criteria: RolePermissionGrantCatalogReadModelCriteria?, pageable: Pageable): Page<RolePermissionGrantCatalogReadModel>
    fun findById(id: RolePermissionGrantCatalogReadModelKey): RolePermissionGrantCatalogReadModel?
    fun findProjectionById(id: RolePermissionGrantCatalogReadModelKey): RolePermissionGrantCatalogReadModelProjection?
    fun findProjectionsByRoleCode(roleCode: String): List<RolePermissionGrantCatalogReadModelProjection>
    fun findProjectionsByPermissionCode(permissionCode: String): List<RolePermissionGrantCatalogReadModelProjection>
    fun save(projection: RolePermissionGrantCatalogReadModelProjection)
}

data class RolePermissionGrantCatalogReadModel(
    val roleId: UUID?,
    val roleCode: String?,
    val roleName: String?,
    val permissionCode: String?,
    val permissionName: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
