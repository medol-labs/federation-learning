package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationoverviewreadmodel

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
import tech.medo.federationmanagement.domain.states.FederationStateEnum;

import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModel
import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelCriteria
import tech.medo.federationmanagement.federationoverview.FederationOverviewReadModelProjection
import tech.medo.federationmanagement.federationoverview.toReadModel

@Service
class FederationOverviewReadModelQueryService(
    private val repository: SpringDataFederationOverviewReadModelRepository
) : QueryService<FederationOverviewReadModelEntity>() {
    fun findByCriteria(criteria: FederationOverviewReadModelCriteria?, pageable: Pageable): Page<FederationOverviewReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: FederationOverviewReadModelCriteria?): Specification<FederationOverviewReadModelEntity> {
        var specification = Specification.where<FederationOverviewReadModelEntity>(null)
        if (criteria != null) {
            criteria.federationId?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("federationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationName?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<String>> { root -> root.get("federationName") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<FederationStateEnum>> { root -> root.get("state") })) }
            criteria.minimumParticipantCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<Int>> { root -> root.get("minimumParticipantCount") })) }
            criteria.activeMemberCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<Int>> { root -> root.get("activeMemberCount") })) }
            criteria.pendingInvitationCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<Int>> { root -> root.get("pendingInvitationCount") })) }
            criteria.activeRuntimeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<Int>> { root -> root.get("activeRuntimeCount") })) }
            criteria.activeTrainingJobCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<FederationOverviewReadModelEntity>, Expression<Int>> { root -> root.get("activeTrainingJobCount") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<FederationOverviewReadModelEntity>, Expression<X>>
    ): Specification<FederationOverviewReadModelEntity> =
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

    private fun FederationOverviewReadModelEntity.toProjection(): FederationOverviewReadModelProjection =
        FederationOverviewReadModelProjection().also {
            it.federationId = this@toProjection.federationId
            it.federationName = this@toProjection.federationName
            it.state = this@toProjection.state
            it.minimumParticipantCount = this@toProjection.minimumParticipantCount
            it.activeMemberCount = this@toProjection.activeMemberCount
            it.pendingInvitationCount = this@toProjection.pendingInvitationCount
            it.activeRuntimeCount = this@toProjection.activeRuntimeCount
            it.activeTrainingJobCount = this@toProjection.activeTrainingJobCount
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
