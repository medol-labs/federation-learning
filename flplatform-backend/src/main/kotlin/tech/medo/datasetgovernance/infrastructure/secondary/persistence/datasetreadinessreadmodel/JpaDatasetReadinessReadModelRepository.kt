package tech.medo.datasetgovernance.infrastructure.secondary.persistence.datasetreadinessreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.datasetgovernance.datasetreadiness.DatasetReadinessReadModel
import tech.medo.datasetgovernance.datasetreadiness.DatasetReadinessReadModelProjection
import tech.medo.datasetgovernance.datasetreadiness.DatasetReadinessReadModelRepository
import tech.medo.datasetgovernance.datasetreadiness.toReadModel

@Repository
class JpaDatasetReadinessReadModelRepository(private val jpaRepository: SpringDataDatasetReadinessReadModelRepository) : DatasetReadinessReadModelRepository {
    override fun findAll(pageable: Pageable): Page<DatasetReadinessReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): DatasetReadinessReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): DatasetReadinessReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: DatasetReadinessReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun DatasetReadinessReadModelEntity.toProjection(): DatasetReadinessReadModelProjection =
        DatasetReadinessReadModelProjection().also {
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.datasetName = this@toProjection.datasetName
            it.organizationName = this@toProjection.organizationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.datasetUsage = this@toProjection.datasetUsage
            it.metadataStatus = this@toProjection.metadataStatus
            it.contractStatus = this@toProjection.contractStatus
            it.approvalStatus = this@toProjection.approvalStatus
            it.accessStatus = this@toProjection.accessStatus
            it.runtimeStatus = this@toProjection.runtimeStatus
            it.overallReadiness = this@toProjection.overallReadiness
            it.readyForTraining = this@toProjection.readyForTraining
            it.canBeSelectedForTraining = this@toProjection.canBeSelectedForTraining
            it.readinessScore = this@toProjection.readinessScore
            it.missingRequirements = this@toProjection.missingRequirements
            it.blockingReasons = this@toProjection.blockingReasons
            it.warnings = this@toProjection.warnings
            it.sampleCount = this@toProjection.sampleCount
            it.featureCount = this@toProjection.featureCount
            it.schemaCompatible = this@toProjection.schemaCompatible
            it.labelCompatible = this@toProjection.labelCompatible
            it.qualityScore = this@toProjection.qualityScore
            it.nonIidScore = this@toProjection.nonIidScore
            it.classBalanceScore = this@toProjection.classBalanceScore
            it.metadataReportId = this@toProjection.metadataReportId
            it.datasetAccessValidationId = this@toProjection.datasetAccessValidationId
            it.readable = this@toProjection.readable
            it.schemaReadable = this@toProjection.schemaReadable
            it.sampleBatchReadable = this@toProjection.sampleBatchReadable
            it.lastProfiledAt = this@toProjection.lastProfiledAt
            it.lastAccessValidatedAt = this@toProjection.lastAccessValidatedAt
            it.lastRuntimeHeartbeatAt = this@toProjection.lastRuntimeHeartbeatAt
            it.lastUpdatedAt = this@toProjection.lastUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun DatasetReadinessReadModelProjection.toEntity(): DatasetReadinessReadModelEntity =
        DatasetReadinessReadModelEntity().also {
            it.datasetId = this@toEntity.datasetId
            it.organizationId = this@toEntity.organizationId
            it.runtimeId = this@toEntity.runtimeId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.datasetName = this@toEntity.datasetName
            it.organizationName = this@toEntity.organizationName
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.datasetUsage = this@toEntity.datasetUsage
            it.metadataStatus = this@toEntity.metadataStatus
            it.contractStatus = this@toEntity.contractStatus
            it.approvalStatus = this@toEntity.approvalStatus
            it.accessStatus = this@toEntity.accessStatus
            it.runtimeStatus = this@toEntity.runtimeStatus
            it.overallReadiness = this@toEntity.overallReadiness
            it.readyForTraining = this@toEntity.readyForTraining
            it.canBeSelectedForTraining = this@toEntity.canBeSelectedForTraining
            it.readinessScore = this@toEntity.readinessScore
            it.missingRequirements = this@toEntity.missingRequirements
            it.blockingReasons = this@toEntity.blockingReasons
            it.warnings = this@toEntity.warnings
            it.sampleCount = this@toEntity.sampleCount
            it.featureCount = this@toEntity.featureCount
            it.schemaCompatible = this@toEntity.schemaCompatible
            it.labelCompatible = this@toEntity.labelCompatible
            it.qualityScore = this@toEntity.qualityScore
            it.nonIidScore = this@toEntity.nonIidScore
            it.classBalanceScore = this@toEntity.classBalanceScore
            it.metadataReportId = this@toEntity.metadataReportId
            it.datasetAccessValidationId = this@toEntity.datasetAccessValidationId
            it.readable = this@toEntity.readable
            it.schemaReadable = this@toEntity.schemaReadable
            it.sampleBatchReadable = this@toEntity.sampleBatchReadable
            it.lastProfiledAt = this@toEntity.lastProfiledAt
            it.lastAccessValidatedAt = this@toEntity.lastAccessValidatedAt
            it.lastRuntimeHeartbeatAt = this@toEntity.lastRuntimeHeartbeatAt
            it.lastUpdatedAt = this@toEntity.lastUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
