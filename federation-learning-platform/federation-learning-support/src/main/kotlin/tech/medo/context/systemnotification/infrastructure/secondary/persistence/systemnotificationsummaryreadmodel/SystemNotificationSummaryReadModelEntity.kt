package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationsummaryreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationSeverity
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.systemnotification.domain.types.NotificationToastPolicy

@Entity
@Table(name = "system_notification_summary")
class SystemNotificationSummaryReadModelEntity : MetadataProjection {
    @Id
    var recipientUserId: String? = null
    var unreadCount: Long? = null
    var latestNotificationId: UUID? = null
    @Enumerated(EnumType.STRING)
    var latestSeverity: NotificationSeverity? = null
    var latestPublishedAt: LocalDateTime? = null
    var messageKey: String? = null
    var fallbackTitle: String? = null
    @Column(columnDefinition = "text")
    var fallbackBody: String? = null
    @Enumerated(EnumType.STRING)
    var toastPolicy: NotificationToastPolicy? = null
    var hasUnreadError: Boolean? = null
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
