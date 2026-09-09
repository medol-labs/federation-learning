package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimenoderesourcelatestreadmodel

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

import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.AgentRuntimeNodeResourceLatestReadModel
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.AgentRuntimeNodeResourceLatestReadModelCriteria
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.AgentRuntimeNodeResourceLatestReadModelProjection
import tech.medo.runtimeagentoperations.agentruntimenoderesourcelatest.toReadModel

@Service
class AgentRuntimeNodeResourceLatestReadModelQueryService(
    private val repository: SpringDataAgentRuntimeNodeResourceLatestReadModelRepository
) : QueryService<AgentRuntimeNodeResourceLatestReadModelEntity>() {
    fun findByCriteria(criteria: AgentRuntimeNodeResourceLatestReadModelCriteria?, pageable: Pageable): Page<AgentRuntimeNodeResourceLatestReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: AgentRuntimeNodeResourceLatestReadModelCriteria?): Specification<AgentRuntimeNodeResourceLatestReadModelEntity> {
        var specification = Specification.where<AgentRuntimeNodeResourceLatestReadModelEntity>(null)
        if (criteria != null) {
            criteria.nodeId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("nodeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeNodeName?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<String>> { root -> root.get("runtimeNodeName") })) }
            criteria.nodeReady?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Boolean>> { root -> root.get("nodeReady") })) }
            criteria.allocatableCpuCores?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("allocatableCpuCores") })) }
            criteria.allocatableMemoryGb?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("allocatableMemoryGb") })) }
            criteria.allocatableGpuCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("allocatableGpuCount") })) }
            criteria.allocatedCpuCores?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("allocatedCpuCores") })) }
            criteria.allocatedMemoryGb?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("allocatedMemoryGb") })) }
            criteria.allocatedGpuCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("allocatedGpuCount") })) }
            criteria.availableCpuCores?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("availableCpuCores") })) }
            criteria.availableMemoryGb?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("availableMemoryGb") })) }
            criteria.availableGpuCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("availableGpuCount") })) }
            criteria.runningWorkloadCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("runningWorkloadCount") })) }
            criteria.workloadCapacity?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<Int>> { root -> root.get("workloadCapacity") })) }
            criteria.observedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("observedAt") })) }
            criteria.telemetryRetentionPolicy?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<String>> { root -> root.get("telemetryRetentionPolicy") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<AgentRuntimeNodeResourceLatestReadModelEntity> =
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
        field: Function<Root<AgentRuntimeNodeResourceLatestReadModelEntity>, Expression<X>>
    ): Specification<AgentRuntimeNodeResourceLatestReadModelEntity> =
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

    private fun AgentRuntimeNodeResourceLatestReadModelEntity.toProjection(): AgentRuntimeNodeResourceLatestReadModelProjection =
        AgentRuntimeNodeResourceLatestReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeNodeName = this@toProjection.runtimeNodeName
            it.nodeReady = this@toProjection.nodeReady
            it.allocatableCpuCores = this@toProjection.allocatableCpuCores
            it.allocatableMemoryGb = this@toProjection.allocatableMemoryGb
            it.allocatableGpuCount = this@toProjection.allocatableGpuCount
            it.allocatedCpuCores = this@toProjection.allocatedCpuCores
            it.allocatedMemoryGb = this@toProjection.allocatedMemoryGb
            it.allocatedGpuCount = this@toProjection.allocatedGpuCount
            it.availableCpuCores = this@toProjection.availableCpuCores
            it.availableMemoryGb = this@toProjection.availableMemoryGb
            it.availableGpuCount = this@toProjection.availableGpuCount
            it.runningWorkloadCount = this@toProjection.runningWorkloadCount
            it.workloadCapacity = this@toProjection.workloadCapacity
            it.observedAt = this@toProjection.observedAt
            it.telemetryRetentionPolicy = this@toProjection.telemetryRetentionPolicy
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
