package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.modellifecycle.modelcatalog.ModelCatalogReadModel
import tech.medo.modellifecycle.modelcatalog.ModelCatalogReadModelProjection
import tech.medo.modellifecycle.modelcatalog.ModelCatalogReadModelRepository
import tech.medo.modellifecycle.modelcatalog.toReadModel

@Repository
class JpaModelCatalogReadModelRepository(private val jpaRepository: SpringDataModelCatalogReadModelRepository) : ModelCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<ModelCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): ModelCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): ModelCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: ModelCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun ModelCatalogReadModelEntity.toProjection(): ModelCatalogReadModelProjection =
        ModelCatalogReadModelProjection().also {
            it.modelId = this@toProjection.modelId
            it.trainingJobId = this@toProjection.trainingJobId
            it.finalRoundId = this@toProjection.finalRoundId
            it.modelArtifactId = this@toProjection.modelArtifactId
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.modelArtifactDigest = this@toProjection.modelArtifactDigest
            it.evaluationReportId = this@toProjection.evaluationReportId
            it.finalGlobalAccuracy = this@toProjection.finalGlobalAccuracy
            it.state = this@toProjection.state
            it.releaseChannel = this@toProjection.releaseChannel
            it.productionStage = this@toProjection.productionStage
            it.previousModelId = this@toProjection.previousModelId
            it.experimentId = this@toProjection.experimentId
            it.hyperparameterSnapshotId = this@toProjection.hyperparameterSnapshotId
            it.reproducibilityManifestId = this@toProjection.reproducibilityManifestId
            it.modelCardId = this@toProjection.modelCardId
            it.baselineModelId = this@toProjection.baselineModelId
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

    private fun ModelCatalogReadModelProjection.toEntity(): ModelCatalogReadModelEntity =
        ModelCatalogReadModelEntity().also {
            it.modelId = this@toEntity.modelId
            it.trainingJobId = this@toEntity.trainingJobId
            it.finalRoundId = this@toEntity.finalRoundId
            it.modelArtifactId = this@toEntity.modelArtifactId
            it.trainingJobObjective = this@toEntity.trainingJobObjective
            it.modelArtifactDigest = this@toEntity.modelArtifactDigest
            it.evaluationReportId = this@toEntity.evaluationReportId
            it.finalGlobalAccuracy = this@toEntity.finalGlobalAccuracy
            it.state = this@toEntity.state
            it.releaseChannel = this@toEntity.releaseChannel
            it.productionStage = this@toEntity.productionStage
            it.previousModelId = this@toEntity.previousModelId
            it.experimentId = this@toEntity.experimentId
            it.hyperparameterSnapshotId = this@toEntity.hyperparameterSnapshotId
            it.reproducibilityManifestId = this@toEntity.reproducibilityManifestId
            it.modelCardId = this@toEntity.modelCardId
            it.baselineModelId = this@toEntity.baselineModelId
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
