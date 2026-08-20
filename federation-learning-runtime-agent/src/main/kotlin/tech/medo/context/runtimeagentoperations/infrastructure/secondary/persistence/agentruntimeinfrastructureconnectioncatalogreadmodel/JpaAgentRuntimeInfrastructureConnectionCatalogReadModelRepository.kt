package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimeinfrastructureconnectioncatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.AgentRuntimeInfrastructureConnectionCatalogReadModel
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.AgentRuntimeInfrastructureConnectionCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.AgentRuntimeInfrastructureConnectionCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.AgentRuntimeInfrastructureConnectionCatalogReadModelRepository
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.toReadModel

@Repository
class JpaAgentRuntimeInfrastructureConnectionCatalogReadModelRepository(
    private val jpaRepository: SpringDataAgentRuntimeInfrastructureConnectionCatalogReadModelRepository,
    private val queryService: AgentRuntimeInfrastructureConnectionCatalogReadModelQueryService
) : AgentRuntimeInfrastructureConnectionCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AgentRuntimeInfrastructureConnectionCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: AgentRuntimeInfrastructureConnectionCatalogReadModelCriteria?, pageable: Pageable): Page<AgentRuntimeInfrastructureConnectionCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): AgentRuntimeInfrastructureConnectionCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentRuntimeInfrastructureConnectionCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentRuntimeInfrastructureConnectionCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun AgentRuntimeInfrastructureConnectionCatalogReadModelEntity.toProjection(): AgentRuntimeInfrastructureConnectionCatalogReadModelProjection =
        AgentRuntimeInfrastructureConnectionCatalogReadModelProjection().also {
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimePlatformConnectionReady = this@toProjection.runtimePlatformConnectionReady
            it.platformApiReachable = this@toProjection.platformApiReachable
            it.agentAuthenticationSucceeded = this@toProjection.agentAuthenticationSucceeded
            it.controlChannelEstablished = this@toProjection.controlChannelEstablished
            it.heartbeatAccepted = this@toProjection.heartbeatAccepted
            it.connectedAt = this@toProjection.connectedAt
            it.connectionReportFailedAt = this@toProjection.connectionReportFailedAt
            it.connectionReportFailureReason = this@toProjection.connectionReportFailureReason
            it.connectionReportRetryable = this@toProjection.connectionReportRetryable
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentRuntimeInfrastructureConnectionCatalogReadModelProjection.toEntity(): AgentRuntimeInfrastructureConnectionCatalogReadModelEntity =
        AgentRuntimeInfrastructureConnectionCatalogReadModelEntity().also {
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.runtimePlatformConnectionReady = this@toEntity.runtimePlatformConnectionReady
            it.platformApiReachable = this@toEntity.platformApiReachable
            it.agentAuthenticationSucceeded = this@toEntity.agentAuthenticationSucceeded
            it.controlChannelEstablished = this@toEntity.controlChannelEstablished
            it.heartbeatAccepted = this@toEntity.heartbeatAccepted
            it.connectedAt = this@toEntity.connectedAt
            it.connectionReportFailedAt = this@toEntity.connectionReportFailedAt
            it.connectionReportFailureReason = this@toEntity.connectionReportFailureReason
            it.connectionReportRetryable = this@toEntity.connectionReportRetryable
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
