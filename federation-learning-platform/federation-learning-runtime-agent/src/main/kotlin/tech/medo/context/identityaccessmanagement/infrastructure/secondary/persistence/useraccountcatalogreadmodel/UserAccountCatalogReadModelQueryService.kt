package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.useraccountcatalogreadmodel

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
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<UserAccountCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<UserAccountCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<UserAccountCatalogReadModelEntity> =
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
        field: Function<Root<UserAccountCatalogReadModelEntity>, Expression<X>>
    ): Specification<UserAccountCatalogReadModelEntity> =
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

    private fun UserAccountCatalogReadModelEntity.toProjection(): UserAccountCatalogReadModelProjection =
        UserAccountCatalogReadModelProjection().also {
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.providerSubject = this@toProjection.providerSubject
            it.userSource = this@toProjection.userSource
            it.passwordHash = this@toProjection.passwordHash
            it.active = this@toProjection.active
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
