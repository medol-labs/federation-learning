package tech.medo.identityaccessmanagement.rolecatalogs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID

import java.time.LocalDateTime
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RoleCatalogReadModelQuery

class RoleCatalogReadModelCriteria {
    var roleId: StringFilter? = null
    var roleCode: StringFilter? = null
    var roleName: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class RoleCatalogReadModelProjection : MetadataProjection {
    var roleId: UUID? = null
    var roleCode: String? = null
    var roleName: String? = null
    override var projectionUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RoleCatalogReadModelProjection.toReadModel(): RoleCatalogReadModel =
    RoleCatalogReadModel(
    roleId = roleId,
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

interface RoleCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RoleCatalogReadModel>
    fun findAllByCriteria(criteria: RoleCatalogReadModelCriteria?, pageable: Pageable): Page<RoleCatalogReadModel>
    fun findById(id: UUID): RoleCatalogReadModel?
    fun findProjectionById(id: UUID): RoleCatalogReadModelProjection?
    fun save(projection: RoleCatalogReadModelProjection)
}

data class RoleCatalogReadModel(
    val roleId: UUID?,
    val roleCode: String?,
    val roleName: String?,
    val projectionUpdatedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
