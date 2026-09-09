package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimeagentlifecyclecatalogreadmodel

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

import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModel
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.RuntimeAgentLifecycleCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog.toReadModel

@Service
class RuntimeAgentLifecycleCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeAgentLifecycleCatalogReadModelRepository
) : QueryService<RuntimeAgentLifecycleCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeAgentLifecycleCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeAgentLifecycleCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeAgentLifecycleCatalogReadModelCriteria?): Specification<RuntimeAgentLifecycleCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeAgentLifecycleCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.agentVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<String>> { root -> root.get("agentVersion") })) }
            criteria.lifecycleStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<String>> { root -> root.get("lifecycleStatus") })) }
            criteria.bootstrapConfigurationLoaded?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("bootstrapConfigurationLoaded") })) }
            criteria.bootstrapFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<String>> { root -> root.get("bootstrapFailureReason") })) }
            criteria.runtimeAgentSelfCheckPassed?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeAgentSelfCheckPassed") })) }
            criteria.configurationLoaded?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("configurationLoaded") })) }
            criteria.secretStoreAccessible?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("secretStoreAccessible") })) }
            criteria.runtimeEngineAdapterReady?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeEngineAdapterReady") })) }
            criteria.modelRepositoryClientReady?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("modelRepositoryClientReady") })) }
            criteria.localDatasetBindingStoreReady?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("localDatasetBindingStoreReady") })) }
            criteria.workingDirectoryWritable?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("workingDirectoryWritable") })) }
            criteria.bootstrappedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("bootstrappedAt") })) }
            criteria.bootstrapFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("bootstrapFailedAt") })) }
            criteria.startedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("startedAt") })) }
            criteria.readyAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("readyAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeAgentLifecycleCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeAgentLifecycleCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeAgentLifecycleCatalogReadModelEntity> =
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

    private fun RuntimeAgentLifecycleCatalogReadModelEntity.toProjection(): RuntimeAgentLifecycleCatalogReadModelProjection =
        RuntimeAgentLifecycleCatalogReadModelProjection().also {
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.agentVersion = this@toProjection.agentVersion
            it.lifecycleStatus = this@toProjection.lifecycleStatus
            it.bootstrapConfigurationLoaded = this@toProjection.bootstrapConfigurationLoaded
            it.bootstrapFailureReason = this@toProjection.bootstrapFailureReason
            it.runtimeAgentSelfCheckPassed = this@toProjection.runtimeAgentSelfCheckPassed
            it.configurationLoaded = this@toProjection.configurationLoaded
            it.secretStoreAccessible = this@toProjection.secretStoreAccessible
            it.runtimeEngineAdapterReady = this@toProjection.runtimeEngineAdapterReady
            it.modelRepositoryClientReady = this@toProjection.modelRepositoryClientReady
            it.localDatasetBindingStoreReady = this@toProjection.localDatasetBindingStoreReady
            it.workingDirectoryWritable = this@toProjection.workingDirectoryWritable
            it.bootstrappedAt = this@toProjection.bootstrappedAt
            it.bootstrapFailedAt = this@toProjection.bootstrapFailedAt
            it.startedAt = this@toProjection.startedAt
            it.readyAt = this@toProjection.readyAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
