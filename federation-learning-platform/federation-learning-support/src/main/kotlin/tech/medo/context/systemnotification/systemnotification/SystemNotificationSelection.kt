package tech.medo.systemnotification.systemnotification

import java.util.UUID;


data class SystemNotificationSelection(
    val notificationId: UUID
)

object SystemNotificationTags {
    const val NOTIFICATION_ID = "notificationId"
}

object SystemNotificationMetadata {
    val concepts = listOf("SystemNotification")
}
