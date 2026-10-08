package tech.medo.systemnotification.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import tech.medo.systemnotification.domain.types.NotificationCategory;
import tech.medo.systemnotification.domain.types.NotificationSeverity;
import tech.medo.systemnotification.domain.types.NotificationToastPolicy;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class SystemNotificationPublishedEvent(
    @EventTag(key = "notificationId")
    val notificationId: UUID,
    val recipientUserId: String,
    val recipientRole: String?,
    val category: NotificationCategory,
    val severity: NotificationSeverity,
    val messageKey: String,
    val messageArgsJson: String?,
    val fallbackTitle: String,
    val fallbackBody: String?,
    val targetType: String?,
    val targetId: String?,
    val targetRoute: String?,
    val toastPolicy: NotificationToastPolicy,
    val publishedAt: LocalDateTime,
    val status: String
)
