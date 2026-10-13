package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationsummaryreadmodel

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
import tech.medo.systemnotification.domain.types.NotificationSeverity
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationSummaryReadModelCriteria
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationSummaryReadModelProjection

@Service
class SystemNotificationSummaryReadModelQueryService() : QueryService<SystemNotificationSummaryReadModelEntity>() {
    fun createSpecification(criteria: SystemNotificationSummaryReadModelCriteria?): Specification<SystemNotificationSummaryReadModelEntity> {
        var specification = Specification.where<SystemNotificationSummaryReadModelEntity>(null)
        if (criteria != null) {
            criteria.recipientUserId?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<String>> { root -> root.get("recipientUserId") })) }
            criteria.unreadCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<Long>> { root -> root.get("unreadCount") })) }
            criteria.latestNotificationId?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("latestNotificationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.latestSeverity?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<NotificationSeverity>> { root -> root.get("latestSeverity") })) }
            criteria.latestPublishedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("latestPublishedAt") })) }
            criteria.messageKey?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<String>> { root -> root.get("messageKey") })) }
            criteria.fallbackTitle?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<String>> { root -> root.get("fallbackTitle") })) }
            criteria.fallbackBody?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<String>> { root -> root.get("fallbackBody") })) }
            criteria.toastPolicy?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<NotificationToastPolicy>> { root -> root.get("toastPolicy") })) }
            criteria.hasUnreadError?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<Boolean>> { root -> root.get("hasUnreadError") })) }
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<SystemNotificationSummaryReadModelEntity> =
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
        field: Function<Root<SystemNotificationSummaryReadModelEntity>, Expression<X>>
    ): Specification<SystemNotificationSummaryReadModelEntity> =
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

    private fun SystemNotificationSummaryReadModelEntity.toProjection(): SystemNotificationSummaryReadModelProjection =
        SystemNotificationSummaryReadModelProjection().also {
            it.recipientUserId = this@toProjection.recipientUserId
            it.unreadCount = this@toProjection.unreadCount
            it.latestNotificationId = this@toProjection.latestNotificationId
            it.latestSeverity = this@toProjection.latestSeverity
            it.latestPublishedAt = this@toProjection.latestPublishedAt
            it.messageKey = this@toProjection.messageKey
            it.fallbackTitle = this@toProjection.fallbackTitle
            it.fallbackBody = this@toProjection.fallbackBody
            it.toastPolicy = this@toProjection.toastPolicy
            it.hasUnreadError = this@toProjection.hasUnreadError
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
