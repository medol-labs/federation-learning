package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationmembershipdirectoryreadmodel

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

import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModel
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelCriteria
import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelProjection
import tech.medo.federationmanagement.federationmembershipdirectory.toReadModel

@Service
class FederationMembershipDirectoryReadModelQueryService(
    private val repository: SpringDataFederationMembershipDirectoryReadModelRepository
) : QueryService<FederationMembershipDirectoryReadModelEntity>() {
    fun findByCriteria(criteria: FederationMembershipDirectoryReadModelCriteria?, pageable: Pageable): Page<FederationMembershipDirectoryReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: FederationMembershipDirectoryReadModelCriteria?): Specification<FederationMembershipDirectoryReadModelEntity> {
        var specification = Specification.where<FederationMembershipDirectoryReadModelEntity>(null)
        if (criteria != null) {
            criteria.federationId?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("federationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationName?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("federationName") })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.membershipStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("membershipStatus") })) }
            criteria.invitationNote?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("invitationNote") })) }
            criteria.approvalNote?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<String>> { root -> root.get("approvalNote") })) }
        }
        return specification
    }

    private fun FederationMembershipDirectoryReadModelEntity.toProjection(): FederationMembershipDirectoryReadModelProjection =
        FederationMembershipDirectoryReadModelProjection().also {
            it.federationId = this@toProjection.federationId
            it.organizationId = this@toProjection.organizationId
            it.federationName = this@toProjection.federationName
            it.organizationName = this@toProjection.organizationName
            it.membershipStatus = this@toProjection.membershipStatus
            it.invitationNote = this@toProjection.invitationNote
            it.approvalNote = this@toProjection.approvalNote
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
