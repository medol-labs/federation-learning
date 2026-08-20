package tech.medo.organizationmanagement.infrastructure.secondary.persistence.organizationdirectoryreadmodel

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
import tech.medo.organizationmanagement.domain.types.OrganizationType;
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum;

import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModel
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelCriteria
import tech.medo.organizationmanagement.organizationdirectory.OrganizationDirectoryReadModelProjection
import tech.medo.organizationmanagement.organizationdirectory.toReadModel

@Service
class OrganizationDirectoryReadModelQueryService(
    private val repository: SpringDataOrganizationDirectoryReadModelRepository
) : QueryService<OrganizationDirectoryReadModelEntity>() {
    fun findByCriteria(criteria: OrganizationDirectoryReadModelCriteria?, pageable: Pageable): Page<OrganizationDirectoryReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: OrganizationDirectoryReadModelCriteria?): Specification<OrganizationDirectoryReadModelEntity> {
        var specification = Specification.where<OrganizationDirectoryReadModelEntity>(null)
        if (criteria != null) {
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<OrganizationDirectoryReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<OrganizationDirectoryReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.organizationType?.let { specification = specification.and(buildSpecification(it, Function<Root<OrganizationDirectoryReadModelEntity>, Expression<OrganizationType>> { root -> root.get("organizationType") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<OrganizationDirectoryReadModelEntity>, Expression<OrganizationStateEnum>> { root -> root.get("state") })) }
            criteria.approvedDatasetCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<OrganizationDirectoryReadModelEntity>, Expression<Int>> { root -> root.get("approvedDatasetCount") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<OrganizationDirectoryReadModelEntity>, Expression<X>>
    ): Specification<OrganizationDirectoryReadModelEntity> =
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

    private fun OrganizationDirectoryReadModelEntity.toProjection(): OrganizationDirectoryReadModelProjection =
        OrganizationDirectoryReadModelProjection().also {
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.organizationType = this@toProjection.organizationType
            it.state = this@toProjection.state
            it.approvedDatasetCount = this@toProjection.approvedDatasetCount
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
