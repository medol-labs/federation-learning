package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructurepackagecatalogreadmodel

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
import java.time.LocalDateTime

import java.util.UUID
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum

import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.toReadModel

@Service
class RuntimeInfrastructurePackageCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeInfrastructurePackageCatalogReadModelRepository
) : QueryService<RuntimeInfrastructurePackageCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeInfrastructurePackageCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeInfrastructurePackageCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeInfrastructurePackageCatalogReadModelCriteria?): Specification<RuntimeInfrastructurePackageCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeInfrastructurePackageCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeInfrastructurePackageId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructurePackageId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.packageName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<String>> { root -> root.get("packageName") })) }
            criteria.packageVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<String>> { root -> root.get("packageVersion") })) }
            criteria.runtimeEnvironmentType?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeEnvironmentType") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<RuntimeInfrastructurePackageStateEnum>> { root -> root.get("state") })) }
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeInfrastructurePackageCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeInfrastructurePackageCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeInfrastructurePackageCatalogReadModelEntity> =
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

    private fun RuntimeInfrastructurePackageCatalogReadModelEntity.toProjection(): RuntimeInfrastructurePackageCatalogReadModelProjection =
        RuntimeInfrastructurePackageCatalogReadModelProjection().also {
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.packageName = this@toProjection.packageName
            it.packageVersion = this@toProjection.packageVersion
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.state = this@toProjection.state
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
