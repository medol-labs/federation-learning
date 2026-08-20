package tech.medo.runtimemonitoring.runtimenoderesourcelatest

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeNodeResourceLatestReadModelQuery

class RuntimeNodeResourceLatestReadModelCriteria {
    var nodeId: StringFilter? = null
    var runtimeAgentId: StringFilter? = null
    var runtimeInfrastructureId: StringFilter? = null
    var runtimeNodeName: StringFilter? = null
    var nodeReady: BooleanFilter? = null
    var allocatableCpuCores: IntegerFilter? = null
    var allocatableMemoryGb: IntegerFilter? = null
    var allocatableGpuCount: IntegerFilter? = null
    var allocatedCpuCores: IntegerFilter? = null
    var allocatedMemoryGb: IntegerFilter? = null
    var allocatedGpuCount: IntegerFilter? = null
    var availableCpuCores: IntegerFilter? = null
    var availableMemoryGb: IntegerFilter? = null
    var availableGpuCount: IntegerFilter? = null
    var runningWorkloadCount: IntegerFilter? = null
    var workloadCapacity: IntegerFilter? = null
    var observedAt: RangeFilter<LocalDateTime>? = null
    var allocatableCapacityChanged: BooleanFilter? = null
    var telemetryRetentionPolicy: StringFilter? = null
}


class RuntimeNodeResourceLatestReadModelProjection : MetadataProjection {
    var nodeId: UUID? = null
    var runtimeAgentId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeNodeName: String? = null
    var nodeReady: Boolean? = null
    var allocatableCpuCores: Int? = null
    var allocatableMemoryGb: Int? = null
    var allocatableGpuCount: Int? = null
    var allocatedCpuCores: Int? = null
    var allocatedMemoryGb: Int? = null
    var allocatedGpuCount: Int? = null
    var availableCpuCores: Int? = null
    var availableMemoryGb: Int? = null
    var availableGpuCount: Int? = null
    var runningWorkloadCount: Int? = null
    var workloadCapacity: Int? = null
    var observedAt: LocalDateTime? = null
    var allocatableCapacityChanged: Boolean? = null
    var telemetryRetentionPolicy: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeNodeResourceLatestReadModelProjection.toReadModel(): RuntimeNodeResourceLatestReadModel =
    RuntimeNodeResourceLatestReadModel(
    nodeId = nodeId,
    runtimeAgentId = runtimeAgentId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    runtimeNodeName = runtimeNodeName,
    nodeReady = nodeReady,
    allocatableCpuCores = allocatableCpuCores,
    allocatableMemoryGb = allocatableMemoryGb,
    allocatableGpuCount = allocatableGpuCount,
    allocatedCpuCores = allocatedCpuCores,
    allocatedMemoryGb = allocatedMemoryGb,
    allocatedGpuCount = allocatedGpuCount,
    availableCpuCores = availableCpuCores,
    availableMemoryGb = availableMemoryGb,
    availableGpuCount = availableGpuCount,
    runningWorkloadCount = runningWorkloadCount,
    workloadCapacity = workloadCapacity,
    observedAt = observedAt,
    allocatableCapacityChanged = allocatableCapacityChanged,
    telemetryRetentionPolicy = telemetryRetentionPolicy,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeNodeResourceLatestReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeNodeResourceLatestReadModel>
    fun findAllByCriteria(criteria: RuntimeNodeResourceLatestReadModelCriteria?, pageable: Pageable): Page<RuntimeNodeResourceLatestReadModel>
    fun findById(id: UUID): RuntimeNodeResourceLatestReadModel?
    fun findProjectionById(id: UUID): RuntimeNodeResourceLatestReadModelProjection?
    fun save(projection: RuntimeNodeResourceLatestReadModelProjection)
}

data class RuntimeNodeResourceLatestReadModel(
    val nodeId: UUID?,
    val runtimeAgentId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val runtimeNodeName: String?,
    val nodeReady: Boolean?,
    val allocatableCpuCores: Int?,
    val allocatableMemoryGb: Int?,
    val allocatableGpuCount: Int?,
    val allocatedCpuCores: Int?,
    val allocatedMemoryGb: Int?,
    val allocatedGpuCount: Int?,
    val availableCpuCores: Int?,
    val availableMemoryGb: Int?,
    val availableGpuCount: Int?,
    val runningWorkloadCount: Int?,
    val workloadCapacity: Int?,
    val observedAt: LocalDateTime?,
    val allocatableCapacityChanged: Boolean?,
    val telemetryRetentionPolicy: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
