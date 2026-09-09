package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructurepackagecatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import tech.jhipster.service.QueryService
import java.util.function.Function
import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum;

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
        }
        return specification
    }

    private fun RuntimeInfrastructurePackageCatalogReadModelEntity.toProjection(): RuntimeInfrastructurePackageCatalogReadModelProjection =
        RuntimeInfrastructurePackageCatalogReadModelProjection().also {
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.packageName = this@toProjection.packageName
            it.packageVersion = this@toProjection.packageVersion
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.state = this@toProjection.state
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
