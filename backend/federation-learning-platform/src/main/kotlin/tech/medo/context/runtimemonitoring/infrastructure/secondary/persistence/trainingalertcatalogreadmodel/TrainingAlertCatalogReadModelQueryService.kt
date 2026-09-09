package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.trainingalertcatalogreadmodel

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
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimemonitoring.trainingalertcatalog.TrainingAlertCatalogReadModel
import tech.medo.runtimemonitoring.trainingalertcatalog.TrainingAlertCatalogReadModelCriteria
import tech.medo.runtimemonitoring.trainingalertcatalog.TrainingAlertCatalogReadModelProjection
import tech.medo.runtimemonitoring.trainingalertcatalog.toReadModel

@Service
class TrainingAlertCatalogReadModelQueryService(
    private val repository: SpringDataTrainingAlertCatalogReadModelRepository
) : QueryService<TrainingAlertCatalogReadModelEntity>() {
    fun findByCriteria(criteria: TrainingAlertCatalogReadModelCriteria?, pageable: Pageable): Page<TrainingAlertCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: TrainingAlertCatalogReadModelCriteria?): Specification<TrainingAlertCatalogReadModelEntity> {
        var specification = Specification.where<TrainingAlertCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.alertId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("alertId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.nodeId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("nodeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeNodeName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeNodeName") })) }
            criteria.trainingJobObjective?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> root.get("trainingJobObjective") })) }
            criteria.severity?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> root.get("severity") })) }
            criteria.message?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> root.get("message") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<TrainingAlertStateEnum>> { root -> root.get("state") })) }
            criteria.acknowledgedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("acknowledgedAt") })) }
            criteria.resolvedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("resolvedAt") })) }
            criteria.resolutionSummary?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<String>> { root -> root.get("resolutionSummary") })) }
            criteria.canAcknowledge?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("canAcknowledge") })) }
            criteria.canResolve?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("canResolve") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<TrainingAlertCatalogReadModelEntity> =
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
        field: Function<Root<TrainingAlertCatalogReadModelEntity>, Expression<X>>
    ): Specification<TrainingAlertCatalogReadModelEntity> =
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

    private fun TrainingAlertCatalogReadModelEntity.toProjection(): TrainingAlertCatalogReadModelProjection =
        TrainingAlertCatalogReadModelProjection().also {
            it.alertId = this@toProjection.alertId
            it.nodeId = this@toProjection.nodeId
            it.trainingJobId = this@toProjection.trainingJobId
            it.runtimeNodeName = this@toProjection.runtimeNodeName
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.severity = this@toProjection.severity
            it.message = this@toProjection.message
            it.state = this@toProjection.state
            it.acknowledgedAt = this@toProjection.acknowledgedAt
            it.resolvedAt = this@toProjection.resolvedAt
            it.resolutionSummary = this@toProjection.resolutionSummary
            it.canAcknowledge = this@toProjection.canAcknowledge
            it.canResolve = this@toProjection.canResolve
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
