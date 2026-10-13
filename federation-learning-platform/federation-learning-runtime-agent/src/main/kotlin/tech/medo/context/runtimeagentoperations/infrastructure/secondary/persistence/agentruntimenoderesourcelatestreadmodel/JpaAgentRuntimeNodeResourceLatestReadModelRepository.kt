package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimenoderesourcelatestreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.AgentRuntimeNodeResourceLatestReadModel
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.AgentRuntimeNodeResourceLatestReadModelCriteria
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.AgentRuntimeNodeResourceLatestReadModelProjection
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.AgentRuntimeNodeResourceLatestReadModelRepository
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.toReadModel

@Repository
class JpaAgentRuntimeNodeResourceLatestReadModelRepository(
    private val jpaRepository: SpringDataAgentRuntimeNodeResourceLatestReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: AgentRuntimeNodeResourceLatestReadModelQueryService
) : AgentRuntimeNodeResourceLatestReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AgentRuntimeNodeResourceLatestReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: AgentRuntimeNodeResourceLatestReadModelCriteria?, pageable: Pageable): Page<AgentRuntimeNodeResourceLatestReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): AgentRuntimeNodeResourceLatestReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): AgentRuntimeNodeResourceLatestReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentRuntimeNodeResourceLatestReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentRuntimeNodeResourceLatestReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: UUID): Specification<AgentRuntimeNodeResourceLatestReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("nodeId"), id)
        }

    private fun accessSpecification(): Specification<AgentRuntimeNodeResourceLatestReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "RuntimeAgentOperations",
            readModelName = "AgentRuntimeNodeResourceLatest",
            policy = ReadModelAccessPolicy(
            name = null,
            fields = emptyList<String>()
        )
        )

    private fun AgentRuntimeNodeResourceLatestReadModelEntity.toProjection(): AgentRuntimeNodeResourceLatestReadModelProjection =
        AgentRuntimeNodeResourceLatestReadModelProjection().also {
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
            it.telemetryRetentionPolicy = this@toProjection.telemetryRetentionPolicy
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentRuntimeNodeResourceLatestReadModelProjection.toEntity(): AgentRuntimeNodeResourceLatestReadModelEntity =
        AgentRuntimeNodeResourceLatestReadModelEntity().also {
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
            it.telemetryRetentionPolicy = this@toEntity.telemetryRetentionPolicy
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
