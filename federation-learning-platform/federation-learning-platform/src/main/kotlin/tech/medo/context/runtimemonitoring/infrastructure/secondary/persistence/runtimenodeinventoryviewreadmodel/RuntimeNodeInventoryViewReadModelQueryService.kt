package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimenodeinventoryviewreadmodel

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

import tech.medo.runtimemonitoring.runtimenodeinventoryview.RuntimeNodeInventoryViewReadModel
import tech.medo.runtimemonitoring.runtimenodeinventoryview.RuntimeNodeInventoryViewReadModelCriteria
import tech.medo.runtimemonitoring.runtimenodeinventoryview.RuntimeNodeInventoryViewReadModelProjection
import tech.medo.runtimemonitoring.runtimenodeinventoryview.toReadModel

@Service
class RuntimeNodeInventoryViewReadModelQueryService(
    private val repository: SpringDataRuntimeNodeInventoryViewReadModelRepository
) : QueryService<RuntimeNodeInventoryViewReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeNodeInventoryViewReadModelCriteria?, pageable: Pageable): Page<RuntimeNodeInventoryViewReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeNodeInventoryViewReadModelCriteria?): Specification<RuntimeNodeInventoryViewReadModelEntity> {
        var specification = Specification.where<RuntimeNodeInventoryViewReadModelEntity>(null)
        if (criteria != null) {
            criteria.nodeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("nodeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeNodeInventoryReportId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeNodeInventoryReportId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.runtimeNodeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeNodeName") })) }
            criteria.infrastructureNodeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("infrastructureNodeId") })) }
            criteria.runtimeNodeRole?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeNodeRole") })) }
            criteria.nodeReady?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<Boolean>> { root -> root.get("nodeReady") })) }
            criteria.runtimeEngineVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeEngineVersion") })) }
            criteria.containerEngineVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("containerEngineVersion") })) }
            criteria.operatingSystem?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("operatingSystem") })) }
            criteria.architecture?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("architecture") })) }
            criteria.inventoryHash?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<String>> { root -> root.get("inventoryHash") })) }
            criteria.discoveredAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("discoveredAt") })) }
            criteria.recordedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("recordedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeNodeInventoryViewReadModelEntity> =
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
        field: Function<Root<RuntimeNodeInventoryViewReadModelEntity>, Expression<X>>
    ): Specification<RuntimeNodeInventoryViewReadModelEntity> =
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

    private fun RuntimeNodeInventoryViewReadModelEntity.toProjection(): RuntimeNodeInventoryViewReadModelProjection =
        RuntimeNodeInventoryViewReadModelProjection().also {
            it.nodeId = this@toProjection.nodeId
            it.runtimeNodeInventoryReportId = this@toProjection.runtimeNodeInventoryReportId
            it.organizationId = this@toProjection.organizationId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.runtimeNodeName = this@toProjection.runtimeNodeName
            it.infrastructureNodeId = this@toProjection.infrastructureNodeId
            it.runtimeNodeRole = this@toProjection.runtimeNodeRole
            it.nodeReady = this@toProjection.nodeReady
            it.runtimeEngineVersion = this@toProjection.runtimeEngineVersion
            it.containerEngineVersion = this@toProjection.containerEngineVersion
            it.operatingSystem = this@toProjection.operatingSystem
            it.architecture = this@toProjection.architecture
            it.inventoryHash = this@toProjection.inventoryHash
            it.discoveredAt = this@toProjection.discoveredAt
            it.recordedAt = this@toProjection.recordedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
