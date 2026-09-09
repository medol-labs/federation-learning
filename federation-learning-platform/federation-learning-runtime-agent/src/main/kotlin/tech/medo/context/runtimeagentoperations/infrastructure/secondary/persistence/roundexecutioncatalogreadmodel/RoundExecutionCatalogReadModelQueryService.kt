package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.roundexecutioncatalogreadmodel

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
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModel
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelProjection
import tech.medo.runtimeagentoperations.roundexecutioncatalog.toReadModel

@Service
class RoundExecutionCatalogReadModelQueryService(
    private val repository: SpringDataRoundExecutionCatalogReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<RoundExecutionCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RoundExecutionCatalogReadModelCriteria?, pageable: Pageable): Page<RoundExecutionCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RoundExecutionCatalogReadModelCriteria?): Specification<RoundExecutionCatalogReadModelEntity> {
        var specification = Specification.where<RoundExecutionCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.roundExecutionId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roundExecutionId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.executionSessionId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("executionSessionId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.executionPlanId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("executionPlanId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingRunConfigurationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingRunConfigurationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roundId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundNumber?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Int>> { root -> root.get("roundNumber") })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<RoundExecutionStateEnum>> { root -> root.get("state") })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.baseModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("baseModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeEngineJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeEngineJobId") })) }
            criteria.runtimeEngineObservedStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeEngineObservedStatus") })) }
            criteria.runtimeEngineObservationAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("runtimeEngineObservationAt") })) }
            criteria.localUpdateArtifactRef?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("localUpdateArtifactRef") })) }
            criteria.metricsArtifactRef?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("metricsArtifactRef") })) }
            criteria.localExecutionRequirementsSatisfied?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("localExecutionRequirementsSatisfied") })) }
            criteria.runtimeIdentityMatched?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeIdentityMatched") })) }
            criteria.runtimeDatasetBindingAvailable?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeDatasetBindingAvailable") })) }
            criteria.datasetAccessValidated?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("datasetAccessValidated") })) }
            criteria.baseModelAvailable?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("baseModelAvailable") })) }
            criteria.trainingConfigurationSupported?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("trainingConfigurationSupported") })) }
            criteria.runtimeResourceAvailable?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeResourceAvailable") })) }
            criteria.runtimeAgentIdle?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeAgentIdle") })) }
            criteria.updateArtifactId?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("updateArtifactId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.artifactRef?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("artifactRef") })) }
            criteria.artifactDigest?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("artifactDigest") })) }
            criteria.trainingLoss?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("trainingLoss") })) }
            criteria.receivedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("receivedAt") })) }
            criteria.acceptedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("acceptedAt") })) }
            criteria.rejectedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("rejectedAt") })) }
            criteria.startedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("startedAt") })) }
            criteria.completedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("completedAt") })) }
            criteria.failedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("failedAt") })) }
            criteria.submittedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("submittedAt") })) }
            criteria.failureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("failureReason") })) }
            criteria.retryReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("retryReason") })) }
            criteria.runtimeEngineReleased?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeEngineReleased") })) }
            criteria.runtimeEngineReleaseFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeEngineReleaseFailureReason") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RoundExecutionCatalogReadModelEntity> =
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
        field: Function<Root<RoundExecutionCatalogReadModelEntity>, Expression<X>>
    ): Specification<RoundExecutionCatalogReadModelEntity> =
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

    private fun RoundExecutionCatalogReadModelEntity.toProjection(): RoundExecutionCatalogReadModelProjection =
        RoundExecutionCatalogReadModelProjection().also {
            it.roundExecutionId = this@toProjection.roundExecutionId
            it.executionSessionId = this@toProjection.executionSessionId
            it.executionPlanId = this@toProjection.executionPlanId
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.roundId = this@toProjection.roundId
            it.roundNumber = this@toProjection.roundNumber
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.state = this@toProjection.state
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.baseModelId = this@toProjection.baseModelId
            it.runtimeEngineJobId = this@toProjection.runtimeEngineJobId
            it.runtimeEngineObservedStatus = this@toProjection.runtimeEngineObservedStatus
            it.runtimeEngineObservationAt = this@toProjection.runtimeEngineObservationAt
            it.localUpdateArtifactRef = this@toProjection.localUpdateArtifactRef
            it.metricsArtifactRef = this@toProjection.metricsArtifactRef
            it.localExecutionRequirementsSatisfied = this@toProjection.localExecutionRequirementsSatisfied
            it.runtimeIdentityMatched = this@toProjection.runtimeIdentityMatched
            it.runtimeDatasetBindingAvailable = this@toProjection.runtimeDatasetBindingAvailable
            it.datasetAccessValidated = this@toProjection.datasetAccessValidated
            it.baseModelAvailable = this@toProjection.baseModelAvailable
            it.trainingConfigurationSupported = this@toProjection.trainingConfigurationSupported
            it.runtimeResourceAvailable = this@toProjection.runtimeResourceAvailable
            it.runtimeAgentIdle = this@toProjection.runtimeAgentIdle
            it.updateArtifactId = this@toProjection.updateArtifactId
            it.artifactRef = this@toProjection.artifactRef
            it.artifactDigest = this@toProjection.artifactDigest
            it.trainingLoss = this@toProjection.trainingLoss
            it.receivedAt = this@toProjection.receivedAt
            it.acceptedAt = this@toProjection.acceptedAt
            it.rejectedAt = this@toProjection.rejectedAt
            it.startedAt = this@toProjection.startedAt
            it.completedAt = this@toProjection.completedAt
            it.failedAt = this@toProjection.failedAt
            it.submittedAt = this@toProjection.submittedAt
            it.failureReason = this@toProjection.failureReason
            it.retryReason = this@toProjection.retryReason
            it.runtimeEngineReleased = this@toProjection.runtimeEngineReleased
            it.runtimeEngineReleaseFailureReason = this@toProjection.runtimeEngineReleaseFailureReason
            it.rejectionReasons = this@toProjection.rejectionReasons?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
