package tech.medo.systemnotification.systemnotificationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationSeverity
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.LongFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class SystemNotificationSummaryReadModelQuery

class SystemNotificationSummaryReadModelCriteria {
    var recipientUserId: StringFilter? = null
    var unreadCount: LongFilter? = null
    var latestNotificationId: StringFilter? = null
    var latestSeverity: Filter<NotificationSeverity>? = null
    var latestPublishedAt: RangeFilter<LocalDateTime>? = null
    var messageKey: StringFilter? = null
    var fallbackTitle: StringFilter? = null
    var fallbackBody: StringFilter? = null
    var toastPolicy: Filter<NotificationToastPolicy>? = null
    var hasUnreadError: BooleanFilter? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class SystemNotificationSummaryReadModelProjection : MetadataProjection {
    var recipientUserId: String? = null
    var unreadCount: Long? = null
    var latestNotificationId: UUID? = null
    var latestSeverity: NotificationSeverity? = null
    var latestPublishedAt: LocalDateTime? = null
    var messageKey: String? = null
    var fallbackTitle: String? = null
    var fallbackBody: String? = null
    var toastPolicy: NotificationToastPolicy? = null
    var hasUnreadError: Boolean? = null
    override var projectionUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun SystemNotificationSummaryReadModelProjection.toReadModel(): SystemNotificationSummaryReadModel =
    SystemNotificationSummaryReadModel(
    recipientUserId = recipientUserId,
    unreadCount = unreadCount,
    latestNotificationId = latestNotificationId,
    latestSeverity = latestSeverity,
    latestPublishedAt = latestPublishedAt,
    messageKey = messageKey,
    fallbackTitle = fallbackTitle,
    fallbackBody = fallbackBody,
    toastPolicy = toastPolicy,
    hasUnreadError = hasUnreadError,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface SystemNotificationSummaryReadModelRepository {
    fun findAll(pageable: Pageable): Page<SystemNotificationSummaryReadModel>
    fun findAllByCriteria(criteria: SystemNotificationSummaryReadModelCriteria?, pageable: Pageable): Page<SystemNotificationSummaryReadModel>
    fun findById(id: String): SystemNotificationSummaryReadModel?
    fun findProjectionById(id: String): SystemNotificationSummaryReadModelProjection?
    fun save(projection: SystemNotificationSummaryReadModelProjection)
}

data class SystemNotificationSummaryReadModel(
    val recipientUserId: String?,
    val unreadCount: Long?,
    val latestNotificationId: UUID?,
    val latestSeverity: NotificationSeverity?,
    val latestPublishedAt: LocalDateTime?,
    val messageKey: String?,
    val fallbackTitle: String?,
    val fallbackBody: String?,
    val toastPolicy: NotificationToastPolicy?,
    val hasUnreadError: Boolean?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
