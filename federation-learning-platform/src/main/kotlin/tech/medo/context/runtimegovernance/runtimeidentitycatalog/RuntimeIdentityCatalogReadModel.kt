package tech.medo.runtimegovernance.runtimeidentitycatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeIdentityCatalogReadModelQuery

class RuntimeIdentityCatalogReadModelCriteria {
    var runtimeId: StringFilter? = null
    var runtimeInfrastructureId: StringFilter? = null
    var runtimeAgentId: StringFilter? = null
    var organizationId: StringFilter? = null
    var organizationName: StringFilter? = null
    var runtimeName: StringFilter? = null
    var identityStatus: StringFilter? = null
    var activatedAt: RangeFilter<LocalDateTime>? = null
    var revokedAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeIdentityCatalogReadModelProjection : MetadataProjection {
    var runtimeId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var identityStatus: String? = null
    var activatedAt: LocalDateTime? = null
    var revokedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeIdentityCatalogReadModelProjection.toReadModel(): RuntimeIdentityCatalogReadModel =
    RuntimeIdentityCatalogReadModel(
    runtimeId = runtimeId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    runtimeAgentId = runtimeAgentId,
    organizationId = organizationId,
    organizationName = organizationName,
    runtimeName = runtimeName,
    identityStatus = identityStatus,
    activatedAt = activatedAt,
    revokedAt = revokedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeIdentityCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeIdentityCatalogReadModel>
    fun findAllByCriteria(criteria: RuntimeIdentityCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeIdentityCatalogReadModel>
    fun findById(id: UUID): RuntimeIdentityCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeIdentityCatalogReadModelProjection?
    fun save(projection: RuntimeIdentityCatalogReadModelProjection)
}

data class RuntimeIdentityCatalogReadModel(
    val runtimeId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val runtimeAgentId: UUID?,
    val organizationId: UUID?,
    val organizationName: String?,
    val runtimeName: String?,
    val identityStatus: String?,
    val activatedAt: LocalDateTime?,
    val revokedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
