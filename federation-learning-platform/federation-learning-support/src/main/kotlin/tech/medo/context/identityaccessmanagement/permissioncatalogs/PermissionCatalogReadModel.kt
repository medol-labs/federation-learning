package tech.medo.identityaccessmanagement.permissioncatalogs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class PermissionCatalogReadModelQuery

class PermissionCatalogReadModelCriteria {
    var permissionId: StringFilter? = null
    var permissionCode: StringFilter? = null
    var permissionName: StringFilter? = null
    var description: StringFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class PermissionCatalogReadModelProjection : MetadataProjection {
    var permissionId: UUID? = null
    var permissionCode: String? = null
    var permissionName: String? = null
    var description: String? = null
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

fun PermissionCatalogReadModelProjection.toReadModel(): PermissionCatalogReadModel =
    PermissionCatalogReadModel(
    permissionId = permissionId,
    permissionCode = permissionCode,
    permissionName = permissionName,
    description = description,
    projectionUpdatedAt = projectionUpdatedAt,
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
    fun findById(id: UUID): PermissionCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): PermissionCatalogReadModel?
    fun findProjectionById(id: UUID): PermissionCatalogReadModelProjection?
    fun save(projection: PermissionCatalogReadModelProjection)
}

data class PermissionCatalogReadModel(
    val permissionId: UUID?,
    val permissionCode: String?,
    val permissionName: String?,
    val description: String?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
