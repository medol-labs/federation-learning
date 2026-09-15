package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentfeatureschemacatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.AgentFeatureSchemaCatalogReadModel
import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.AgentFeatureSchemaCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.AgentFeatureSchemaCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.AgentFeatureSchemaCatalogReadModelRepository
import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.toReadModel

@Repository
class JpaAgentFeatureSchemaCatalogReadModelRepository(
    private val jpaRepository: SpringDataAgentFeatureSchemaCatalogReadModelRepository,
    private val queryService: AgentFeatureSchemaCatalogReadModelQueryService
) : AgentFeatureSchemaCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AgentFeatureSchemaCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: AgentFeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<AgentFeatureSchemaCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): AgentFeatureSchemaCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentFeatureSchemaCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentFeatureSchemaCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun AgentFeatureSchemaCatalogReadModelEntity.toProjection(): AgentFeatureSchemaCatalogReadModelProjection =
        AgentFeatureSchemaCatalogReadModelProjection().also {
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.schemaStatus = this@toProjection.schemaStatus
            it.syncedAt = this@toProjection.syncedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentFeatureSchemaCatalogReadModelProjection.toEntity(): AgentFeatureSchemaCatalogReadModelEntity =
        AgentFeatureSchemaCatalogReadModelEntity().also {
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.schemaStatus = this@toEntity.schemaStatus
            it.syncedAt = this@toEntity.syncedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
