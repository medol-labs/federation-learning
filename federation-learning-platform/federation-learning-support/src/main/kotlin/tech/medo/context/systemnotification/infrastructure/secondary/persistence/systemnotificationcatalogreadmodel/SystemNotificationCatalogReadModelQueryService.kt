package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationcatalogreadmodel

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
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationCatalogReadModelCriteria
import tech.medo.systemnotification.systemnotificationcatalog.SystemNotificationCatalogReadModelProjection

@Service
class SystemNotificationCatalogReadModelQueryService() : QueryService<SystemNotificationCatalogReadModelEntity>() {
    fun createSpecification(criteria: SystemNotificationCatalogReadModelCriteria?): Specification<SystemNotificationCatalogReadModelEntity> {
        var specification = Specification.where<SystemNotificationCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.notificationId?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("notificationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.recipientUserId?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("recipientUserId") })) }
            criteria.recipientRole?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("recipientRole") })) }
            criteria.category?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<NotificationCategory>> { root -> root.get("category") })) }
            criteria.severity?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<NotificationSeverity>> { root -> root.get("severity") })) }
            criteria.messageKey?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("messageKey") })) }
            criteria.messageArgsJson?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("messageArgsJson") })) }
            criteria.fallbackTitle?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("fallbackTitle") })) }
            criteria.fallbackBody?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("fallbackBody") })) }
            criteria.targetType?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("targetType") })) }
            criteria.targetId?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("targetId") })) }
            criteria.targetRoute?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<String>> { root -> root.get("targetRoute") })) }
            criteria.toastPolicy?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<NotificationToastPolicy>> { root -> root.get("toastPolicy") })) }
            criteria.publishedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("publishedAt") })) }
            criteria.readAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("readAt") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<SystemNotificationStateEnum>> { root -> root.get("state") })) }
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<SystemNotificationCatalogReadModelEntity> =
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
        field: Function<Root<SystemNotificationCatalogReadModelEntity>, Expression<X>>
    ): Specification<SystemNotificationCatalogReadModelEntity> =
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

    private fun SystemNotificationCatalogReadModelEntity.toProjection(): SystemNotificationCatalogReadModelProjection =
        SystemNotificationCatalogReadModelProjection().also {
            it.notificationId = this@toProjection.notificationId
            it.recipientUserId = this@toProjection.recipientUserId
            it.recipientRole = this@toProjection.recipientRole
            it.category = this@toProjection.category
            it.severity = this@toProjection.severity
            it.messageKey = this@toProjection.messageKey
            it.messageArgsJson = this@toProjection.messageArgsJson
            it.fallbackTitle = this@toProjection.fallbackTitle
            it.fallbackBody = this@toProjection.fallbackBody
            it.targetType = this@toProjection.targetType
            it.targetId = this@toProjection.targetId
            it.targetRoute = this@toProjection.targetRoute
            it.toastPolicy = this@toProjection.toastPolicy
            it.publishedAt = this@toProjection.publishedAt
            it.readAt = this@toProjection.readAt
            it.state = this@toProjection.state
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
