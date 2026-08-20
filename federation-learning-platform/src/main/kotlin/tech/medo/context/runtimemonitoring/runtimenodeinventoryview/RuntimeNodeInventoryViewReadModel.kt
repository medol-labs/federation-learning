package tech.medo.runtimemonitoring.runtimenodeinventoryview

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeNodeInventoryViewReadModelQuery

class RuntimeNodeInventoryViewReadModelCriteria {
    var nodeId: StringFilter? = null
    var runtimeNodeInventoryReportId: StringFilter? = null
    var organizationId: StringFilter? = null
    var runtimeInfrastructureId: StringFilter? = null
    var runtimeAgentId: StringFilter? = null
    var organizationName: StringFilter? = null
    var runtimeName: StringFilter? = null
    var runtimeNodeName: StringFilter? = null
    var infrastructureNodeId: StringFilter? = null
    var runtimeNodeRole: StringFilter? = null
    var nodeReady: BooleanFilter? = null
    var runtimeEngineVersion: StringFilter? = null
    var containerEngineVersion: StringFilter? = null
    var operatingSystem: StringFilter? = null
    var architecture: StringFilter? = null
    var inventoryHash: StringFilter? = null
    var discoveredAt: RangeFilter<LocalDateTime>? = null
    var recordedAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeNodeInventoryViewReadModelProjection : MetadataProjection {
    var nodeId: UUID? = null
    var runtimeNodeInventoryReportId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var runtimeNodeName: String? = null
    var infrastructureNodeId: String? = null
    var runtimeNodeRole: String? = null
    var nodeReady: Boolean? = null
    var runtimeEngineVersion: String? = null
    var containerEngineVersion: String? = null
    var operatingSystem: String? = null
    var architecture: String? = null
    var inventoryHash: String? = null
    var discoveredAt: LocalDateTime? = null
    var recordedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeNodeInventoryViewReadModelProjection.toReadModel(): RuntimeNodeInventoryViewReadModel =
    RuntimeNodeInventoryViewReadModel(
    nodeId = nodeId,
    runtimeNodeInventoryReportId = runtimeNodeInventoryReportId,
    organizationId = organizationId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    runtimeAgentId = runtimeAgentId,
    organizationName = organizationName,
    runtimeName = runtimeName,
    runtimeNodeName = runtimeNodeName,
    infrastructureNodeId = infrastructureNodeId,
    runtimeNodeRole = runtimeNodeRole,
    nodeReady = nodeReady,
    runtimeEngineVersion = runtimeEngineVersion,
    containerEngineVersion = containerEngineVersion,
    operatingSystem = operatingSystem,
    architecture = architecture,
    inventoryHash = inventoryHash,
    discoveredAt = discoveredAt,
    recordedAt = recordedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeNodeInventoryViewReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeNodeInventoryViewReadModel>
    fun findAllByCriteria(criteria: RuntimeNodeInventoryViewReadModelCriteria?, pageable: Pageable): Page<RuntimeNodeInventoryViewReadModel>
    fun findById(id: UUID): RuntimeNodeInventoryViewReadModel?
    fun findProjectionById(id: UUID): RuntimeNodeInventoryViewReadModelProjection?
    fun save(projection: RuntimeNodeInventoryViewReadModelProjection)
}

data class RuntimeNodeInventoryViewReadModel(
    val nodeId: UUID?,
    val runtimeNodeInventoryReportId: UUID?,
    val organizationId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val runtimeAgentId: UUID?,
    val organizationName: String?,
    val runtimeName: String?,
    val runtimeNodeName: String?,
    val infrastructureNodeId: String?,
    val runtimeNodeRole: String?,
    val nodeReady: Boolean?,
    val runtimeEngineVersion: String?,
    val containerEngineVersion: String?,
    val operatingSystem: String?,
    val architecture: String?,
    val inventoryHash: String?,
    val discoveredAt: LocalDateTime?,
    val recordedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
