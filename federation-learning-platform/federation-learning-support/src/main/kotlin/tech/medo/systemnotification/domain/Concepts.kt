package tech.medo.systemnotification.domain

object Concepts {
    data object SystemNotification {
        const val NAME = "SystemNotification"
        val slices = listOf("PublishSystemNotification", "MarkSystemNotificationRead", "MarkAllSystemNotificationsRead", "SystemNotificationCatalog")
        val states = listOf("Unread", "Read")
    }
}
