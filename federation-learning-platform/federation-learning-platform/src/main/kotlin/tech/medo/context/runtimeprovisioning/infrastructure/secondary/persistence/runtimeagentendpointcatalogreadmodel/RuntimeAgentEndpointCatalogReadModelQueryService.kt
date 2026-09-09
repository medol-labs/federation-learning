package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeagentendpointcatalogreadmodel

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

import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeagentendpointcatalog.toReadModel

@Service
class RuntimeAgentEndpointCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeAgentEndpointCatalogReadModelRepository
) : QueryService<RuntimeAgentEndpointCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeAgentEndpointCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeAgentEndpointCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeAgentEndpointCatalogReadModelCriteria?): Specification<RuntimeAgentEndpointCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeAgentEndpointCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.runtimeAgentEndpoint?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeAgentEndpoint") })) }
            criteria.endpointScope?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> root.get("endpointScope") })) }
            criteria.connectionStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<String>> { root -> root.get("connectionStatus") })) }
            criteria.connectedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("connectedAt") })) }
            criteria.activatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("activatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeAgentEndpointCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeAgentEndpointCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeAgentEndpointCatalogReadModelEntity> =
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

    private fun RuntimeAgentEndpointCatalogReadModelEntity.toProjection(): RuntimeAgentEndpointCatalogReadModelProjection =
        RuntimeAgentEndpointCatalogReadModelProjection().also {
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeId = this@toProjection.runtimeId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.organizationId = this@toProjection.organizationId
            it.runtimeName = this@toProjection.runtimeName
            it.runtimeAgentEndpoint = this@toProjection.runtimeAgentEndpoint
            it.endpointScope = this@toProjection.endpointScope
            it.connectionStatus = this@toProjection.connectionStatus
            it.connectedAt = this@toProjection.connectedAt
            it.activatedAt = this@toProjection.activatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
