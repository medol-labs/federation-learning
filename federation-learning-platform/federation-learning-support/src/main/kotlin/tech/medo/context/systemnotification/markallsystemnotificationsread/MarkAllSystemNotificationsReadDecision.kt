package tech.medo.systemnotification.markallsystemnotificationsread

import tech.medo.systemnotification.markallsystemnotificationsread.MarkAllSystemNotificationsReadCommand


import tech.medo.systemnotification.events.AllSystemNotificationsReadEvent
import tech.medo.systemnotification.systemnotification.SystemNotificationState





interface MarkAllSystemNotificationsReadDecision {
    fun decide(command: MarkAllSystemNotificationsReadCommand, state: SystemNotificationState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            AllSystemNotificationsReadEvent(recipientUserId = command.recipientUserId, readAt = command.readAt, notificationId = command.notificationId)
        )
    }
}
