package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimeinfrastructureconnectioncatalogreadmodel

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

import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.AgentRuntimeInfrastructureConnectionCatalogReadModel
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.AgentRuntimeInfrastructureConnectionCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.AgentRuntimeInfrastructureConnectionCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog.toReadModel

@Service
class AgentRuntimeInfrastructureConnectionCatalogReadModelQueryService(
    private val repository: SpringDataAgentRuntimeInfrastructureConnectionCatalogReadModelRepository
) : QueryService<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>() {
    fun findByCriteria(criteria: AgentRuntimeInfrastructureConnectionCatalogReadModelCriteria?, pageable: Pageable): Page<AgentRuntimeInfrastructureConnectionCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: AgentRuntimeInfrastructureConnectionCatalogReadModelCriteria?): Specification<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity> {
        var specification = Specification.where<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimePlatformConnectionReady?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimePlatformConnectionReady") })) }
            criteria.platformApiReachable?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("platformApiReachable") })) }
            criteria.agentAuthenticationSucceeded?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("agentAuthenticationSucceeded") })) }
            criteria.controlChannelEstablished?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("controlChannelEstablished") })) }
            criteria.heartbeatAccepted?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("heartbeatAccepted") })) }
            criteria.connectedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("connectedAt") })) }
            criteria.connectionReportFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("connectionReportFailedAt") })) }
            criteria.connectionReportFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<String>> { root -> root.get("connectionReportFailureReason") })) }
            criteria.connectionReportRetryable?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("connectionReportRetryable") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity> =
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
        field: Function<Root<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity>, Expression<X>>
    ): Specification<AgentRuntimeInfrastructureConnectionCatalogReadModelEntity> =
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

    private fun AgentRuntimeInfrastructureConnectionCatalogReadModelEntity.toProjection(): AgentRuntimeInfrastructureConnectionCatalogReadModelProjection =
        AgentRuntimeInfrastructureConnectionCatalogReadModelProjection().also {
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimePlatformConnectionReady = this@toProjection.runtimePlatformConnectionReady
            it.platformApiReachable = this@toProjection.platformApiReachable
            it.agentAuthenticationSucceeded = this@toProjection.agentAuthenticationSucceeded
            it.controlChannelEstablished = this@toProjection.controlChannelEstablished
            it.heartbeatAccepted = this@toProjection.heartbeatAccepted
            it.connectedAt = this@toProjection.connectedAt
            it.connectionReportFailedAt = this@toProjection.connectionReportFailedAt
            it.connectionReportFailureReason = this@toProjection.connectionReportFailureReason
            it.connectionReportRetryable = this@toProjection.connectionReportRetryable
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
