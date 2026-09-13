package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.userroleassignmentcatalogreadmodel

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

import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModel
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelProjection
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.toReadModel

@Service
class UserRoleAssignmentCatalogReadModelQueryService(
    private val repository: SpringDataUserRoleAssignmentCatalogReadModelRepository
) : QueryService<UserRoleAssignmentCatalogReadModelEntity>() {
    fun findByCriteria(criteria: UserRoleAssignmentCatalogReadModelCriteria?, pageable: Pageable): Page<UserRoleAssignmentCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: UserRoleAssignmentCatalogReadModelCriteria?): Specification<UserRoleAssignmentCatalogReadModelEntity> {
        var specification = Specification.where<UserRoleAssignmentCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.userAccountId?.let { specification = specification.and(buildSpecification(it, Function<Root<UserRoleAssignmentCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("userAccountId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.username?.let { specification = specification.and(buildSpecification(it, Function<Root<UserRoleAssignmentCatalogReadModelEntity>, Expression<String>> { root -> root.get("username") })) }
            criteria.roleCode?.let { specification = specification.and(buildSpecification(it, Function<Root<UserRoleAssignmentCatalogReadModelEntity>, Expression<String>> { root -> root.get("roleCode") })) }
            criteria.roleName?.let { specification = specification.and(buildSpecification(it, Function<Root<UserRoleAssignmentCatalogReadModelEntity>, Expression<String>> { root -> root.get("roleName") })) }
        }
        return specification
    }

    private fun UserRoleAssignmentCatalogReadModelEntity.toProjection(): UserRoleAssignmentCatalogReadModelProjection =
        UserRoleAssignmentCatalogReadModelProjection().also {
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.roleCode = this@toProjection.roleCode
            it.roleName = this@toProjection.roleName
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
