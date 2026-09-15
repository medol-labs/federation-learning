package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimeidentitycatalogreadmodel

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

import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.AgentRuntimeIdentityCatalogReadModel
import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.AgentRuntimeIdentityCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.AgentRuntimeIdentityCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentruntimeidentitycatalog.toReadModel

@Service
class AgentRuntimeIdentityCatalogReadModelQueryService(
    private val repository: SpringDataAgentRuntimeIdentityCatalogReadModelRepository
) : QueryService<AgentRuntimeIdentityCatalogReadModelEntity>() {
    fun findByCriteria(criteria: AgentRuntimeIdentityCatalogReadModelCriteria?, pageable: Pageable): Page<AgentRuntimeIdentityCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: AgentRuntimeIdentityCatalogReadModelCriteria?): Specification<AgentRuntimeIdentityCatalogReadModelEntity> {
        var specification = Specification.where<AgentRuntimeIdentityCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.identityStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<String>> { root -> root.get("identityStatus") })) }
            criteria.activatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("activatedAt") })) }
            criteria.revokedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("revokedAt") })) }
            criteria.syncedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("syncedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<AgentRuntimeIdentityCatalogReadModelEntity> =
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
        field: Function<Root<AgentRuntimeIdentityCatalogReadModelEntity>, Expression<X>>
    ): Specification<AgentRuntimeIdentityCatalogReadModelEntity> =
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

    private fun AgentRuntimeIdentityCatalogReadModelEntity.toProjection(): AgentRuntimeIdentityCatalogReadModelProjection =
        AgentRuntimeIdentityCatalogReadModelProjection().also {
            it.runtimeId = this@toProjection.runtimeId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.identityStatus = this@toProjection.identityStatus
            it.activatedAt = this@toProjection.activatedAt
            it.revokedAt = this@toProjection.revokedAt
            it.syncedAt = this@toProjection.syncedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
