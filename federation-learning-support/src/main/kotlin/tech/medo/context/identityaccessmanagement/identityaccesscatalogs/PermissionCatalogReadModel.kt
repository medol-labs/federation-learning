package tech.medo.identityaccessmanagement.identityaccesscatalogs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection

import tech.jhipster.service.filter.StringFilter


class PermissionCatalogReadModelQuery

class PermissionCatalogReadModelCriteria {
    var permissionCode: StringFilter? = null
    var permissionName: StringFilter? = null
    var description: StringFilter? = null
}


class PermissionCatalogReadModelProjection : MetadataProjection {
    var permissionCode: String? = null
    var permissionName: String? = null
    var description: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun PermissionCatalogReadModelProjection.toReadModel(): PermissionCatalogReadModel =
    PermissionCatalogReadModel(
    permissionCode = permissionCode,
    permissionName = permissionName,
    description = description,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface PermissionCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<PermissionCatalogReadModel>
    fun findAllByCriteria(criteria: PermissionCatalogReadModelCriteria?, pageable: Pageable): Page<PermissionCatalogReadModel>
    fun findById(id: String): PermissionCatalogReadModel?
    fun findProjectionById(id: String): PermissionCatalogReadModelProjection?
    fun save(projection: PermissionCatalogReadModelProjection)
}

data class PermissionCatalogReadModel(
    val permissionCode: String?,
    val permissionName: String?,
    val description: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
