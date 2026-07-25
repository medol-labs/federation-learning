package tech.medo.datasetgovernance.infrastructure.secondary.persistence.runtimedatasetmetadatacatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModel
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelProjection
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelRepository
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.toReadModel

@Repository
class JpaRuntimeDatasetMetadataCatalogReadModelRepository(private val jpaRepository: SpringDataRuntimeDatasetMetadataCatalogReadModelRepository) : RuntimeDatasetMetadataCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeDatasetMetadataCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeDatasetMetadataCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeDatasetMetadataCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeDatasetMetadataCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeDatasetMetadataCatalogReadModelEntity.toProjection(): RuntimeDatasetMetadataCatalogReadModelProjection =
        RuntimeDatasetMetadataCatalogReadModelProjection().also {
            it.metadataReportId = this@toProjection.metadataReportId
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.datasetName = this@toProjection.datasetName
            it.organizationName = this@toProjection.organizationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.sampleCount = this@toProjection.sampleCount
            it.featureCount = this@toProjection.featureCount
            it.schemaCompatible = this@toProjection.schemaCompatible
            it.labelCompatible = this@toProjection.labelCompatible
            it.missingValueRate = this@toProjection.missingValueRate
            it.duplicateRate = this@toProjection.duplicateRate
            it.qualityScore = this@toProjection.qualityScore
            it.nonIidScore = this@toProjection.nonIidScore
            it.classBalanceScore = this@toProjection.classBalanceScore
            it.profilingStatus = this@toProjection.profilingStatus
            it.failureReason = this@toProjection.failureReason
            it.profiledAt = this@toProjection.profiledAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeDatasetMetadataCatalogReadModelProjection.toEntity(): RuntimeDatasetMetadataCatalogReadModelEntity =
        RuntimeDatasetMetadataCatalogReadModelEntity().also {
            it.metadataReportId = this@toEntity.metadataReportId
            it.datasetId = this@toEntity.datasetId
            it.organizationId = this@toEntity.organizationId
            it.runtimeId = this@toEntity.runtimeId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.datasetName = this@toEntity.datasetName
            it.organizationName = this@toEntity.organizationName
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.sampleCount = this@toEntity.sampleCount
            it.featureCount = this@toEntity.featureCount
            it.schemaCompatible = this@toEntity.schemaCompatible
            it.labelCompatible = this@toEntity.labelCompatible
            it.missingValueRate = this@toEntity.missingValueRate
            it.duplicateRate = this@toEntity.duplicateRate
            it.qualityScore = this@toEntity.qualityScore
            it.nonIidScore = this@toEntity.nonIidScore
            it.classBalanceScore = this@toEntity.classBalanceScore
            it.profilingStatus = this@toEntity.profilingStatus
            it.failureReason = this@toEntity.failureReason
            it.profiledAt = this@toEntity.profiledAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
