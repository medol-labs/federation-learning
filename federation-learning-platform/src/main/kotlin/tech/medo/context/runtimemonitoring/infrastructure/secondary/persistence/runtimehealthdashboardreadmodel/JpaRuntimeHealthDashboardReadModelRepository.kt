package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimehealthdashboardreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimemonitoring.runtimehealthdashboard.RuntimeHealthDashboardReadModel
import tech.medo.runtimemonitoring.runtimehealthdashboard.RuntimeHealthDashboardReadModelCriteria
import tech.medo.runtimemonitoring.runtimehealthdashboard.RuntimeHealthDashboardReadModelProjection
import tech.medo.runtimemonitoring.runtimehealthdashboard.RuntimeHealthDashboardReadModelRepository
import tech.medo.runtimemonitoring.runtimehealthdashboard.toReadModel

@Repository
class JpaRuntimeHealthDashboardReadModelRepository(
    private val jpaRepository: SpringDataRuntimeHealthDashboardReadModelRepository,
    private val queryService: RuntimeHealthDashboardReadModelQueryService
) : RuntimeHealthDashboardReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeHealthDashboardReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeHealthDashboardReadModelCriteria?, pageable: Pageable): Page<RuntimeHealthDashboardReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeHealthDashboardReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeHealthDashboardReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeHealthDashboardReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeHealthDashboardReadModelEntity.toProjection(): RuntimeHealthDashboardReadModelProjection =
        RuntimeHealthDashboardReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.federationId = this@toProjection.federationId
            it.trainingJobId = this@toProjection.trainingJobId
            it.roundExecutionId = this@toProjection.roundExecutionId
            it.federationName = this@toProjection.federationName
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.cpuLoad = this@toProjection.cpuLoad
            it.gpuLoad = this@toProjection.gpuLoad
            it.memoryLoad = this@toProjection.memoryLoad
            it.nodeReady = this@toProjection.nodeReady
            it.availableCpuCores = this@toProjection.availableCpuCores
            it.availableMemoryGb = this@toProjection.availableMemoryGb
            it.availableGpuCount = this@toProjection.availableGpuCount
            it.runningWorkloadCount = this@toProjection.runningWorkloadCount
            it.workloadCapacity = this@toProjection.workloadCapacity
            it.healthStatus = this@toProjection.healthStatus
            it.lastHeartbeatAt = this@toProjection.lastHeartbeatAt
            it.lastResourceSnapshotAt = this@toProjection.lastResourceSnapshotAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeHealthDashboardReadModelProjection.toEntity(): RuntimeHealthDashboardReadModelEntity =
        RuntimeHealthDashboardReadModelEntity().also {
            it.nodeId = this@toEntity.nodeId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.federationId = this@toEntity.federationId
            it.trainingJobId = this@toEntity.trainingJobId
            it.roundExecutionId = this@toEntity.roundExecutionId
            it.federationName = this@toEntity.federationName
            it.trainingJobObjective = this@toEntity.trainingJobObjective
            it.cpuLoad = this@toEntity.cpuLoad
            it.gpuLoad = this@toEntity.gpuLoad
            it.memoryLoad = this@toEntity.memoryLoad
            it.nodeReady = this@toEntity.nodeReady
            it.availableCpuCores = this@toEntity.availableCpuCores
            it.availableMemoryGb = this@toEntity.availableMemoryGb
            it.availableGpuCount = this@toEntity.availableGpuCount
            it.runningWorkloadCount = this@toEntity.runningWorkloadCount
            it.workloadCapacity = this@toEntity.workloadCapacity
            it.healthStatus = this@toEntity.healthStatus
            it.lastHeartbeatAt = this@toEntity.lastHeartbeatAt
            it.lastResourceSnapshotAt = this@toEntity.lastResourceSnapshotAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
