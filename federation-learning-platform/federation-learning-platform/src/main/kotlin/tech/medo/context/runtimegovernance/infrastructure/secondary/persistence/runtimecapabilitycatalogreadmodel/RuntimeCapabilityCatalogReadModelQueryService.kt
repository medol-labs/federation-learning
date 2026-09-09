package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimecapabilitycatalogreadmodel

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

import tech.medo.runtimegovernance.runtimecapabilitycatalog.RuntimeCapabilityCatalogReadModel
import tech.medo.runtimegovernance.runtimecapabilitycatalog.RuntimeCapabilityCatalogReadModelCriteria
import tech.medo.runtimegovernance.runtimecapabilitycatalog.RuntimeCapabilityCatalogReadModelProjection
import tech.medo.runtimegovernance.runtimecapabilitycatalog.toReadModel

@Service
class RuntimeCapabilityCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeCapabilityCatalogReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<RuntimeCapabilityCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeCapabilityCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeCapabilityCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeCapabilityCatalogReadModelCriteria?): Specification<RuntimeCapabilityCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeCapabilityCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeCapabilityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.capabilityStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeCapabilityCatalogReadModelEntity>, Expression<String>> { root -> root.get("capabilityStatus") })) }
            criteria.detectedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeCapabilityCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("detectedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeCapabilityCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeCapabilityCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeCapabilityCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeCapabilityCatalogReadModelEntity> =
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

    private fun RuntimeCapabilityCatalogReadModelEntity.toProjection(): RuntimeCapabilityCatalogReadModelProjection =
        RuntimeCapabilityCatalogReadModelProjection().also {
            it.runtimeId = this@toProjection.runtimeId
            it.capabilityTypes = this@toProjection.capabilityTypes?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.capabilityStatus = this@toProjection.capabilityStatus
            it.detectedAt = this@toProjection.detectedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
