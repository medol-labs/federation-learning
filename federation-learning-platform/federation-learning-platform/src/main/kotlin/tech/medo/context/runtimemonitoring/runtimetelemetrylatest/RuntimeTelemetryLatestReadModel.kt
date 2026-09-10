package tech.medo.runtimemonitoring.runtimetelemetrylatest

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BigDecimalFilter
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeTelemetryLatestReadModelQuery

class RuntimeTelemetryLatestReadModelCriteria {
    var nodeId: StringFilter? = null
    var runtimeAgentId: StringFilter? = null
    var federationId: StringFilter? = null
    var federationName: StringFilter? = null
    var trainingJobId: StringFilter? = null
    var trainingJobObjective: StringFilter? = null
    var roundExecutionId: StringFilter? = null
    var runtimeNodeName: StringFilter? = null
    var cpuLoad: BigDecimalFilter? = null
    var gpuLoad: BigDecimalFilter? = null
    var memoryLoad: BigDecimalFilter? = null
    var lastHeartbeatAt: RangeFilter<LocalDateTime>? = null
    var heartbeatMissingBeyondThreshold: BooleanFilter? = null
    var heartbeatObservedAfterOffline: BooleanFilter? = null
    var resourcePressureDetected: BooleanFilter? = null
    var telemetryRetentionPolicy: StringFilter? = null
}


class RuntimeTelemetryLatestReadModelProjection : MetadataProjection {
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var federationName: String? = null
    var trainingJobId: UUID? = null
    var trainingJobObjective: String? = null
    var roundExecutionId: UUID? = null
    var runtimeNodeName: String? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var heartbeatMissingBeyondThreshold: Boolean? = null
    var heartbeatObservedAfterOffline: Boolean? = null
    var resourcePressureDetected: Boolean? = null
    var telemetryRetentionPolicy: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeTelemetryLatestReadModelProjection.toReadModel(): RuntimeTelemetryLatestReadModel =
    RuntimeTelemetryLatestReadModel(
    nodeId = nodeId,
    runtimeAgentId = runtimeAgentId,
    federationId = federationId,
    federationName = federationName,
    trainingJobId = trainingJobId,
    trainingJobObjective = trainingJobObjective,
    roundExecutionId = roundExecutionId,
    runtimeNodeName = runtimeNodeName,
    cpuLoad = cpuLoad,
    gpuLoad = gpuLoad,
    memoryLoad = memoryLoad,
    lastHeartbeatAt = lastHeartbeatAt,
    heartbeatMissingBeyondThreshold = heartbeatMissingBeyondThreshold,
    heartbeatObservedAfterOffline = heartbeatObservedAfterOffline,
    resourcePressureDetected = resourcePressureDetected,
    telemetryRetentionPolicy = telemetryRetentionPolicy,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeTelemetryLatestReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeTelemetryLatestReadModel>
    fun findAllByCriteria(criteria: RuntimeTelemetryLatestReadModelCriteria?, pageable: Pageable): Page<RuntimeTelemetryLatestReadModel>
    fun findById(id: UUID): RuntimeTelemetryLatestReadModel?
    fun findProjectionById(id: UUID): RuntimeTelemetryLatestReadModelProjection?
    fun save(projection: RuntimeTelemetryLatestReadModelProjection)
}

data class RuntimeTelemetryLatestReadModel(
    val nodeId: UUID?,
    val runtimeAgentId: UUID?,
    val federationId: UUID?,
    val federationName: String?,
    val trainingJobId: UUID?,
    val trainingJobObjective: String?,
    val roundExecutionId: UUID?,
    val runtimeNodeName: String?,
    val cpuLoad: BigDecimal?,
    val gpuLoad: BigDecimal?,
    val memoryLoad: BigDecimal?,
    val lastHeartbeatAt: LocalDateTime?,
    val heartbeatMissingBeyondThreshold: Boolean?,
    val heartbeatObservedAfterOffline: Boolean?,
    val resourcePressureDetected: Boolean?,
    val telemetryRetentionPolicy: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
