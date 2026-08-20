package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdatasetaccessvalidationcatalogreadmodel

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
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.AgentDatasetAccessValidationCatalogReadModel
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.AgentDatasetAccessValidationCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.AgentDatasetAccessValidationCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentdatasetaccessvalidationcatalog.toReadModel

@Service
class AgentDatasetAccessValidationCatalogReadModelQueryService(
    private val repository: SpringDataAgentDatasetAccessValidationCatalogReadModelRepository
) : QueryService<AgentDatasetAccessValidationCatalogReadModelEntity>() {
    fun findByCriteria(criteria: AgentDatasetAccessValidationCatalogReadModelCriteria?, pageable: Pageable): Page<AgentDatasetAccessValidationCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: AgentDatasetAccessValidationCatalogReadModelCriteria?): Specification<AgentDatasetAccessValidationCatalogReadModelEntity> {
        var specification = Specification.where<AgentDatasetAccessValidationCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.datasetAccessValidationId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetAccessValidationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeDatasetBindingId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeDatasetBindingId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetName?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> root.get("datasetName") })) }
            criteria.readable?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("readable") })) }
            criteria.schemaReadable?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("schemaReadable") })) }
            criteria.sampleBatchReadable?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("sampleBatchReadable") })) }
            criteria.validationStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> root.get("validationStatus") })) }
            criteria.failureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<String>> { root -> root.get("failureReason") })) }
            criteria.validatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("validatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<AgentDatasetAccessValidationCatalogReadModelEntity> =
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
        field: Function<Root<AgentDatasetAccessValidationCatalogReadModelEntity>, Expression<X>>
    ): Specification<AgentDatasetAccessValidationCatalogReadModelEntity> =
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

    private fun AgentDatasetAccessValidationCatalogReadModelEntity.toProjection(): AgentDatasetAccessValidationCatalogReadModelProjection =
        AgentDatasetAccessValidationCatalogReadModelProjection().also {
            it.datasetAccessValidationId = this@toProjection.datasetAccessValidationId
            it.runtimeDatasetBindingId = this@toProjection.runtimeDatasetBindingId
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.runtimeId = this@toProjection.runtimeId
            it.datasetName = this@toProjection.datasetName
            it.readable = this@toProjection.readable
            it.schemaReadable = this@toProjection.schemaReadable
            it.sampleBatchReadable = this@toProjection.sampleBatchReadable
            it.validationStatus = this@toProjection.validationStatus
            it.failureReason = this@toProjection.failureReason
            it.validatedAt = this@toProjection.validatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
