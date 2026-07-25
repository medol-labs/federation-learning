package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetcapabilityreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModel
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelProjection
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelRepository
import tech.medo.runtimeagentoperations.datasetcapability.toReadModel

@Repository
class JpaDatasetCapabilityReadModelRepository(private val jpaRepository: SpringDataDatasetCapabilityReadModelRepository) : DatasetCapabilityReadModelRepository {
    override fun findAll(pageable: Pageable): Page<DatasetCapabilityReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): DatasetCapabilityReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): DatasetCapabilityReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: DatasetCapabilityReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun DatasetCapabilityReadModelEntity.toProjection(): DatasetCapabilityReadModelProjection =
        DatasetCapabilityReadModelProjection().also {
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.organizationName = this@toProjection.organizationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.datasetName = this@toProjection.datasetName
            it.datasetUsage = this@toProjection.datasetUsage
            it.sampleCount = this@toProjection.sampleCount
            it.featureCount = this@toProjection.featureCount
            it.schemaCompatible = this@toProjection.schemaCompatible
            it.labelCompatible = this@toProjection.labelCompatible
            it.qualityScore = this@toProjection.qualityScore
            it.nonIidScore = this@toProjection.nonIidScore
            it.metadataReportId = this@toProjection.metadataReportId
            it.metadataStatus = this@toProjection.metadataStatus
            it.approvalStatus = this@toProjection.approvalStatus
            it.approved = this@toProjection.approved
            it.lastProfiledAt = this@toProjection.lastProfiledAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun DatasetCapabilityReadModelProjection.toEntity(): DatasetCapabilityReadModelEntity =
        DatasetCapabilityReadModelEntity().also {
            it.datasetId = this@toEntity.datasetId
            it.organizationId = this@toEntity.organizationId
            it.runtimeId = this@toEntity.runtimeId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.organizationName = this@toEntity.organizationName
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.datasetName = this@toEntity.datasetName
            it.datasetUsage = this@toEntity.datasetUsage
            it.sampleCount = this@toEntity.sampleCount
            it.featureCount = this@toEntity.featureCount
            it.schemaCompatible = this@toEntity.schemaCompatible
            it.labelCompatible = this@toEntity.labelCompatible
            it.qualityScore = this@toEntity.qualityScore
            it.nonIidScore = this@toEntity.nonIidScore
            it.metadataReportId = this@toEntity.metadataReportId
            it.metadataStatus = this@toEntity.metadataStatus
            it.approvalStatus = this@toEntity.approvalStatus
            it.approved = this@toEntity.approved
            it.lastProfiledAt = this@toEntity.lastProfiledAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
