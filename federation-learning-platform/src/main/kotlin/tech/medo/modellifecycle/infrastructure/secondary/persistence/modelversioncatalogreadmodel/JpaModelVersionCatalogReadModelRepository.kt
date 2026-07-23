package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelversioncatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.modellifecycle.modelversioncatalog.ModelVersionCatalogReadModel
import tech.medo.modellifecycle.modelversioncatalog.ModelVersionCatalogReadModelProjection
import tech.medo.modellifecycle.modelversioncatalog.ModelVersionCatalogReadModelRepository
import tech.medo.modellifecycle.modelversioncatalog.toReadModel

@Repository
class JpaModelVersionCatalogReadModelRepository(private val jpaRepository: SpringDataModelVersionCatalogReadModelRepository) : ModelVersionCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<ModelVersionCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): ModelVersionCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): ModelVersionCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: ModelVersionCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun ModelVersionCatalogReadModelEntity.toProjection(): ModelVersionCatalogReadModelProjection =
        ModelVersionCatalogReadModelProjection().also {
            it.modelVersionId = this@toProjection.modelVersionId
            it.trainingJobId = this@toProjection.trainingJobId
            it.finalRoundId = this@toProjection.finalRoundId
            it.modelArtifactId = this@toProjection.modelArtifactId
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.modelHash = this@toProjection.modelHash
            it.evaluationReportId = this@toProjection.evaluationReportId
            it.finalGlobalAccuracy = this@toProjection.finalGlobalAccuracy
            it.state = this@toProjection.state
            it.releaseChannel = this@toProjection.releaseChannel
            it.productionStage = this@toProjection.productionStage
            it.previousModelVersionId = this@toProjection.previousModelVersionId
            it.experimentId = this@toProjection.experimentId
            it.hyperparameterSnapshotId = this@toProjection.hyperparameterSnapshotId
            it.reproducibilityManifestId = this@toProjection.reproducibilityManifestId
            it.modelCardId = this@toProjection.modelCardId
            it.baselineModelVersionId = this@toProjection.baselineModelVersionId
            it.hasEvaluationPackage = this@toProjection.hasEvaluationPackage
            it.approvalStatus = this@toProjection.approvalStatus
            it.releaseStatus = this@toProjection.releaseStatus
            it.isProduction = this@toProjection.isProduction
            it.canRecordEvaluationPackage = this@toProjection.canRecordEvaluationPackage
            it.canApprove = this@toProjection.canApprove
            it.canPromoteToProduction = this@toProjection.canPromoteToProduction
            it.canRollback = this@toProjection.canRollback
            it.canRetire = this@toProjection.canRetire
            it.blockedReason = this@toProjection.blockedReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun ModelVersionCatalogReadModelProjection.toEntity(): ModelVersionCatalogReadModelEntity =
        ModelVersionCatalogReadModelEntity().also {
            it.modelVersionId = this@toEntity.modelVersionId
            it.trainingJobId = this@toEntity.trainingJobId
            it.finalRoundId = this@toEntity.finalRoundId
            it.modelArtifactId = this@toEntity.modelArtifactId
            it.trainingJobObjective = this@toEntity.trainingJobObjective
            it.modelHash = this@toEntity.modelHash
            it.evaluationReportId = this@toEntity.evaluationReportId
            it.finalGlobalAccuracy = this@toEntity.finalGlobalAccuracy
            it.state = this@toEntity.state
            it.releaseChannel = this@toEntity.releaseChannel
            it.productionStage = this@toEntity.productionStage
            it.previousModelVersionId = this@toEntity.previousModelVersionId
            it.experimentId = this@toEntity.experimentId
            it.hyperparameterSnapshotId = this@toEntity.hyperparameterSnapshotId
            it.reproducibilityManifestId = this@toEntity.reproducibilityManifestId
            it.modelCardId = this@toEntity.modelCardId
            it.baselineModelVersionId = this@toEntity.baselineModelVersionId
            it.hasEvaluationPackage = this@toEntity.hasEvaluationPackage
            it.approvalStatus = this@toEntity.approvalStatus
            it.releaseStatus = this@toEntity.releaseStatus
            it.isProduction = this@toEntity.isProduction
            it.canRecordEvaluationPackage = this@toEntity.canRecordEvaluationPackage
            it.canApprove = this@toEntity.canApprove
            it.canPromoteToProduction = this@toEntity.canPromoteToProduction
            it.canRollback = this@toEntity.canRollback
            it.canRetire = this@toEntity.canRetire
            it.blockedReason = this@toEntity.blockedReason
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
