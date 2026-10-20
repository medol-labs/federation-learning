package tech.medo.systemnotification.systemnotificationcatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class SystemNotificationCatalogReadModelQuery

class SystemNotificationCatalogReadModelCriteria {
    var notificationId: StringFilter? = null
    var recipientUserId: StringFilter? = null
    var recipientRole: StringFilter? = null
    var category: Filter<NotificationCategory>? = null
    var severity: Filter<NotificationSeverity>? = null
    var messageKey: StringFilter? = null
    var messageArgsJson: StringFilter? = null
    var fallbackTitle: StringFilter? = null
    var fallbackBody: StringFilter? = null
    var targetType: StringFilter? = null
    var targetId: StringFilter? = null
    var targetRoute: StringFilter? = null
    var toastPolicy: Filter<NotificationToastPolicy>? = null
    var publishedAt: RangeFilter<LocalDateTime>? = null
    var readAt: RangeFilter<LocalDateTime>? = null
    var state: Filter<SystemNotificationStateEnum>? = null
    var projectionUpdatedAt: RangeFilter<LocalDateTime>? = null
}


class SystemNotificationCatalogReadModelProjection : MetadataProjection {
    var notificationId: UUID? = null
    var recipientUserId: String? = null
    var recipientRole: String? = null
    var category: NotificationCategory? = null
    var severity: NotificationSeverity? = null
    var messageKey: String? = null
    var messageArgsJson: String? = null
    var fallbackTitle: String? = null
    var fallbackBody: String? = null
    var targetType: String? = null
    var targetId: String? = null
    var targetRoute: String? = null
    var toastPolicy: NotificationToastPolicy? = null
    var publishedAt: LocalDateTime? = null
    var readAt: LocalDateTime? = null
    var state: SystemNotificationStateEnum? = null
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}

fun SystemNotificationCatalogReadModelProjection.toReadModel(): SystemNotificationCatalogReadModel =
    SystemNotificationCatalogReadModel(
    notificationId = notificationId,
    recipientUserId = recipientUserId,
    recipientRole = recipientRole,
    category = category,
    severity = severity,
    messageKey = messageKey,
    messageArgsJson = messageArgsJson,
    fallbackTitle = fallbackTitle,
    fallbackBody = fallbackBody,
    targetType = targetType,
    targetId = targetId,
    targetRoute = targetRoute,
    toastPolicy = toastPolicy,
    publishedAt = publishedAt,
    readAt = readAt,
    state = state,
    projectionUpdatedAt = projectionUpdatedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface SystemNotificationCatalogReadModelRepository {
    fun findAllByFilter(recipientUserId: String?, recipientRole: String?, category: NotificationCategory?, severity: NotificationSeverity?, state: SystemNotificationStateEnum?, pageable: Pageable): Page<SystemNotificationCatalogReadModel>
    fun findAllByCriteria(criteria: SystemNotificationCatalogReadModelCriteria?, pageable: Pageable): Page<SystemNotificationCatalogReadModel>
    fun findById(id: UUID): SystemNotificationCatalogReadModel?
    fun findByIdWithAccessPolicy(id: UUID): SystemNotificationCatalogReadModel?
    fun findProjectionById(id: UUID): SystemNotificationCatalogReadModelProjection?
    fun save(projection: SystemNotificationCatalogReadModelProjection)
}

data class SystemNotificationCatalogReadModel(
    val notificationId: UUID?,
    val recipientUserId: String?,
    val recipientRole: String?,
    val category: NotificationCategory?,
    val severity: NotificationSeverity?,
    val messageKey: String?,
    val messageArgsJson: String?,
    val fallbackTitle: String?,
    val fallbackBody: String?,
    val targetType: String?,
    val targetId: String?,
    val targetRoute: String?,
    val toastPolicy: NotificationToastPolicy?,
    val publishedAt: LocalDateTime?,
    val readAt: LocalDateTime?,
    val state: SystemNotificationStateEnum?,
    val projectionUpdatedAt: LocalDateTime? = null,
    val userId: String? = null,
    val sessionId: String? = null,
    val correlationId: String? = null,
    val causationId: String? = null,
    val traceId: String? = null,
    val tenantId: String? = null
)
