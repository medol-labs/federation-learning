package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationplancatalogreadmodel

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

import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.RuntimeInstallationPlanCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinstallationplancatalog.toReadModel

@Service
class RuntimeInstallationPlanCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeInstallationPlanCatalogReadModelRepository
) : QueryService<RuntimeInstallationPlanCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeInstallationPlanCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeInstallationPlanCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeInstallationPlanCatalogReadModelCriteria?): Specification<RuntimeInstallationPlanCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeInstallationPlanCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeInstallationPlanId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInstallationPlanId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.runtimeInfrastructurePackageId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructurePackageId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructurePackageName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageName") })) }
            criteria.runtimeInfrastructurePackageVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageVersion") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.agentInstallMode?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("agentInstallMode") })) }
            criteria.expectedNodeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<Int>> { root -> root.get("expectedNodeCount") })) }
            criteria.planStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("planStatus") })) }
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.preparedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("preparedAt") })) }
            criteria.preparedNodeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<Int>> { root -> root.get("preparedNodeCount") })) }
            criteria.observedNodeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<Int>> { root -> root.get("observedNodeCount") })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeAgentVersion") })) }
            criteria.plannedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("plannedAt") })) }
            criteria.verifiedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("verifiedAt") })) }
            criteria.verificationFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("verificationFailedAt") })) }
            criteria.verificationFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("verificationFailureReason") })) }
            criteria.agentReadyAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("agentReadyAt") })) }
            criteria.agentDeploymentFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("agentDeploymentFailedAt") })) }
            criteria.agentDeploymentFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("agentDeploymentFailureReason") })) }
            criteria.agentDeploymentRetryFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("agentDeploymentRetryFailedAt") })) }
            criteria.agentDeploymentRetryFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<String>> { root -> root.get("agentDeploymentRetryFailureReason") })) }
            criteria.lastConnectedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastConnectedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeInstallationPlanCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeInstallationPlanCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeInstallationPlanCatalogReadModelEntity> =
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

    private fun RuntimeInstallationPlanCatalogReadModelEntity.toProjection(): RuntimeInstallationPlanCatalogReadModelProjection =
        RuntimeInstallationPlanCatalogReadModelProjection().also {
            it.runtimeInstallationPlanId = this@toProjection.runtimeInstallationPlanId
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.runtimeName = this@toProjection.runtimeName
            it.agentInstallMode = this@toProjection.agentInstallMode
            it.expectedNodeCount = this@toProjection.expectedNodeCount
            it.planStatus = this@toProjection.planStatus
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.preparedAt = this@toProjection.preparedAt
            it.preparedNodeCount = this@toProjection.preparedNodeCount
            it.observedNodeCount = this@toProjection.observedNodeCount
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeAgentVersion = this@toProjection.runtimeAgentVersion
            it.plannedAt = this@toProjection.plannedAt
            it.verifiedAt = this@toProjection.verifiedAt
            it.verificationFailedAt = this@toProjection.verificationFailedAt
            it.verificationFailureReason = this@toProjection.verificationFailureReason
            it.agentReadyAt = this@toProjection.agentReadyAt
            it.agentDeploymentFailedAt = this@toProjection.agentDeploymentFailedAt
            it.agentDeploymentFailureReason = this@toProjection.agentDeploymentFailureReason
            it.agentDeploymentRetryFailedAt = this@toProjection.agentDeploymentRetryFailedAt
            it.agentDeploymentRetryFailureReason = this@toProjection.agentDeploymentRetryFailureReason
            it.lastConnectedAt = this@toProjection.lastConnectedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
