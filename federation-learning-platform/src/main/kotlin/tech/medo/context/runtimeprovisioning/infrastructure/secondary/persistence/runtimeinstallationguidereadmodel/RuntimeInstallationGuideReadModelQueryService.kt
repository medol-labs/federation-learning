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
import java.util.UUID;

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
            criteria.runtimeInfrastructurePackageId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeInfrastructurePackageId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeInfrastructurePackageName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageName") })) }
            criteria.runtimeInfrastructurePackageVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeInfrastructurePackageVersion") })) }
            criteria.infrastructureInstallGuide?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("infrastructureInstallGuide") })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.bootstrapCommand?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("bootstrapCommand") })) }
            criteria.runtimeDeploymentTargetType?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeDeploymentTargetType") })) }
            criteria.runtimeEnvironmentType?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("runtimeEnvironmentType") })) }
            criteria.agentInstallMode?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("agentInstallMode") })) }
            criteria.installProfile?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("installProfile") })) }
            criteria.architecture?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<String>> { root -> root.get("architecture") })) }
            criteria.expectedNodeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeInstallationGuideReadModelEntity>, Expression<Int>> { root -> root.get("expectedNodeCount") })) }
        }
        return specification
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
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.runtimeInfrastructurePackageName = this@toProjection.runtimeInfrastructurePackageName
            it.runtimeInfrastructurePackageVersion = this@toProjection.runtimeInfrastructurePackageVersion
            it.infrastructureInstallGuide = this@toProjection.infrastructureInstallGuide
            it.organizationName = this@toProjection.organizationName
            it.runtimeName = this@toProjection.runtimeName
            it.bootstrapCommand = this@toProjection.bootstrapCommand
            it.runtimeDeploymentTargetType = this@toProjection.runtimeDeploymentTargetType
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.agentInstallMode = this@toProjection.agentInstallMode
            it.installProfile = this@toProjection.installProfile
            it.architecture = this@toProjection.architecture
            it.expectedNodeCount = this@toProjection.expectedNodeCount
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
