package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.permissioncatalogreadmodel

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

import tech.medo.identityaccessmanagement.identityaccesscatalogs.PermissionCatalogReadModel
import tech.medo.identityaccessmanagement.identityaccesscatalogs.PermissionCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.identityaccesscatalogs.PermissionCatalogReadModelProjection
import tech.medo.identityaccessmanagement.identityaccesscatalogs.toReadModel

@Service
class PermissionCatalogReadModelQueryService(
    private val repository: SpringDataPermissionCatalogReadModelRepository
) : QueryService<PermissionCatalogReadModelEntity>() {
    fun findByCriteria(criteria: PermissionCatalogReadModelCriteria?, pageable: Pageable): Page<PermissionCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: PermissionCatalogReadModelCriteria?): Specification<PermissionCatalogReadModelEntity> {
        var specification = Specification.where<PermissionCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.permissionId?.let { specification = specification.and(buildSpecification(it, Function<Root<PermissionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("permissionId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.permissionCode?.let { specification = specification.and(buildSpecification(it, Function<Root<PermissionCatalogReadModelEntity>, Expression<String>> { root -> root.get("permissionCode") })) }
            criteria.permissionName?.let { specification = specification.and(buildSpecification(it, Function<Root<PermissionCatalogReadModelEntity>, Expression<String>> { root -> root.get("permissionName") })) }
            criteria.description?.let { specification = specification.and(buildSpecification(it, Function<Root<PermissionCatalogReadModelEntity>, Expression<String>> { root -> root.get("description") })) }
        }
        return specification
    }

    private fun PermissionCatalogReadModelEntity.toProjection(): PermissionCatalogReadModelProjection =
        PermissionCatalogReadModelProjection().also {
            it.permissionId = this@toProjection.permissionId
            it.permissionCode = this@toProjection.permissionCode
            it.permissionName = this@toProjection.permissionName
            it.description = this@toProjection.description
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
