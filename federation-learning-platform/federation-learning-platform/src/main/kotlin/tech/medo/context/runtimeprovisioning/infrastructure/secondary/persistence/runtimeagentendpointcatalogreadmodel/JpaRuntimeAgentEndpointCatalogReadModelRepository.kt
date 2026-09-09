package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeagentendpointcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.toReadModel

@Repository
class JpaRuntimeAgentEndpointCatalogReadModelRepository(
    private val jpaRepository: SpringDataRuntimeAgentEndpointCatalogReadModelRepository,
    private val queryService: RuntimeAgentEndpointCatalogReadModelQueryService
) : RuntimeAgentEndpointCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeAgentEndpointCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeAgentEndpointCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeAgentEndpointCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeAgentEndpointCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeAgentEndpointCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeAgentEndpointCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeAgentEndpointCatalogReadModelEntity.toProjection(): RuntimeAgentEndpointCatalogReadModelProjection =
        RuntimeAgentEndpointCatalogReadModelProjection().also {
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeId = this@toProjection.runtimeId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.organizationId = this@toProjection.organizationId
            it.runtimeName = this@toProjection.runtimeName
            it.runtimeAgentEndpoint = this@toProjection.runtimeAgentEndpoint
            it.endpointScope = this@toProjection.endpointScope
            it.connectionStatus = this@toProjection.connectionStatus
            it.connectedAt = this@toProjection.connectedAt
            it.activatedAt = this@toProjection.activatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeAgentEndpointCatalogReadModelProjection.toEntity(): RuntimeAgentEndpointCatalogReadModelEntity =
        RuntimeAgentEndpointCatalogReadModelEntity().also {
            it.runtimeAgentId = this@toEntity.runtimeAgentId
            it.runtimeId = this@toEntity.runtimeId
            it.runtimeInfrastructureId = this@toEntity.runtimeInfrastructureId
            it.organizationId = this@toEntity.organizationId
            it.runtimeName = this@toEntity.runtimeName
            it.runtimeAgentEndpoint = this@toEntity.runtimeAgentEndpoint
            it.endpointScope = this@toEntity.endpointScope
            it.connectionStatus = this@toEntity.connectionStatus
            it.connectedAt = this@toEntity.connectedAt
            it.activatedAt = this@toEntity.activatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
