package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimeidentitycatalogreadmodel

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

import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModel
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelCriteria
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelProjection
import tech.medo.runtimegovernance.runtimeidentitycatalog.toReadModel

@Service
class RuntimeIdentityCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeIdentityCatalogReadModelRepository
) : QueryService<RuntimeIdentityCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeIdentityCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeIdentityCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeIdentityCatalogReadModelCriteria?): Specification<RuntimeIdentityCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeIdentityCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.identityStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> root.get("identityStatus") })) }
            criteria.activatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("activatedAt") })) }
            criteria.revokedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("revokedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeIdentityCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeIdentityCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeIdentityCatalogReadModelEntity> =
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

    private fun RuntimeIdentityCatalogReadModelEntity.toProjection(): RuntimeIdentityCatalogReadModelProjection =
        RuntimeIdentityCatalogReadModelProjection().also {
            it.runtimeId = this@toProjection.runtimeId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.identityStatus = this@toProjection.identityStatus
            it.activatedAt = this@toProjection.activatedAt
            it.revokedAt = this@toProjection.revokedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
