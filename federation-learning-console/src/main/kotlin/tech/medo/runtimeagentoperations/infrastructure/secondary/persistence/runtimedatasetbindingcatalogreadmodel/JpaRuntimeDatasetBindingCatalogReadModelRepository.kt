package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimedatasetbindingcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.toReadModel

@Repository
class JpaRuntimeDatasetBindingCatalogReadModelRepository(private val jpaRepository: SpringDataRuntimeDatasetBindingCatalogReadModelRepository) : RuntimeDatasetBindingCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

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
            it.datasetName = this@toProjection.datasetName
            it.dataSourceType = this@toProjection.dataSourceType
            it.host = this@toProjection.host
            it.port = this@toProjection.port
            it.url = this@toProjection.url
            it.databaseName = this@toProjection.databaseName
            it.schemaName = this@toProjection.schemaName
            it.tableName = this@toProjection.tableName
            it.filePath = this@toProjection.filePath
            it.objectBucket = this@toProjection.objectBucket
            it.objectPrefix = this@toProjection.objectPrefix
            it.dataFormat = this@toProjection.dataFormat
            it.credentialSecretName = this@toProjection.credentialSecretName
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
            it.datasetName = this@toEntity.datasetName
            it.dataSourceType = this@toEntity.dataSourceType
            it.host = this@toEntity.host
            it.port = this@toEntity.port
            it.url = this@toEntity.url
            it.databaseName = this@toEntity.databaseName
            it.schemaName = this@toEntity.schemaName
            it.tableName = this@toEntity.tableName
            it.filePath = this@toEntity.filePath
            it.objectBucket = this@toEntity.objectBucket
            it.objectPrefix = this@toEntity.objectPrefix
            it.dataFormat = this@toEntity.dataFormat
            it.credentialSecretName = this@toEntity.credentialSecretName
            it.configuredAt = this@toEntity.configuredAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
