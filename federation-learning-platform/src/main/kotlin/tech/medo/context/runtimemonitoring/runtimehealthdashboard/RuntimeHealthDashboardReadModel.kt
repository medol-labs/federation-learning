package tech.medo.runtimemonitoring.runtimehealthdashboard

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
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeHealthDashboardReadModelQuery

class RuntimeHealthDashboardReadModelCriteria {
    var nodeId: StringFilter? = null
    var runtimeAgentId: StringFilter? = null
    var federationId: StringFilter? = null
    var trainingJobId: StringFilter? = null
    var roundExecutionId: StringFilter? = null
    var federationName: StringFilter? = null
    var trainingJobObjective: StringFilter? = null
    var cpuLoad: BigDecimalFilter? = null
    var gpuLoad: BigDecimalFilter? = null
    var memoryLoad: BigDecimalFilter? = null
    var nodeReady: BooleanFilter? = null
    var availableCpuCores: IntegerFilter? = null
    var availableMemoryGb: IntegerFilter? = null
    var availableGpuCount: IntegerFilter? = null
    var runningWorkloadCount: IntegerFilter? = null
    var workloadCapacity: IntegerFilter? = null
    var healthStatus: StringFilter? = null
    var lastHeartbeatAt: RangeFilter<LocalDateTime>? = null
    var lastResourceSnapshotAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeHealthDashboardReadModelProjection : MetadataProjection {
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var federationId: UUID? = null
    var trainingJobId: UUID? = null
    var roundExecutionId: UUID? = null
    var federationName: String? = null
    var trainingJobObjective: String? = null
    var cpuLoad: BigDecimal? = null
    var gpuLoad: BigDecimal? = null
    var memoryLoad: BigDecimal? = null
    var nodeReady: Boolean? = null
    var availableCpuCores: Int? = null
    var availableMemoryGb: Int? = null
    var availableGpuCount: Int? = null
    var runningWorkloadCount: Int? = null
    var workloadCapacity: Int? = null
    var healthStatus: String? = null
    var lastHeartbeatAt: LocalDateTime? = null
    var lastResourceSnapshotAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeHealthDashboardReadModelProjection.toReadModel(): RuntimeHealthDashboardReadModel =
    RuntimeHealthDashboardReadModel(
    nodeId = nodeId,
    runtimeAgentId = runtimeAgentId,
    federationId = federationId,
    trainingJobId = trainingJobId,
    roundExecutionId = roundExecutionId,
    federationName = federationName,
    trainingJobObjective = trainingJobObjective,
    cpuLoad = cpuLoad,
    gpuLoad = gpuLoad,
    memoryLoad = memoryLoad,
    nodeReady = nodeReady,
    availableCpuCores = availableCpuCores,
    availableMemoryGb = availableMemoryGb,
    availableGpuCount = availableGpuCount,
    runningWorkloadCount = runningWorkloadCount,
    workloadCapacity = workloadCapacity,
    healthStatus = healthStatus,
    lastHeartbeatAt = lastHeartbeatAt,
    lastResourceSnapshotAt = lastResourceSnapshotAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeHealthDashboardReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeHealthDashboardReadModel>
    fun findAllByCriteria(criteria: RuntimeHealthDashboardReadModelCriteria?, pageable: Pageable): Page<RuntimeHealthDashboardReadModel>
    fun findById(id: UUID): RuntimeHealthDashboardReadModel?
    fun findProjectionById(id: UUID): RuntimeHealthDashboardReadModelProjection?
    fun save(projection: RuntimeHealthDashboardReadModelProjection)
}

data class RuntimeHealthDashboardReadModel(
    val nodeId: UUID?,
    val runtimeAgentId: UUID?,
    val federationId: UUID?,
    val trainingJobId: UUID?,
    val roundExecutionId: UUID?,
    val federationName: String?,
    val trainingJobObjective: String?,
    val cpuLoad: BigDecimal?,
    val gpuLoad: BigDecimal?,
    val memoryLoad: BigDecimal?,
    val nodeReady: Boolean?,
    val availableCpuCores: Int?,
    val availableMemoryGb: Int?,
    val availableGpuCount: Int?,
    val runningWorkloadCount: Int?,
    val workloadCapacity: Int?,
    val healthStatus: String?,
    val lastHeartbeatAt: LocalDateTime?,
    val lastResourceSnapshotAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
