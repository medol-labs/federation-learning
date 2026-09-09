package tech.medo.runtimeprovisioning.runtimeagentendpointcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeAgentEndpointCatalogReadModelQuery

class RuntimeAgentEndpointCatalogReadModelCriteria {
    var runtimeAgentId: StringFilter? = null
    var runtimeId: StringFilter? = null
    var runtimeInfrastructureId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeName: StringFilter? = null
    var runtimeAgentEndpoint: StringFilter? = null
    var endpointScope: StringFilter? = null
    var connectionStatus: StringFilter? = null
    var connectedAt: RangeFilter<LocalDateTime>? = null
    var activatedAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeAgentEndpointCatalogReadModelProjection : MetadataProjection {
    var runtimeAgentId: UUID? = null
    var runtimeId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var organizationId: UUID? = null
    var runtimeName: String? = null
    var runtimeAgentEndpoint: String? = null
    var endpointScope: String? = null
    var connectionStatus: String? = null
    var connectedAt: LocalDateTime? = null
    var activatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeAgentEndpointCatalogReadModelProjection.toReadModel(): RuntimeAgentEndpointCatalogReadModel =
    RuntimeAgentEndpointCatalogReadModel(
    runtimeAgentId = runtimeAgentId,
    runtimeId = runtimeId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    organizationId = organizationId,
    runtimeName = runtimeName,
    runtimeAgentEndpoint = runtimeAgentEndpoint,
    endpointScope = endpointScope,
    connectionStatus = connectionStatus,
    connectedAt = connectedAt,
    activatedAt = activatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeAgentEndpointCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeAgentEndpointCatalogReadModel>
    fun findAllByCriteria(criteria: RuntimeAgentEndpointCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeAgentEndpointCatalogReadModel>
    fun findById(id: UUID): RuntimeAgentEndpointCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeAgentEndpointCatalogReadModelProjection?
    fun save(projection: RuntimeAgentEndpointCatalogReadModelProjection)
}

data class RuntimeAgentEndpointCatalogReadModel(
    val runtimeAgentId: UUID?,
    val runtimeId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val organizationId: UUID?,
    val runtimeName: String?,
    val runtimeAgentEndpoint: String?,
    val endpointScope: String?,
    val connectionStatus: String?,
    val connectedAt: LocalDateTime?,
    val activatedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
