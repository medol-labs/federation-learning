package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimetelemetrylatestreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModel
import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModelCriteria
import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModelProjection
import tech.medo.runtimemonitoring.runtimetelemetrylatest.RuntimeTelemetryLatestReadModelRepository
import tech.medo.runtimemonitoring.runtimetelemetrylatest.toReadModel

@Repository
class JpaRuntimeTelemetryLatestReadModelRepository(
    private val jpaRepository: SpringDataRuntimeTelemetryLatestReadModelRepository,
    private val queryService: RuntimeTelemetryLatestReadModelQueryService
) : RuntimeTelemetryLatestReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeTelemetryLatestReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeTelemetryLatestReadModelCriteria?, pageable: Pageable): Page<RuntimeTelemetryLatestReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeTelemetryLatestReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeTelemetryLatestReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeTelemetryLatestReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeTelemetryLatestReadModelEntity.toProjection(): RuntimeTelemetryLatestReadModelProjection =
        RuntimeTelemetryLatestReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.federationId = this@toProjection.federationId
            it.federationName = this@toProjection.federationName
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.roundExecutionId = this@toProjection.roundExecutionId
            it.runtimeNodeName = this@toProjection.runtimeNodeName
            it.cpuLoad = this@toProjection.cpuLoad
            it.gpuLoad = this@toProjection.gpuLoad
            it.memoryLoad = this@toProjection.memoryLoad
            it.lastHeartbeatAt = this@toProjection.lastHeartbeatAt
            it.heartbeatMissingBeyondThreshold = this@toProjection.heartbeatMissingBeyondThreshold
            it.heartbeatObservedAfterOffline = this@toProjection.heartbeatObservedAfterOffline
            it.resourcePressureDetected = this@toProjection.resourcePressureDetected
            it.telemetryRetentionPolicy = this@toProjection.telemetryRetentionPolicy
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeTelemetryLatestReadModelProjection.toEntity(): RuntimeTelemetryLatestReadModelEntity =
        RuntimeTelemetryLatestReadModelEntity().also {
            it.nodeId = this@toEntity.nodeId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.federationId = this@toEntity.federationId
            it.federationName = this@toEntity.federationName
            it.trainingJobId = this@toEntity.trainingJobId
            it.trainingJobObjective = this@toEntity.trainingJobObjective
            it.roundExecutionId = this@toEntity.roundExecutionId
            it.runtimeNodeName = this@toEntity.runtimeNodeName
            it.cpuLoad = this@toEntity.cpuLoad
            it.gpuLoad = this@toEntity.gpuLoad
            it.memoryLoad = this@toEntity.memoryLoad
            it.lastHeartbeatAt = this@toEntity.lastHeartbeatAt
            it.heartbeatMissingBeyondThreshold = this@toEntity.heartbeatMissingBeyondThreshold
            it.heartbeatObservedAfterOffline = this@toEntity.heartbeatObservedAfterOffline
            it.resourcePressureDetected = this@toEntity.resourcePressureDetected
            it.telemetryRetentionPolicy = this@toEntity.telemetryRetentionPolicy
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
