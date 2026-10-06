package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

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
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum

import tech.medo.runtimeprovisioning.runtimeinstallationguide.RuntimeInstallationGuideReadModel
import tech.medo.runtimeprovisioning.runtimeinstallationguide.RuntimeInstallationGuideReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinstallationguide.RuntimeInstallationGuideReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinstallationguide.toReadModel

@Service
class RuntimeInstallationGuideReadModelQueryService(
    private val repository: SpringDataRuntimeInstallationGuideReadModelRepository
) : QueryService<RuntimeInstallationGuideReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeInstallationGuideReadModelCriteria?, pageable: Pageable): Page<RuntimeInstallationGuideReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeInstallationGuideReadModelCriteria?): Specification<RuntimeInstallationGuideReadModelEntity> {
        var specification = Specification.where<RuntimeInstallationGuideReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeInstallationPlanId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInstallationPlanId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructureState?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<RuntimeInfrastructureStateEnum>> { root -> root.get("runtimeInfrastructureState") })) }
            criteria.runtimeInfrastructurePackageId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructurePackageId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructurePackageName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageName") })) }
            criteria.runtimeInfrastructurePackageVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageVersion") })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.bootstrapCommand?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("bootstrapCommand") })) }
            criteria.nodeLabelCommand?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("nodeLabelCommand") })) }
            criteria.nodeTaintCommand?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("nodeTaintCommand") })) }
            criteria.runtimeAgentNodeSelectorYaml?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeAgentNodeSelectorYaml") })) }
            criteria.runtimeAgentTolerationsYaml?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeAgentTolerationsYaml") })) }
            criteria.bootstrapConfigYaml?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("bootstrapConfigYaml") })) }
            criteria.runtimeEnvironmentType?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeEnvironmentType") })) }
            criteria.agentInstallMode?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("agentInstallMode") })) }
            criteria.expectedNodeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<Int>> { root -> root.get("expectedNodeCount") })) }
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeInstallationGuideReadModelEntity> =
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
        field: Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<X>>
    ): Specification<RuntimeInstallationGuideReadModelEntity> =
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

    private fun RuntimeInstallationGuideReadModelEntity.toProjection(): RuntimeInstallationGuideReadModelProjection =
        RuntimeInstallationGuideReadModelProjection().also {
            it.runtimeInstallationPlanId = this@toProjection.runtimeInstallationPlanId
            it.organizationId = this@toProjection.organizationId
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeInfrastructureState = this@toProjection.runtimeInfrastructureState
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.bootstrapCommand = this@toProjection.bootstrapCommand
            it.nodeLabelCommand = this@toProjection.nodeLabelCommand
            it.nodeTaintCommand = this@toProjection.nodeTaintCommand
            it.runtimeAgentNodeSelectorYaml = this@toProjection.runtimeAgentNodeSelectorYaml
            it.runtimeAgentTolerationsYaml = this@toProjection.runtimeAgentTolerationsYaml
            it.bootstrapConfigYaml = this@toProjection.bootstrapConfigYaml
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.agentInstallMode = this@toProjection.agentInstallMode
            it.expectedNodeCount = this@toProjection.expectedNodeCount
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
