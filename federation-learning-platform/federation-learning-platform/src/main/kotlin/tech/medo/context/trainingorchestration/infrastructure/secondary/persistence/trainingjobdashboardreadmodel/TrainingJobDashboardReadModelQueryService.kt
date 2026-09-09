package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingjobdashboardreadmodel

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
import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum;
import java.math.BigDecimal;

import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModel
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelCriteria
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelProjection
import tech.medo.trainingorchestration.trainingjobdashboard.toReadModel

@Service
class TrainingJobDashboardReadModelQueryService(
    private val repository: SpringDataTrainingJobDashboardReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<TrainingJobDashboardReadModelEntity>() {
    fun findByCriteria(criteria: TrainingJobDashboardReadModelCriteria?, pageable: Pageable): Page<TrainingJobDashboardReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: TrainingJobDashboardReadModelCriteria?): Specification<TrainingJobDashboardReadModelEntity> {
        var specification = Specification.where<TrainingJobDashboardReadModelEntity>(null)
        if (criteria != null) {
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("federationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingRunConfigurationId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingRunConfigurationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("federationName") })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.objective?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("objective") })) }
            criteria.strategyName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("strategyName") })) }
            criteria.aggregationAlgorithm?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("aggregationAlgorithm") })) }
            criteria.secureAggregationRequired?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("secureAggregationRequired") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<TrainingJobStateEnum>> { root -> root.get("state") })) }
            criteria.workflowStage?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("workflowStage") })) }
            criteria.workflowStep?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Int>> { root -> root.get("workflowStep") })) }
            criteria.nextAction?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("nextAction") })) }
            criteria.blockedReason?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("blockedReason") })) }
            criteria.canSubmit?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("canSubmit") })) }
            criteria.canStartRound?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("canStartRound") })) }
            criteria.canPause?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("canPause") })) }
            criteria.canResume?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("canResume") })) }
            criteria.canCancel?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("canCancel") })) }
            criteria.canComplete?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("canComplete") })) }
            criteria.currentRoundNumber?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Int>> { root -> root.get("currentRoundNumber") })) }
            criteria.startedRuntimeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Int>> { root -> root.get("startedRuntimeCount") })) }
            criteria.minimumNodesPerRound?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Int>> { root -> root.get("minimumNodesPerRound") })) }
            criteria.maxRounds?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Int>> { root -> root.get("maxRounds") })) }
            criteria.roundProgressPercent?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<Int>> { root -> root.get("roundProgressPercent") })) }
            criteria.globalAccuracy?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<BigDecimal>> { root -> root.get("globalAccuracy") })) }
            criteria.finalModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("finalModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.stopReason?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingJobDashboardReadModelEntity>, Expression<String>> { root -> root.get("stopReason") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<TrainingJobDashboardReadModelEntity>, Expression<X>>
    ): Specification<TrainingJobDashboardReadModelEntity> =
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

    private fun TrainingJobDashboardReadModelEntity.toProjection(): TrainingJobDashboardReadModelProjection =
        TrainingJobDashboardReadModelProjection().also {
            it.trainingJobId = this@toProjection.trainingJobId
            it.federationId = this@toProjection.federationId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.federationName = this@toProjection.federationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.objective = this@toProjection.objective
            it.strategyName = this@toProjection.strategyName
            it.aggregationAlgorithm = this@toProjection.aggregationAlgorithm
            it.secureAggregationRequired = this@toProjection.secureAggregationRequired
            it.state = this@toProjection.state
            it.workflowStage = this@toProjection.workflowStage
            it.workflowStep = this@toProjection.workflowStep
            it.nextAction = this@toProjection.nextAction
            it.availableActions = this@toProjection.availableActions?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.blockedReason = this@toProjection.blockedReason
            it.canSubmit = this@toProjection.canSubmit
            it.canStartRound = this@toProjection.canStartRound
            it.canPause = this@toProjection.canPause
            it.canResume = this@toProjection.canResume
            it.canCancel = this@toProjection.canCancel
            it.canComplete = this@toProjection.canComplete
            it.currentRoundNumber = this@toProjection.currentRoundNumber
            it.startedRuntimeCount = this@toProjection.startedRuntimeCount
            it.minimumNodesPerRound = this@toProjection.minimumNodesPerRound
            it.maxRounds = this@toProjection.maxRounds
            it.roundProgressPercent = this@toProjection.roundProgressPercent
            it.globalAccuracy = this@toProjection.globalAccuracy
            it.finalModelId = this@toProjection.finalModelId
            it.stopReason = this@toProjection.stopReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
