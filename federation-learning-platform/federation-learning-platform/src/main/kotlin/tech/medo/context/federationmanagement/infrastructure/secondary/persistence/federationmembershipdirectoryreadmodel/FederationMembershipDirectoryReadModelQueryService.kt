package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationmembershipdirectoryreadmodel

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
import java.time.LocalDateTime

import java.util.UUID

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
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<FederationMembershipDirectoryReadModelEntity> =
        Specification { root, _, builder ->
            val expression = field.apply(root)
            val predicates = mutableListOf<jakarta.persistence.criteria.Predicate>()
            filter.getEquals()?.let { predicates.add(builder.equal(expression, localDateTimeValue(it))) }
            filter.getNotEquals()?.let { predicates.add(builder.notEqual(expression, localDateTimeValue(it))) }
            filter.getSpecified()?.let { predicates.add(if (it) builder.isNotNull(expression) else builder.isNull(expression)) }
            (filter.getIn() as List<*>?)?.takeIf { it.isNotEmpty() }?.let { predicates.add(expression.`in`(it.map { value -> localDateTimeValue(value) })) }
            (filter.getNotIn() as List<*>?)?.takeIf { it.isNotEmpty() }?.let { predicates.add(builder.not(expression.`in`(it.map { value -> localDateTimeValue(value) }))) }
            filter.getGreaterThan()?.let { predicates.add(builder.greaterThan(expression, localDateTimeValue(it))) }
            filter.getGreaterThanOrEqual()?.let { predicates.add(builder.greaterThanOrEqualTo(expression, localDateTimeValue(it))) }
            filter.getLessThan()?.let { predicates.add(builder.lessThan(expression, localDateTimeValue(it))) }
            filter.getLessThanOrEqual()?.let { predicates.add(builder.lessThanOrEqualTo(expression, localDateTimeValue(it))) }
            builder.and(*predicates.toTypedArray())
        }

    private fun localDateTimeValue(value: Any?): LocalDateTime =
        when (value) {
            is LocalDateTime -> value
            null -> throw IllegalArgumentException("LocalDateTime filter value is required.")
            else -> value.toString().let { raw ->
                if (raw.all { it.isDigit() }) {
                    java.time.Instant.ofEpochMilli(raw.toLong()).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
                } else {
                    LocalDateTime.parse(raw)
                }
            }
        }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<FederationMembershipDirectoryReadModelEntity>, Expression<X>>
    ): Specification<FederationMembershipDirectoryReadModelEntity> =
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

    private fun FederationMembershipDirectoryReadModelEntity.toProjection(): FederationMembershipDirectoryReadModelProjection =
        FederationMembershipDirectoryReadModelProjection().also {
            it.federationId = this@toProjection.federationId
            it.organizationId = this@toProjection.organizationId
            it.federationName = this@toProjection.federationName
            it.organizationName = this@toProjection.organizationName
            it.membershipStatus = this@toProjection.membershipStatus
            it.invitationNote = this@toProjection.invitationNote
            it.approvalNote = this@toProjection.approvalNote
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
