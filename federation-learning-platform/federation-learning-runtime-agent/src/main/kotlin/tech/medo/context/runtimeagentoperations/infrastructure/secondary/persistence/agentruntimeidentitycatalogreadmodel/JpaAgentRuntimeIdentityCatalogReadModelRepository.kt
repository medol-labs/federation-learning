package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimeidentitycatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.AgentRuntimeIdentityCatalogReadModel
import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.AgentRuntimeIdentityCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.AgentRuntimeIdentityCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.AgentRuntimeIdentityCatalogReadModelRepository
import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.toReadModel

@Repository
class JpaAgentRuntimeIdentityCatalogReadModelRepository(
    private val jpaRepository: SpringDataAgentRuntimeIdentityCatalogReadModelRepository,
    private val queryService: AgentRuntimeIdentityCatalogReadModelQueryService
) : AgentRuntimeIdentityCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AgentRuntimeIdentityCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: AgentRuntimeIdentityCatalogReadModelCriteria?, pageable: Pageable): Page<AgentRuntimeIdentityCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): AgentRuntimeIdentityCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentRuntimeIdentityCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentRuntimeIdentityCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun AgentRuntimeIdentityCatalogReadModelEntity.toProjection(): AgentRuntimeIdentityCatalogReadModelProjection =
        AgentRuntimeIdentityCatalogReadModelProjection().also {
            it.runtimeId = this@toProjection.runtimeId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.identityStatus = this@toProjection.identityStatus
            it.activatedAt = this@toProjection.activatedAt
            it.revokedAt = this@toProjection.revokedAt
            it.syncedAt = this@toProjection.syncedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentRuntimeIdentityCatalogReadModelProjection.toEntity(): AgentRuntimeIdentityCatalogReadModelEntity =
        AgentRuntimeIdentityCatalogReadModelEntity().also {
            it.runtimeId = this@toEntity.runtimeId
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.organizationId = this@toEntity.organizationId
            it.organizationName = this@toEntity.organizationName
            it.runtimeName = this@toEntity.runtimeName
            it.identityStatus = this@toEntity.identityStatus
            it.activatedAt = this@toEntity.activatedAt
            it.revokedAt = this@toEntity.revokedAt
            it.syncedAt = this@toEntity.syncedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
