package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.useraccountcatalogreadmodel

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

import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModel
import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModelProjection
import tech.medo.identityaccessmanagement.useraccountcatalogs.toReadModel

@Service
class UserAccountCatalogReadModelQueryService(
    private val repository: SpringDataUserAccountCatalogReadModelRepository
) : QueryService<UserAccountCatalogReadModelEntity>() {
    fun findByCriteria(criteria: UserAccountCatalogReadModelCriteria?, pageable: Pageable): Page<UserAccountCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: UserAccountCatalogReadModelCriteria?): Specification<UserAccountCatalogReadModelEntity> {
        var specification = Specification.where<UserAccountCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.userAccountId?.let { specification = specification.and(buildSpecification(it, Function<Root<UserAccountCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("userAccountId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.username?.let { specification = specification.and(buildSpecification(it, Function<Root<UserAccountCatalogReadModelEntity>, Expression<String>> { root -> root.get("username") })) }
            criteria.providerSubject?.let { specification = specification.and(buildSpecification(it, Function<Root<UserAccountCatalogReadModelEntity>, Expression<String>> { root -> root.get("providerSubject") })) }
            criteria.userSource?.let { specification = specification.and(buildSpecification(it, Function<Root<UserAccountCatalogReadModelEntity>, Expression<String>> { root -> root.get("userSource") })) }
            criteria.passwordHash?.let { specification = specification.and(buildSpecification(it, Function<Root<UserAccountCatalogReadModelEntity>, Expression<String>> { root -> root.get("passwordHash") })) }
            criteria.active?.let { specification = specification.and(buildSpecification(it, Function<Root<UserAccountCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("active") })) }
        }
        return specification
    }

    private fun UserAccountCatalogReadModelEntity.toProjection(): UserAccountCatalogReadModelProjection =
        UserAccountCatalogReadModelProjection().also {
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.providerSubject = this@toProjection.providerSubject
            it.userSource = this@toProjection.userSource
            it.passwordHash = this@toProjection.passwordHash
            it.active = this@toProjection.active
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
