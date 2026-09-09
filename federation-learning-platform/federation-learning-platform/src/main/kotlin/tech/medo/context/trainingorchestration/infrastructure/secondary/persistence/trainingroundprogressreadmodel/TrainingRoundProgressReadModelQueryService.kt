package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingroundprogressreadmodel

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
import tech.medo.trainingorchestration.domain.types.TrainingRoundParticipant;
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModel
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelCriteria
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelProjection
import tech.medo.trainingorchestration.trainingroundprogress.toReadModel

@Service
class TrainingRoundProgressReadModelQueryService(
    private val repository: SpringDataTrainingRoundProgressReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<TrainingRoundProgressReadModelEntity>() {
    fun findByCriteria(criteria: TrainingRoundProgressReadModelCriteria?, pageable: Pageable): Page<TrainingRoundProgressReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: TrainingRoundProgressReadModelCriteria?): Specification<TrainingRoundProgressReadModelEntity> {
        var specification = Specification.where<TrainingRoundProgressReadModelEntity>(null)
        if (criteria != null) {
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingRunConfigurationId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingRunConfigurationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roundId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobObjective?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("trainingJobObjective") })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.roundNumber?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("roundNumber") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<TrainingRoundStateEnum>> { root -> root.get("state") })) }
            criteria.selectedOrganizationCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("selectedOrganizationCount") })) }
            criteria.selectedRuntimeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("selectedRuntimeCount") })) }
            criteria.targetRuntimeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("targetRuntimeCount") })) }
            criteria.executionPlanDispatchedCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("executionPlanDispatchedCount") })) }
            criteria.roundExecutionStartedCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("roundExecutionStartedCount") })) }
            criteria.submittedModelUpdateCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("submittedModelUpdateCount") })) }
            criteria.rejectedUpdateCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("rejectedUpdateCount") })) }
            criteria.acceptedModelUpdateCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("acceptedModelUpdateCount") })) }
            criteria.acceptedUpdateCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("acceptedUpdateCount") })) }
            criteria.pendingUpdateCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("pendingUpdateCount") })) }
            criteria.failedRoundExecutionCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("failedRoundExecutionCount") })) }
            criteria.completedRoundExecutionCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("completedRoundExecutionCount") })) }
            criteria.retriedRoundExecutionCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("retriedRoundExecutionCount") })) }
            criteria.failedRoundExecutionRetryCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("failedRoundExecutionRetryCount") })) }
            criteria.quorumMet?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Boolean>> { root -> root.get("quorumMet") })) }
            criteria.quorumStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("quorumStatus") })) }
            criteria.minimumNodesPerRound?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("minimumNodesPerRound") })) }
            criteria.aggregationReady?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Boolean>> { root -> root.get("aggregationReady") })) }
            criteria.secureAggregationRequired?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Boolean>> { root -> root.get("secureAggregationRequired") })) }
            criteria.secureAggregationStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("secureAggregationStatus") })) }
            criteria.evaluationComplete?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Boolean>> { root -> root.get("evaluationComplete") })) }
            criteria.progressPercent?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<Int>> { root -> root.get("progressPercent") })) }
            criteria.currentPhase?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("currentPhase") })) }
            criteria.nextAction?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("nextAction") })) }
            criteria.blockedReason?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("blockedReason") })) }
            criteria.delayedReason?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("delayedReason") })) }
            criteria.roundStartedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("roundStartedAt") })) }
            criteria.contributionDeadlineAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("contributionDeadlineAt") })) }
            criteria.aggregationStartedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("aggregationStartedAt") })) }
            criteria.evaluationSubmittedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("evaluationSubmittedAt") })) }
            criteria.completedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("completedAt") })) }
            criteria.failedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("failedAt") })) }
            criteria.baseModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("baseModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.aggregatedModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("aggregatedModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.globalAccuracy?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<BigDecimal>> { root -> root.get("globalAccuracy") })) }
            criteria.globalFairnessScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<BigDecimal>> { root -> root.get("globalFairnessScore") })) }
            criteria.failureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRoundProgressReadModelEntity>, Expression<String>> { root -> root.get("failureReason") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<TrainingRoundProgressReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<TrainingRoundProgressReadModelEntity> =
        Specification { root, _, builder ->
            val expression = field.apply(root)
            val predicates = mutableListOf<jakarta.persistence.criteria.Predicate>()
            filter.getEquals()?.let { predicates.add(builder.equal(expression, localDateTimeValue(it))) }
            filter.getNotEquals()?.let { predicates.add(builder.notEqual(expression, localDateTimeValue(it))) }
            filter.getSpecified()?.let { predicates.add(if (it) builder.isNotNull(expression) else builder.isNull(expression)) }
            (filter.getIn() as List<*>?)?.takeIf { it.isNotEmpty() }?.let { predicates.add(expression.`in`(it.map { value -> localDateTimeValue(value) })) }
            (filter.getNotIn() as List<*>?)?.takeIf { it.isNotEmpty() }?.let { predicates.add(builder.not(expression.`in`(it.map { value -> localDateTimeValue(value) }))) }
            filter.getGreaterThan()?.let { predicates.add(builder.greaterThan(expression, localDateTimeValue(it))) }
            filter.getGreaterThanOrEqual()?.let { predicates.add(builder.greaterThanOrEqualTo(expression, localDateTimeValue(it))) }
            filter.getLessThan()?.let { predicates.add(builder.lessThan(expression, localDateTimeValue(it))) }
            filter.getLessThanOrEqual()?.let { predicates.add(builder.lessThanOrEqualTo(expression, localDateTimeValue(it))) }
            builder.and(*predicates.toTypedArray())
        }

    private fun localDateTimeValue(value: Any?): LocalDateTime =
        when (value) {
            is LocalDateTime -> value
            null -> throw IllegalArgumentException("LocalDateTime filter value is required.")
            else -> value.toString().let { raw ->
                if (raw.all { it.isDigit() }) {
                    java.time.Instant.ofEpochMilli(raw.toLong()).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
                } else {
                    LocalDateTime.parse(raw)
                }
            }
        }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<TrainingRoundProgressReadModelEntity>, Expression<X>>
    ): Specification<TrainingRoundProgressReadModelEntity> =
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

    private fun TrainingRoundProgressReadModelEntity.toProjection(): TrainingRoundProgressReadModelProjection =
        TrainingRoundProgressReadModelProjection().also {
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.roundId = this@toProjection.roundId
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.roundNumber = this@toProjection.roundNumber
            it.state = this@toProjection.state
            it.selectedOrganizationIds = this@toProjection.selectedOrganizationIds?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<UUID>>() {}) } ?: emptyList()
            it.selectedParticipants = this@toProjection.selectedParticipants?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<TrainingRoundParticipant>>() {}) } ?: emptyList()
            it.selectedOrganizationCount = this@toProjection.selectedOrganizationCount
            it.selectedRuntimeCount = this@toProjection.selectedRuntimeCount
            it.targetRuntimeCount = this@toProjection.targetRuntimeCount
            it.executionPlanDispatchedCount = this@toProjection.executionPlanDispatchedCount
            it.roundExecutionStartedCount = this@toProjection.roundExecutionStartedCount
            it.submittedModelUpdateCount = this@toProjection.submittedModelUpdateCount
            it.rejectedUpdateCount = this@toProjection.rejectedUpdateCount
            it.acceptedModelUpdateCount = this@toProjection.acceptedModelUpdateCount
            it.acceptedUpdateCount = this@toProjection.acceptedUpdateCount
            it.pendingUpdateCount = this@toProjection.pendingUpdateCount
            it.failedRoundExecutionCount = this@toProjection.failedRoundExecutionCount
            it.completedRoundExecutionCount = this@toProjection.completedRoundExecutionCount
            it.retriedRoundExecutionCount = this@toProjection.retriedRoundExecutionCount
            it.failedRoundExecutionRetryCount = this@toProjection.failedRoundExecutionRetryCount
            it.quorumMet = this@toProjection.quorumMet
            it.quorumStatus = this@toProjection.quorumStatus
            it.minimumNodesPerRound = this@toProjection.minimumNodesPerRound
            it.aggregationReady = this@toProjection.aggregationReady
            it.secureAggregationRequired = this@toProjection.secureAggregationRequired
            it.secureAggregationStatus = this@toProjection.secureAggregationStatus
            it.evaluationComplete = this@toProjection.evaluationComplete
            it.progressPercent = this@toProjection.progressPercent
            it.currentPhase = this@toProjection.currentPhase
            it.nextAction = this@toProjection.nextAction
            it.blockedReason = this@toProjection.blockedReason
            it.delayedReason = this@toProjection.delayedReason
            it.roundStartedAt = this@toProjection.roundStartedAt
            it.contributionDeadlineAt = this@toProjection.contributionDeadlineAt
            it.aggregationStartedAt = this@toProjection.aggregationStartedAt
            it.evaluationSubmittedAt = this@toProjection.evaluationSubmittedAt
            it.completedAt = this@toProjection.completedAt
            it.failedAt = this@toProjection.failedAt
            it.baseModelId = this@toProjection.baseModelId
            it.artifactRefs = this@toProjection.artifactRefs?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.rejectedUpdateReasons = this@toProjection.rejectedUpdateReasons?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.aggregatedModelId = this@toProjection.aggregatedModelId
            it.globalAccuracy = this@toProjection.globalAccuracy
            it.globalFairnessScore = this@toProjection.globalFairnessScore
            it.failureReason = this@toProjection.failureReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
