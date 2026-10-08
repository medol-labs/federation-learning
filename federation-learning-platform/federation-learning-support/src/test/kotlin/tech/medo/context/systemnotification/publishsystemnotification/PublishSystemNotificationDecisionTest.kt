package tech.medo.systemnotification.publishsystemnotification

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.systemnotification.publishsystemnotification.PublishSystemNotificationCommand
import tech.medo.systemnotification.events.SystemNotificationPublishedEvent
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

class PublishSystemNotificationDecisionTest {
    @Test
    fun PublishSystemNotificationEmitsSystemNotificationPublishedEvent() {
        val events = (object : PublishSystemNotificationDecision {}).decide(
            PublishSystemNotificationCommand(
            notificationId = java.util.UUID.randomUUID(),
            recipientUserId = "",
            recipientRole = null,
            category = NotificationCategory.SYSTEM,
            severity = NotificationSeverity.INFO,
            messageKey = "",
            messageArgsJson = null,
            fallbackTitle = "",
            fallbackBody = null,
            targetType = null,
            targetId = null,
            targetRoute = null,
            toastPolicy = NotificationToastPolicy.NONE,
            publishedAt = java.time.LocalDateTime.now(),
            status = ""
            )
        )

        assertTrue(events.any { it is SystemNotificationPublishedEvent })
    }
}
