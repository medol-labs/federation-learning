package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimenoderesourcelatestreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimemonitoring.runtimenoderesourcelatest.RuntimeNodeResourceLatestReadModel
import tech.medo.runtimemonitoring.runtimenoderesourcelatest.RuntimeNodeResourceLatestReadModelCriteria
import tech.medo.runtimemonitoring.runtimenoderesourcelatest.RuntimeNodeResourceLatestReadModelProjection
import tech.medo.runtimemonitoring.runtimenoderesourcelatest.RuntimeNodeResourceLatestReadModelRepository
import tech.medo.runtimemonitoring.runtimenoderesourcelatest.toReadModel

@Repository
class JpaRuntimeNodeResourceLatestReadModelRepository(
    private val jpaRepository: SpringDataRuntimeNodeResourceLatestReadModelRepository,
    private val queryService: RuntimeNodeResourceLatestReadModelQueryService
) : RuntimeNodeResourceLatestReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeNodeResourceLatestReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeNodeResourceLatestReadModelCriteria?, pageable: Pageable): Page<RuntimeNodeResourceLatestReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeNodeResourceLatestReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeNodeResourceLatestReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeNodeResourceLatestReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeNodeResourceLatestReadModelEntity.toProjection(): RuntimeNodeResourceLatestReadModelProjection =
        RuntimeNodeResourceLatestReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeNodeName = this@toProjection.runtimeNodeName
            it.nodeReady = this@toProjection.nodeReady
            it.allocatableCpuCores = this@toProjection.allocatableCpuCores
            it.allocatableMemoryGb = this@toProjection.allocatableMemoryGb
            it.allocatableGpuCount = this@toProjection.allocatableGpuCount
            it.allocatedCpuCores = this@toProjection.allocatedCpuCores
            it.allocatedMemoryGb = this@toProjection.allocatedMemoryGb
            it.allocatedGpuCount = this@toProjection.allocatedGpuCount
            it.availableCpuCores = this@toProjection.availableCpuCores
            it.availableMemoryGb = this@toProjection.availableMemoryGb
            it.availableGpuCount = this@toProjection.availableGpuCount
            it.runningWorkloadCount = this@toProjection.runningWorkloadCount
            it.workloadCapacity = this@toProjection.workloadCapacity
            it.observedAt = this@toProjection.observedAt
            it.allocatableCapacityChanged = this@toProjection.allocatableCapacityChanged
            it.telemetryRetentionPolicy = this@toProjection.telemetryRetentionPolicy
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeNodeResourceLatestReadModelProjection.toEntity(): RuntimeNodeResourceLatestReadModelEntity =
        RuntimeNodeResourceLatestReadModelEntity().also {
            it.nodeId = this@toEntity.nodeId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.runtimeNodeName = this@toEntity.runtimeNodeName
            it.nodeReady = this@toEntity.nodeReady
            it.allocatableCpuCores = this@toEntity.allocatableCpuCores
            it.allocatableMemoryGb = this@toEntity.allocatableMemoryGb
            it.allocatableGpuCount = this@toEntity.allocatableGpuCount
            it.allocatedCpuCores = this@toEntity.allocatedCpuCores
            it.allocatedMemoryGb = this@toEntity.allocatedMemoryGb
            it.allocatedGpuCount = this@toEntity.allocatedGpuCount
            it.availableCpuCores = this@toEntity.availableCpuCores
            it.availableMemoryGb = this@toEntity.availableMemoryGb
            it.availableGpuCount = this@toEntity.availableGpuCount
            it.runningWorkloadCount = this@toEntity.runningWorkloadCount
            it.workloadCapacity = this@toEntity.workloadCapacity
            it.observedAt = this@toEntity.observedAt
            it.allocatableCapacityChanged = this@toEntity.allocatableCapacityChanged
            it.telemetryRetentionPolicy = this@toEntity.telemetryRetentionPolicy
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
