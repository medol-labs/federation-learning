package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionaryvaluetranslationcatalogreadmodel

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

import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModel
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModelCriteria
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModelProjection
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.toReadModel

@Service
class DictionaryValueTranslationCatalogReadModelQueryService(
    private val repository: SpringDataDictionaryValueTranslationCatalogReadModelRepository
) : QueryService<DictionaryValueTranslationCatalogReadModelEntity>() {
    fun findByCriteria(criteria: DictionaryValueTranslationCatalogReadModelCriteria?, pageable: Pageable): Page<DictionaryValueTranslationCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: DictionaryValueTranslationCatalogReadModelCriteria?): Specification<DictionaryValueTranslationCatalogReadModelEntity> {
        var specification = Specification.where<DictionaryValueTranslationCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.dictionaryValueTranslationId?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dictionaryValueTranslationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.dictionaryValueId?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dictionaryValueId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.dictionaryCode?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<String>> { root -> root.get("dictionaryCode") })) }
            criteria.valueCode?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<String>> { root -> root.get("valueCode") })) }
            criteria.locale?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<String>> { root -> root.get("locale") })) }
            criteria.displayName?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<String>> { root -> root.get("displayName") })) }
            criteria.description?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<String>> { root -> root.get("description") })) }
            criteria.updatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("updatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<DictionaryValueTranslationCatalogReadModelEntity> =
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
        field: Function<Root<DictionaryValueTranslationCatalogReadModelEntity>, Expression<X>>
    ): Specification<DictionaryValueTranslationCatalogReadModelEntity> =
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

    private fun DictionaryValueTranslationCatalogReadModelEntity.toProjection(): DictionaryValueTranslationCatalogReadModelProjection =
        DictionaryValueTranslationCatalogReadModelProjection().also {
            it.dictionaryValueTranslationId = this@toProjection.dictionaryValueTranslationId
            it.dictionaryValueId = this@toProjection.dictionaryValueId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.valueCode = this@toProjection.valueCode
            it.locale = this@toProjection.locale
            it.displayName = this@toProjection.displayName
            it.description = this@toProjection.description
            it.updatedAt = this@toProjection.updatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
