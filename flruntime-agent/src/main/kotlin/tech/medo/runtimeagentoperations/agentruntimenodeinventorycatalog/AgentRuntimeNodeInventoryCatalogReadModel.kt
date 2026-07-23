package tech.medo.runtimeagentoperations.agentruntimenodeinventorycatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class AgentRuntimeNodeInventoryCatalogReadModelQuery

class AgentRuntimeNodeInventoryCatalogReadModelProjection : MetadataProjection {
    var runtimeNodeInventoryReportId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var organizationName: String? = null
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
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AgentRuntimeNodeInventoryCatalogReadModelProjection.toReadModel(): AgentRuntimeNodeInventoryCatalogReadModel =
    AgentRuntimeNodeInventoryCatalogReadModel(
    runtimeNodeInventoryReportId = runtimeNodeInventoryReportId,
    organizationId = organizationId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    runtimeAgentId = runtimeAgentId,
    organizationName = organizationName,
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
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentRuntimeNodeInventoryCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<AgentRuntimeNodeInventoryCatalogReadModel>
    fun findById(id: UUID): AgentRuntimeNodeInventoryCatalogReadModel?
    fun findProjectionById(id: UUID): AgentRuntimeNodeInventoryCatalogReadModelProjection?
    fun save(projection: AgentRuntimeNodeInventoryCatalogReadModelProjection)
}

data class AgentRuntimeNodeInventoryCatalogReadModel(
    val runtimeNodeInventoryReportId: UUID?,
    val organizationId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val runtimeAgentId: UUID?,
    val organizationName: String?,
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
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
