package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimetelemetrylatestreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.runtimeagentoperations.agentruntimetelemetrylatest.AgentRuntimeTelemetryLatestReadModel
import tech.medo.runtimeagentoperations.agentruntimetelemetrylatest.AgentRuntimeTelemetryLatestReadModelProjection
import tech.medo.runtimeagentoperations.agentruntimetelemetrylatest.AgentRuntimeTelemetryLatestReadModelRepository
import tech.medo.runtimeagentoperations.agentruntimetelemetrylatest.toReadModel

@Repository
class JpaAgentRuntimeTelemetryLatestReadModelRepository(private val jpaRepository: SpringDataAgentRuntimeTelemetryLatestReadModelRepository) : AgentRuntimeTelemetryLatestReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AgentRuntimeTelemetryLatestReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): AgentRuntimeTelemetryLatestReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentRuntimeTelemetryLatestReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentRuntimeTelemetryLatestReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun AgentRuntimeTelemetryLatestReadModelEntity.toProjection(): AgentRuntimeTelemetryLatestReadModelProjection =
        AgentRuntimeTelemetryLatestReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.federationId = this@toProjection.federationId
            it.trainingJobId = this@toProjection.trainingJobId
            it.roundExecutionId = this@toProjection.roundExecutionId
            it.cpuLoad = this@toProjection.cpuLoad
            it.gpuLoad = this@toProjection.gpuLoad
            it.memoryLoad = this@toProjection.memoryLoad
            it.lastHeartbeatAt = this@toProjection.lastHeartbeatAt
            it.telemetryRetentionPolicy = this@toProjection.telemetryRetentionPolicy
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentRuntimeTelemetryLatestReadModelProjection.toEntity(): AgentRuntimeTelemetryLatestReadModelEntity =
        AgentRuntimeTelemetryLatestReadModelEntity().also {
            it.nodeId = this@toEntity.nodeId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.federationId = this@toEntity.federationId
            it.trainingJobId = this@toEntity.trainingJobId
            it.roundExecutionId = this@toEntity.roundExecutionId
            it.cpuLoad = this@toEntity.cpuLoad
            it.gpuLoad = this@toEntity.gpuLoad
            it.memoryLoad = this@toEntity.memoryLoad
            it.lastHeartbeatAt = this@toEntity.lastHeartbeatAt
            it.telemetryRetentionPolicy = this@toEntity.telemetryRetentionPolicy
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
