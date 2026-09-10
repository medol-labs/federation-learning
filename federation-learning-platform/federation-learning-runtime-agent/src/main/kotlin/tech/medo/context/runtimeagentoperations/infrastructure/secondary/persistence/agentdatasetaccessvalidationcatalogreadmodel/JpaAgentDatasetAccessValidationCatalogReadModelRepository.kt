package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdatasetaccessvalidationcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.AgentDatasetAccessValidationCatalogReadModel
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.AgentDatasetAccessValidationCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.AgentDatasetAccessValidationCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.AgentDatasetAccessValidationCatalogReadModelRepository
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.toReadModel

@Repository
class JpaAgentDatasetAccessValidationCatalogReadModelRepository(
    private val jpaRepository: SpringDataAgentDatasetAccessValidationCatalogReadModelRepository,
    private val queryService: AgentDatasetAccessValidationCatalogReadModelQueryService
) : AgentDatasetAccessValidationCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AgentDatasetAccessValidationCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: AgentDatasetAccessValidationCatalogReadModelCriteria?, pageable: Pageable): Page<AgentDatasetAccessValidationCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): AgentDatasetAccessValidationCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentDatasetAccessValidationCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentDatasetAccessValidationCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun AgentDatasetAccessValidationCatalogReadModelEntity.toProjection(): AgentDatasetAccessValidationCatalogReadModelProjection =
        AgentDatasetAccessValidationCatalogReadModelProjection().also {
            it.datasetAccessValidationId = this@toProjection.datasetAccessValidationId
            it.runtimeDatasetBindingId = this@toProjection.runtimeDatasetBindingId
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.runtimeId = this@toProjection.runtimeId
            it.datasetName = this@toProjection.datasetName
            it.runtimeName = this@toProjection.runtimeName
            it.readable = this@toProjection.readable
            it.schemaReadable = this@toProjection.schemaReadable
            it.sampleBatchReadable = this@toProjection.sampleBatchReadable
            it.validationStatus = this@toProjection.validationStatus
            it.failureReason = this@toProjection.failureReason
            it.validatedAt = this@toProjection.validatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentDatasetAccessValidationCatalogReadModelProjection.toEntity(): AgentDatasetAccessValidationCatalogReadModelEntity =
        AgentDatasetAccessValidationCatalogReadModelEntity().also {
            it.datasetAccessValidationId = this@toEntity.datasetAccessValidationId
            it.runtimeDatasetBindingId = this@toEntity.runtimeDatasetBindingId
            it.datasetId = this@toEntity.datasetId
            it.organizationId = this@toEntity.organizationId
            it.organizationName = this@toEntity.organizationName
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.runtimeId = this@toEntity.runtimeId
            it.datasetName = this@toEntity.datasetName
            it.runtimeName = this@toEntity.runtimeName
            it.readable = this@toEntity.readable
            it.schemaReadable = this@toEntity.schemaReadable
            it.sampleBatchReadable = this@toEntity.sampleBatchReadable
            it.validationStatus = this@toEntity.validationStatus
            it.failureReason = this@toEntity.failureReason
            it.validatedAt = this@toEntity.validatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
