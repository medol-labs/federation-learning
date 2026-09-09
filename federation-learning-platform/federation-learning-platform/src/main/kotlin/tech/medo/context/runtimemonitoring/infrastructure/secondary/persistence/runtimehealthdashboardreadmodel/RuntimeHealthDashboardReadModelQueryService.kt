package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimehealthdashboardreadmodel

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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimemonitoring.runtimehealthdashboard.RuntimeHealthDashboardReadModel
import tech.medo.runtimemonitoring.runtimehealthdashboard.RuntimeHealthDashboardReadModelCriteria
import tech.medo.runtimemonitoring.runtimehealthdashboard.RuntimeHealthDashboardReadModelProjection
import tech.medo.runtimemonitoring.runtimehealthdashboard.toReadModel

@Service
class RuntimeHealthDashboardReadModelQueryService(
    private val repository: SpringDataRuntimeHealthDashboardReadModelRepository
) : QueryService<RuntimeHealthDashboardReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeHealthDashboardReadModelCriteria?, pageable: Pageable): Page<RuntimeHealthDashboardReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeHealthDashboardReadModelCriteria?): Specification<RuntimeHealthDashboardReadModelEntity> {
        var specification = Specification.where<RuntimeHealthDashboardReadModelEntity>(null)
        if (criteria != null) {
            criteria.nodeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("nodeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("federationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundExecutionId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roundExecutionId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> root.get("federationName") })) }
            criteria.trainingJobObjective?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> root.get("trainingJobObjective") })) }
            criteria.cpuLoad?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<BigDecimal>> { root -> root.get("cpuLoad") })) }
            criteria.gpuLoad?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<BigDecimal>> { root -> root.get("gpuLoad") })) }
            criteria.memoryLoad?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<BigDecimal>> { root -> root.get("memoryLoad") })) }
            criteria.nodeReady?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<Boolean>> { root -> root.get("nodeReady") })) }
            criteria.availableCpuCores?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<Int>> { root -> root.get("availableCpuCores") })) }
            criteria.availableMemoryGb?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<Int>> { root -> root.get("availableMemoryGb") })) }
            criteria.availableGpuCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<Int>> { root -> root.get("availableGpuCount") })) }
            criteria.runningWorkloadCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<Int>> { root -> root.get("runningWorkloadCount") })) }
            criteria.workloadCapacity?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<Int>> { root -> root.get("workloadCapacity") })) }
            criteria.healthStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<String>> { root -> root.get("healthStatus") })) }
            criteria.lastHeartbeatAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastHeartbeatAt") })) }
            criteria.lastResourceSnapshotAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastResourceSnapshotAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeHealthDashboardReadModelEntity> =
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
        field: Function<Root<RuntimeHealthDashboardReadModelEntity>, Expression<X>>
    ): Specification<RuntimeHealthDashboardReadModelEntity> =
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

    private fun RuntimeHealthDashboardReadModelEntity.toProjection(): RuntimeHealthDashboardReadModelProjection =
        RuntimeHealthDashboardReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.federationId = this@toProjection.federationId
            it.trainingJobId = this@toProjection.trainingJobId
            it.roundExecutionId = this@toProjection.roundExecutionId
            it.federationName = this@toProjection.federationName
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.cpuLoad = this@toProjection.cpuLoad
            it.gpuLoad = this@toProjection.gpuLoad
            it.memoryLoad = this@toProjection.memoryLoad
            it.nodeReady = this@toProjection.nodeReady
            it.availableCpuCores = this@toProjection.availableCpuCores
            it.availableMemoryGb = this@toProjection.availableMemoryGb
            it.availableGpuCount = this@toProjection.availableGpuCount
            it.runningWorkloadCount = this@toProjection.runningWorkloadCount
            it.workloadCapacity = this@toProjection.workloadCapacity
            it.healthStatus = this@toProjection.healthStatus
            it.lastHeartbeatAt = this@toProjection.lastHeartbeatAt
            it.lastResourceSnapshotAt = this@toProjection.lastResourceSnapshotAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
