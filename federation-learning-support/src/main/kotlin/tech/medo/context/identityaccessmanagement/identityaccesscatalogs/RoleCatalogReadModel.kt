package tech.medo.identityaccessmanagement.identityaccesscatalogs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection

import tech.jhipster.service.filter.StringFilter


class RoleCatalogReadModelQuery

class RoleCatalogReadModelCriteria {
    var roleCode: StringFilter? = null
    var roleName: StringFilter? = null
}


class RoleCatalogReadModelProjection : MetadataProjection {
    var roleCode: String? = null
    var roleName: String? = null
    var permissionCodes: List<String> = emptyList()
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RoleCatalogReadModelProjection.toReadModel(): RoleCatalogReadModel =
    RoleCatalogReadModel(
    roleCode = roleCode,
    roleName = roleName,
    permissionCodes = permissionCodes,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RoleCatalogReadModelRepository {
    fun findAllByFilter(roleCode: String?, roleName: String?, pageable: Pageable): Page<RoleCatalogReadModel>
    fun findAllByCriteria(criteria: RoleCatalogReadModelCriteria?, pageable: Pageable): Page<RoleCatalogReadModel>
    fun findById(id: String): RoleCatalogReadModel?
    fun findProjectionById(id: String): RoleCatalogReadModelProjection?
    fun save(projection: RoleCatalogReadModelProjection)
}

data class RoleCatalogReadModel(
    val roleCode: String?,
    val roleName: String?,
    val permissionCodes: List<String>,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
