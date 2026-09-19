package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimedatasetbindingcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.toReadModel

@Repository
class JpaRuntimeDatasetBindingCatalogReadModelRepository(
    private val jpaRepository: SpringDataRuntimeDatasetBindingCatalogReadModelRepository,
    private val queryService: RuntimeDatasetBindingCatalogReadModelQueryService
) : RuntimeDatasetBindingCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeDatasetBindingCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RuntimeDatasetBindingCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeDatasetBindingCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeDatasetBindingCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeDatasetBindingCatalogReadModelEntity.toProjection(): RuntimeDatasetBindingCatalogReadModelProjection =
        RuntimeDatasetBindingCatalogReadModelProjection().also {
            it.runtimeDatasetBindingId = this@toProjection.runtimeDatasetBindingId
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.organizationName = this@toProjection.organizationName
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.datasetName = this@toProjection.datasetName
            it.runtimeName = this@toProjection.runtimeName
            it.filePath = this@toProjection.filePath
            it.dataFormat = this@toProjection.dataFormat
            it.configuredAt = this@toProjection.configuredAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeDatasetBindingCatalogReadModelProjection.toEntity(): RuntimeDatasetBindingCatalogReadModelEntity =
        RuntimeDatasetBindingCatalogReadModelEntity().also {
            it.runtimeDatasetBindingId = this@toEntity.runtimeDatasetBindingId
            it.datasetId = this@toEntity.datasetId
            it.organizationId = this@toEntity.organizationId
            it.runtimeId = this@toEntity.runtimeId
            it.organizationName = this@toEntity.organizationName
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.datasetName = this@toEntity.datasetName
            it.runtimeName = this@toEntity.runtimeName
            it.filePath = this@toEntity.filePath
            it.dataFormat = this@toEntity.dataFormat
            it.configuredAt = this@toEntity.configuredAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
