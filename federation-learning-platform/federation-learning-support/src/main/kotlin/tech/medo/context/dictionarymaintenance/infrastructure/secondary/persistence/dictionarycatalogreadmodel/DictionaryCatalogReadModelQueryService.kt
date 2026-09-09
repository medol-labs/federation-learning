package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionarycatalogreadmodel

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
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.dictionarymaintenance.dictionarycatalog.DictionaryCatalogReadModel
import tech.medo.dictionarymaintenance.dictionarycatalog.DictionaryCatalogReadModelCriteria
import tech.medo.dictionarymaintenance.dictionarycatalog.DictionaryCatalogReadModelProjection
import tech.medo.dictionarymaintenance.dictionarycatalog.toReadModel

@Service
class DictionaryCatalogReadModelQueryService(
    private val repository: SpringDataDictionaryCatalogReadModelRepository
) : QueryService<DictionaryCatalogReadModelEntity>() {
    fun findByCriteria(criteria: DictionaryCatalogReadModelCriteria?, pageable: Pageable): Page<DictionaryCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: DictionaryCatalogReadModelCriteria?): Specification<DictionaryCatalogReadModelEntity> {
        var specification = Specification.where<DictionaryCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.dictionaryId?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dictionaryId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.dictionaryCode?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<String>> { root -> root.get("dictionaryCode") })) }
            criteria.dictionaryName?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<String>> { root -> root.get("dictionaryName") })) }
            criteria.description?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<String>> { root -> root.get("description") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<DictionaryStateEnum>> { root -> root.get("state") })) }
            criteria.registeredAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("registeredAt") })) }
            criteria.updatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("updatedAt") })) }
            criteria.archivedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("archivedAt") })) }
            criteria.archiveReason?.let { specification = specification.and(buildSpecification(it, Function<Root<DictionaryCatalogReadModelEntity>, Expression<String>> { root -> root.get("archiveReason") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<DictionaryCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<DictionaryCatalogReadModelEntity> =
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
        field: Function<Root<DictionaryCatalogReadModelEntity>, Expression<X>>
    ): Specification<DictionaryCatalogReadModelEntity> =
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

    private fun DictionaryCatalogReadModelEntity.toProjection(): DictionaryCatalogReadModelProjection =
        DictionaryCatalogReadModelProjection().also {
            it.dictionaryId = this@toProjection.dictionaryId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.dictionaryName = this@toProjection.dictionaryName
            it.description = this@toProjection.description
            it.state = this@toProjection.state
            it.registeredAt = this@toProjection.registeredAt
            it.updatedAt = this@toProjection.updatedAt
            it.archivedAt = this@toProjection.archivedAt
            it.archiveReason = this@toProjection.archiveReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
