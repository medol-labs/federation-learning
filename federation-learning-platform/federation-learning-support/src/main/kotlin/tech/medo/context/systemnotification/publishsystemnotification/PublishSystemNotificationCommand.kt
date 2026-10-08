package tech.medo.systemnotification.publishsystemnotification

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.systemnotification.systemnotification.SystemNotificationSelection
import java.util.UUID;
import tech.medo.systemnotification.domain.types.NotificationCategory;
import tech.medo.systemnotification.domain.types.NotificationSeverity;
import tech.medo.systemnotification.domain.types.NotificationToastPolicy;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class PublishSystemNotificationCommand(
    val notificationId: UUID = java.util.UUID.randomUUID(),
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
) {
    @TargetEntityId
    val selection: SystemNotificationSelection = SystemNotificationSelection(notificationId = notificationId)


}
