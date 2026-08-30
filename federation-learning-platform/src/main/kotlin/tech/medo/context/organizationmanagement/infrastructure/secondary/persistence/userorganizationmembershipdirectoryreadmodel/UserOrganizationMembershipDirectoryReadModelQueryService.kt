package tech.medo.organizationmanagement.infrastructure.secondary.persistence.userorganizationmembershipdirectoryreadmodel

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
import tech.medo.organizationmanagement.domain.states.UserOrganizationMembershipStateEnum;

import tech.medo.organizationmanagement.userorganizationmembershipdirectory.UserOrganizationMembershipDirectoryReadModel
import tech.medo.organizationmanagement.userorganizationmembershipdirectory.UserOrganizationMembershipDirectoryReadModelCriteria
import tech.medo.organizationmanagement.userorganizationmembershipdirectory.UserOrganizationMembershipDirectoryReadModelProjection
import tech.medo.organizationmanagement.userorganizationmembershipdirectory.toReadModel

@Service
class UserOrganizationMembershipDirectoryReadModelQueryService(
    private val repository: SpringDataUserOrganizationMembershipDirectoryReadModelRepository
) : QueryService<UserOrganizationMembershipDirectoryReadModelEntity>() {
    fun findByCriteria(criteria: UserOrganizationMembershipDirectoryReadModelCriteria?, pageable: Pageable): Page<UserOrganizationMembershipDirectoryReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: UserOrganizationMembershipDirectoryReadModelCriteria?): Specification<UserOrganizationMembershipDirectoryReadModelEntity> {
        var specification = Specification.where<UserOrganizationMembershipDirectoryReadModelEntity>(null)
        if (criteria != null) {
            criteria.userOrganizationMembershipId?.let { specification = specification.and(buildSpecification(it, Function<Root<UserOrganizationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("userOrganizationMembershipId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.userAccountId?.let { specification = specification.and(buildSpecification(it, Function<Root<UserOrganizationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("userAccountId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.username?.let { specification = specification.and(buildSpecification(it, Function<Root<UserOrganizationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("username") })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<UserOrganizationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<UserOrganizationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.organizationUserRole?.let { specification = specification.and(buildSpecification(it, Function<Root<UserOrganizationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("organizationUserRole") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<UserOrganizationMembershipDirectoryReadModelEntity>, Expression<UserOrganizationMembershipStateEnum>> { root -> root.get("state") })) }
        }
        return specification
    }

    private fun UserOrganizationMembershipDirectoryReadModelEntity.toProjection(): UserOrganizationMembershipDirectoryReadModelProjection =
        UserOrganizationMembershipDirectoryReadModelProjection().also {
            it.userOrganizationMembershipId = this@toProjection.userOrganizationMembershipId
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.organizationUserRole = this@toProjection.organizationUserRole
            it.state = this@toProjection.state
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
