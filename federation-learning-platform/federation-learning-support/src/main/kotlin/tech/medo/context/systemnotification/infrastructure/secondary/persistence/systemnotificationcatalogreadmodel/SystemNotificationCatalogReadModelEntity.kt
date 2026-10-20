package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum

@Entity
@Table(name = "system_notification_catalog")
class SystemNotificationCatalogReadModelEntity : MetadataProjection {
    @Id
    var notificationId: UUID? = null
    var recipientUserId: String? = null
    var recipientRole: String? = null
    @Enumerated(EnumType.STRING)
    var category: NotificationCategory? = null
    @Enumerated(EnumType.STRING)
    var severity: NotificationSeverity? = null
    var messageKey: String? = null
    @Column(columnDefinition = "text")
    var messageArgsJson: String? = null
    var fallbackTitle: String? = null
    @Column(columnDefinition = "text")
    var fallbackBody: String? = null
    var targetType: String? = null
    var targetId: String? = null
    var targetRoute: String? = null
    @Enumerated(EnumType.STRING)
    var toastPolicy: NotificationToastPolicy? = null
    var publishedAt: LocalDateTime? = null
    var readAt: LocalDateTime? = null
    @Enumerated(EnumType.STRING)
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
