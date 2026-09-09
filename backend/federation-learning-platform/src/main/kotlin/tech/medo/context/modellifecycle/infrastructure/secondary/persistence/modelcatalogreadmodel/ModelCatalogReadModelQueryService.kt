package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import tech.jhipster.service.QueryService
import tech.jhipster.service.filter.RangeFilter
import java.util.function.Function
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.modellifecycle.domain.states.ModelStateEnum;

import tech.medo.modellifecycle.modelcatalog.ModelCatalogReadModel
import tech.medo.modellifecycle.modelcatalog.ModelCatalogReadModelCriteria
import tech.medo.modellifecycle.modelcatalog.ModelCatalogReadModelProjection
import tech.medo.modellifecycle.modelcatalog.toReadModel

@Service
class ModelCatalogReadModelQueryService(
    private val repository: SpringDataModelCatalogReadModelRepository
) : QueryService<ModelCatalogReadModelEntity>() {
    fun findByCriteria(criteria: ModelCatalogReadModelCriteria?, pageable: Pageable): Page<ModelCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: ModelCatalogReadModelCriteria?): Specification<ModelCatalogReadModelEntity> {
        var specification = Specification.where<ModelCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.modelId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("modelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.finalRoundId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("finalRoundId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.modelArtifactId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("modelArtifactId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobObjective?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("trainingJobObjective") })) }
            criteria.modelArtifactDigest?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelArtifactDigest") })) }
            criteria.evaluationReportId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("evaluationReportId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.finalGlobalAccuracy?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("finalGlobalAccuracy") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<ModelStateEnum>> { root -> root.get("state") })) }
            criteria.releaseChannel?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("releaseChannel") })) }
            criteria.productionStage?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("productionStage") })) }
            criteria.previousModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("previousModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.experimentId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("experimentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.hyperparameterSnapshotId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("hyperparameterSnapshotId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.reproducibilityManifestId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("reproducibilityManifestId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.modelCardId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("modelCardId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.baselineModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("baselineModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.hasEvaluationPackage?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("hasEvaluationPackage") })) }
            criteria.approvalStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("approvalStatus") })) }
            criteria.releaseStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("releaseStatus") })) }
            criteria.isProduction?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("isProduction") })) }
            criteria.canRecordEvaluationPackage?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("canRecordEvaluationPackage") })) }
            criteria.canApprove?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("canApprove") })) }
            criteria.canPromoteToProduction?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("canPromoteToProduction") })) }
            criteria.canRollback?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("canRollback") })) }
            criteria.canRetire?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("canRetire") })) }
            criteria.blockedReason?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("blockedReason") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<ModelCatalogReadModelEntity>, Expression<X>>
    ): Specification<ModelCatalogReadModelEntity> =
        Specification { root, _, builder ->
            val expression = field.apply(root)
            val predicates = mutableListOf<jakarta.persistence.criteria.Predicate>()
            filter.getEquals()?.let { predicates.add(builder.equal(expression, it)) }
            filter.getNotEquals()?.let { predicates.add(builder.notEqual(expression, it)) }
            filter.getSpecified()?.let { predicates.add(if (it) builder.isNotNull(expression) else builder.isNull(expression)) }
            filter.getIn()?.takeIf { it.isNotEmpty() }?.let { predicates.add(expression.`in`(it)) }
            filter.getNotIn()?.takeIf { it.isNotEmpty() }?.let { predicates.add(builder.not(expression.`in`(it))) }
            filter.getGreaterThan()?.let { predicates.add(builder.greaterThan(expression, it)) }
            filter.getGreaterThanOrEqual()?.let { predicates.add(builder.greaterThanOrEqualTo(expression, it)) }
            filter.getLessThan()?.let { predicates.add(builder.lessThan(expression, it)) }
            filter.getLessThanOrEqual()?.let { predicates.add(builder.lessThanOrEqualTo(expression, it)) }
            builder.and(*predicates.toTypedArray())
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
}
