package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionaryvaluecatalogreadmodel

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
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModel
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModelCriteria
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModelProjection
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.toReadModel

@Service
class DictionaryValueCatalogReadModelQueryService(
    private val repository: SpringDataDictionaryValueCatalogReadModelRepository
) : QueryService<DictionaryValueCatalogReadModelEntity>() {
    fun findByCriteria(criteria: DictionaryValueCatalogReadModelCriteria?, pageable: Pageable): Page<DictionaryValueCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: DictionaryValueCatalogReadModelCriteria?): Specification<DictionaryValueCatalogReadModelEntity> {
        var specification = Specification.where<DictionaryValueCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.dictionaryValueId?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dictionaryValueId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.dictionaryId?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dictionaryId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.dictionaryCode?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("dictionaryCode") })) }
            criteria.valueCode?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("valueCode") })) }
            criteria.displayName?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("displayName") })) }
            criteria.displayOrder?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<Int>> { root -> root.get("displayOrder") })) }
            criteria.description?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("description") })) }
            criteria.active?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("active") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<DictionaryValueStateEnum>> { root -> root.get("state") })) }
            criteria.addedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("addedAt") })) }
            criteria.updatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("updatedAt") })) }
            criteria.disabledAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("disabledAt") })) }
            criteria.disabledReason?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<String>> { root -> root.get("disabledReason") })) }
            criteria.enabledAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("enabledAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<DictionaryValueCatalogReadModelEntity> =
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
        field: Function<Root<DictionaryValueCatalogReadModelEntity>, Expression<X>>
    ): Specification<DictionaryValueCatalogReadModelEntity> =
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

    private fun DictionaryValueCatalogReadModelEntity.toProjection(): DictionaryValueCatalogReadModelProjection =
        DictionaryValueCatalogReadModelProjection().also {
            it.dictionaryValueId = this@toProjection.dictionaryValueId
            it.dictionaryId = this@toProjection.dictionaryId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.valueCode = this@toProjection.valueCode
            it.displayName = this@toProjection.displayName
            it.displayOrder = this@toProjection.displayOrder
            it.description = this@toProjection.description
            it.active = this@toProjection.active
            it.state = this@toProjection.state
            it.addedAt = this@toProjection.addedAt
            it.updatedAt = this@toProjection.updatedAt
            it.disabledAt = this@toProjection.disabledAt
            it.disabledReason = this@toProjection.disabledReason
            it.enabledAt = this@toProjection.enabledAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
