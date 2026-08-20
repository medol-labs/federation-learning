package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructureaccessviewreadmodel

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
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;

import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModel
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.toReadModel

@Service
class RuntimeInfrastructureAccessViewReadModelQueryService(
    private val repository: SpringDataRuntimeInfrastructureAccessViewReadModelRepository
) : QueryService<RuntimeInfrastructureAccessViewReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeInfrastructureAccessViewReadModelCriteria?, pageable: Pageable): Page<RuntimeInfrastructureAccessViewReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeInfrastructureAccessViewReadModelCriteria?): Specification<RuntimeInfrastructureAccessViewReadModelEntity> {
        var specification = Specification.where<RuntimeInfrastructureAccessViewReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeInfrastructureId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructureId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInstallationPlanId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInstallationPlanId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructurePackageId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructurePackageId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructurePackageName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageName") })) }
            criteria.runtimeInfrastructurePackageVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageVersion") })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.runtimeDeploymentTargetType?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeDeploymentTargetType") })) }
            criteria.runtimeEnvironmentType?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeEnvironmentType") })) }
            criteria.agentInstallMode?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("agentInstallMode") })) }
            criteria.expectedNodeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<Int>> { root -> root.get("expectedNodeCount") })) }
            criteria.runtimeAgentId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeAgentId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeAgentVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("runtimeAgentVersion") })) }
            criteria.infrastructureVerifiedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("infrastructureVerifiedAt") })) }
            criteria.infrastructureVerificationFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("infrastructureVerificationFailedAt") })) }
            criteria.infrastructureVerificationFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("infrastructureVerificationFailureReason") })) }
            criteria.agentReadyAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("agentReadyAt") })) }
            criteria.agentDeploymentFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("agentDeploymentFailedAt") })) }
            criteria.agentDeploymentFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("agentDeploymentFailureReason") })) }
            criteria.agentDeploymentRetryFailedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("agentDeploymentRetryFailedAt") })) }
            criteria.agentDeploymentRetryFailureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<String>> { root -> root.get("agentDeploymentRetryFailureReason") })) }
            criteria.connectedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("connectedAt") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<RuntimeInfrastructureStateEnum>> { root -> root.get("state") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeInfrastructureAccessViewReadModelEntity> =
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
        field: Function<Root<RuntimeInfrastructureAccessViewReadModelEntity>, Expression<X>>
    ): Specification<RuntimeInfrastructureAccessViewReadModelEntity> =
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

    private fun RuntimeInfrastructureAccessViewReadModelEntity.toProjection(): RuntimeInfrastructureAccessViewReadModelProjection =
        RuntimeInfrastructureAccessViewReadModelProjection().also {
            it.runtimeInfrastructureId = this@toProjection.runtimeInfrastructureId
            it.organizationId = this@toProjection.organizationId
            it.runtimeInstallationPlanId = this@toProjection.runtimeInstallationPlanId
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.runtimeDeploymentTargetType = this@toProjection.runtimeDeploymentTargetType
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.agentInstallMode = this@toProjection.agentInstallMode
            it.expectedNodeCount = this@toProjection.expectedNodeCount
            it.runtimeAgentId = this@toProjection.runtimeAgentId
            it.runtimeAgentVersion = this@toProjection.runtimeAgentVersion
            it.infrastructureVerifiedAt = this@toProjection.infrastructureVerifiedAt
            it.infrastructureVerificationFailedAt = this@toProjection.infrastructureVerificationFailedAt
            it.infrastructureVerificationFailureReason = this@toProjection.infrastructureVerificationFailureReason
            it.agentReadyAt = this@toProjection.agentReadyAt
            it.agentDeploymentFailedAt = this@toProjection.agentDeploymentFailedAt
            it.agentDeploymentFailureReason = this@toProjection.agentDeploymentFailureReason
            it.agentDeploymentRetryFailedAt = this@toProjection.agentDeploymentRetryFailedAt
            it.agentDeploymentRetryFailureReason = this@toProjection.agentDeploymentRetryFailureReason
            it.connectedAt = this@toProjection.connectedAt
            it.state = this@toProjection.state
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
