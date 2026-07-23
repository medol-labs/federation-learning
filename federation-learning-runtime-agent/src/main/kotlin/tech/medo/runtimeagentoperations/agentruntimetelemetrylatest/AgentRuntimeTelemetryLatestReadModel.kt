package tech.medo.runtimeagentoperations.agentruntimetelemetrylatest

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class AgentRuntimeTelemetryLatestReadModelQuery

class AgentRuntimeTelemetryLatestReadModelProjection : MetadataProjection {
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var trainingJobId: UUID? = null
    var roundExecutionId: UUID? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var telemetryRetentionPolicy: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun AgentRuntimeTelemetryLatestReadModelProjection.toReadModel(): AgentRuntimeTelemetryLatestReadModel =
    AgentRuntimeTelemetryLatestReadModel(
    nodeId = nodeId,
    runtimeAgentId = runtimeAgentId,
    federationId = federationId,
    trainingJobId = trainingJobId,
    roundExecutionId = roundExecutionId,
    cpuLoad = cpuLoad,
    gpuLoad = gpuLoad,
    memoryLoad = memoryLoad,
    lastHeartbeatAt = lastHeartbeatAt,
    telemetryRetentionPolicy = telemetryRetentionPolicy,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface AgentRuntimeTelemetryLatestReadModelRepository {
    fun findAll(pageable: Pageable): Page<AgentRuntimeTelemetryLatestReadModel>
    fun findById(id: UUID): AgentRuntimeTelemetryLatestReadModel?
    fun findProjectionById(id: UUID): AgentRuntimeTelemetryLatestReadModelProjection?
    fun save(projection: AgentRuntimeTelemetryLatestReadModelProjection)
}

data class AgentRuntimeTelemetryLatestReadModel(
    val nodeId: UUID?,
    val runtimeAgentId: UUID?,
    val federationId: UUID?,
    val trainingJobId: UUID?,
    val roundExecutionId: UUID?,
    val cpuLoad: BigDecimal?,
    val gpuLoad: BigDecimal?,
    val memoryLoad: BigDecimal?,
    val lastHeartbeatAt: LocalDateTime?,
    val telemetryRetentionPolicy: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
