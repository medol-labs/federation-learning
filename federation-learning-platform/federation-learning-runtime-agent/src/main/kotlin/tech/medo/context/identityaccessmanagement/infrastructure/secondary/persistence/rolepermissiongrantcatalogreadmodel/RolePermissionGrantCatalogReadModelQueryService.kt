package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolepermissiongrantcatalogreadmodel

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

import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModel
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelProjection
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.toReadModel

@Service
class RolePermissionGrantCatalogReadModelQueryService(
    private val repository: SpringDataRolePermissionGrantCatalogReadModelRepository
) : QueryService<RolePermissionGrantCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RolePermissionGrantCatalogReadModelCriteria?, pageable: Pageable): Page<RolePermissionGrantCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RolePermissionGrantCatalogReadModelCriteria?): Specification<RolePermissionGrantCatalogReadModelEntity> {
        var specification = Specification.where<RolePermissionGrantCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.roleId?.let { specification = specification.and(buildSpecification(it, Function<Root<RolePermissionGrantCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roleId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roleCode?.let { specification = specification.and(buildSpecification(it, Function<Root<RolePermissionGrantCatalogReadModelEntity>, Expression<String>> { root -> root.get("roleCode") })) }
            criteria.roleName?.let { specification = specification.and(buildSpecification(it, Function<Root<RolePermissionGrantCatalogReadModelEntity>, Expression<String>> { root -> root.get("roleName") })) }
            criteria.permissionCode?.let { specification = specification.and(buildSpecification(it, Function<Root<RolePermissionGrantCatalogReadModelEntity>, Expression<String>> { root -> root.get("permissionCode") })) }
            criteria.permissionName?.let { specification = specification.and(buildSpecification(it, Function<Root<RolePermissionGrantCatalogReadModelEntity>, Expression<String>> { root -> root.get("permissionName") })) }
        }
        return specification
    }

    private fun RolePermissionGrantCatalogReadModelEntity.toProjection(): RolePermissionGrantCatalogReadModelProjection =
        RolePermissionGrantCatalogReadModelProjection().also {
            it.roleId = this@toProjection.roleId
            it.roleCode = this@toProjection.roleCode
            it.roleName = this@toProjection.roleName
            it.permissionCode = this@toProjection.permissionCode
            it.permissionName = this@toProjection.permissionName
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
