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
    fun findByIdWithAccessPolicy(id: UUID): RoleCatalogReadModel?
    fun findProjectionById(id: UUID): RoleCatalogReadModelProjection?
    fun save(projection: RoleCatalogReadModelProjection)
}

data class RoleCatalogReadModel(
    val roleId: UUID?,
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
