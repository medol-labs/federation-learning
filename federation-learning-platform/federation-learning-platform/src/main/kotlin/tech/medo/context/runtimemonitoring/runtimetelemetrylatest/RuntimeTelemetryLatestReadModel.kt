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
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
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
    var lastRecoveredAt: RangeFilter<LocalDateTime>? = null
    var offlineDetectionPending: BooleanFilter? = null
    var recoveryDetectionPending: BooleanFilter? = null
    var resourcePressureDetectionPending: BooleanFilter? = null
    var offlineReason: StringFilter? = null
    var recoveryReason: StringFilter? = null
    var pressureType: StringFilter? = null
    var observedValue: BigDecimalFilter? = null
    var thresholdValue: BigDecimalFilter? = null
    var alertSeverity: StringFilter? = null
    var alertMessage: StringFilter? = null
    var healthStatus: StringFilter? = null
    var telemetryRetentionPolicy: StringFilter? = null
}


class RuntimeTelemetryLatestReadModelProjection : MetadataProjection {
    override var projectionUpdatedAt: LocalDateTime? = null
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
    var lastRecoveredAt: LocalDateTime? = null
    var offlineDetectionPending: Boolean? = null
    var recoveryDetectionPending: Boolean? = null
    var resourcePressureDetectionPending: Boolean? = null
    var offlineReason: String? = null
    var recoveryReason: String? = null
    var pressureType: String? = null
    var observedValue: BigDecimal? = null
    var thresholdValue: BigDecimal? = null
    var alertSeverity: String? = null
    var alertMessage: String? = null
    var healthStatus: String? = null
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
    lastRecoveredAt = lastRecoveredAt,
    offlineDetectionPending = offlineDetectionPending,
    recoveryDetectionPending = recoveryDetectionPending,
    resourcePressureDetectionPending = resourcePressureDetectionPending,
    offlineReason = offlineReason,
    recoveryReason = recoveryReason,
    pressureType = pressureType,
    observedValue = observedValue,
    thresholdValue = thresholdValue,
    alertSeverity = alertSeverity,
    alertMessage = alertMessage,
    healthStatus = healthStatus,
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
    val lastRecoveredAt: LocalDateTime?,
    val offlineDetectionPending: Boolean?,
    val recoveryDetectionPending: Boolean?,
    val resourcePressureDetectionPending: Boolean?,
    val offlineReason: String?,
    val recoveryReason: String?,
    val pressureType: String?,
    val observedValue: BigDecimal?,
    val thresholdValue: BigDecimal?,
    val alertSeverity: String?,
    val alertMessage: String?,
    val healthStatus: String?,
    val telemetryRetentionPolicy: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
