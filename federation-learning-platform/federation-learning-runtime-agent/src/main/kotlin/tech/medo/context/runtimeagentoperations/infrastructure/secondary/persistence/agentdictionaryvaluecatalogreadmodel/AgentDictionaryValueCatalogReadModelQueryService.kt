package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdictionaryvaluecatalogreadmodel

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

import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModel
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.toReadModel

@Service
class AgentDictionaryValueCatalogReadModelQueryService(
    private val repository: SpringDataAgentDictionaryValueCatalogReadModelRepository
) : QueryService<AgentDictionaryValueCatalogReadModelEntity>() {
    fun findByCriteria(criteria: AgentDictionaryValueCatalogReadModelCriteria?, pageable: Pageable): Page<AgentDictionaryValueCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: AgentDictionaryValueCatalogReadModelCriteria?): Specification<AgentDictionaryValueCatalogReadModelEntity> {
        var specification = Specification.where<AgentDictionaryValueCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.dictionaryValueId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dictionaryValueId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.dictionaryId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dictionaryId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.dictionaryCode?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("dictionaryCode") })) }
            criteria.valueCode?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("valueCode") })) }
            criteria.displayName?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("displayName") })) }
            criteria.displayOrder?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<Int>> { root -> root.get("displayOrder") })) }
            criteria.active?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("active") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("state") })) }
            criteria.syncedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("syncedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<AgentDictionaryValueCatalogReadModelEntity> =
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
        field: Function<Root<AgentDictionaryValueCatalogReadModelEntity>, Expression<X>>
    ): Specification<AgentDictionaryValueCatalogReadModelEntity> =
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

    private fun AgentDictionaryValueCatalogReadModelEntity.toProjection(): AgentDictionaryValueCatalogReadModelProjection =
        AgentDictionaryValueCatalogReadModelProjection().also {
            it.dictionaryValueId = this@toProjection.dictionaryValueId
            it.dictionaryId = this@toProjection.dictionaryId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.valueCode = this@toProjection.valueCode
            it.displayName = this@toProjection.displayName
            it.displayOrder = this@toProjection.displayOrder
            it.active = this@toProjection.active
            it.state = this@toProjection.state
            it.syncedAt = this@toProjection.syncedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
