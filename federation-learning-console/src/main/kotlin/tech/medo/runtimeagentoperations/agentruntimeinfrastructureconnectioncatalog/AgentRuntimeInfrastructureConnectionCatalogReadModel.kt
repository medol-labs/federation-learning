package tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class AgentRuntimeInfrastructureConnectionCatalogReadModelQuery

class AgentRuntimeInfrastructureConnectionCatalogReadModelProjection : MetadataProjection {
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    var runtimePlatformConnectionReady: Boolean? = null
    var platformApiReachable: Boolean? = null
    var agentAuthenticationSucceeded: Boolean? = null
    var controlChannelEstablished: Boolean? = null
    var heartbeatAccepted: Boolean? = null
    var connectedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AgentRuntimeInfrastructureConnectionCatalogReadModelProjection.toReadModel(): AgentRuntimeInfrastructureConnectionCatalogReadModel =
    AgentRuntimeInfrastructureConnectionCatalogReadModel(
    runtimeInfrastructureId = runtimeInfrastructureId,
    runtimeAgentId = runtimeAgentId,
    runtimePlatformConnectionReady = runtimePlatformConnectionReady,
    platformApiReachable = platformApiReachable,
    agentAuthenticationSucceeded = agentAuthenticationSucceeded,
    controlChannelEstablished = controlChannelEstablished,
    heartbeatAccepted = heartbeatAccepted,
    connectedAt = connectedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentRuntimeInfrastructureConnectionCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<AgentRuntimeInfrastructureConnectionCatalogReadModel>
    fun findById(id: UUID): AgentRuntimeInfrastructureConnectionCatalogReadModel?
    fun findProjectionById(id: UUID): AgentRuntimeInfrastructureConnectionCatalogReadModelProjection?
    fun save(projection: AgentRuntimeInfrastructureConnectionCatalogReadModelProjection)
}

data class AgentRuntimeInfrastructureConnectionCatalogReadModel(
    val runtimeInfrastructureId: UUID?,
    val runtimeAgentId: UUID?,
    val runtimePlatformConnectionReady: Boolean?,
    val platformApiReachable: Boolean?,
    val agentAuthenticationSucceeded: Boolean?,
    val controlChannelEstablished: Boolean?,
    val heartbeatAccepted: Boolean?,
    val connectedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
