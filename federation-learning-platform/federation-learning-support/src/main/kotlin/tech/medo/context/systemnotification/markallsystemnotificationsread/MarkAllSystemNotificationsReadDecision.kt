package tech.medo.systemnotification.markallsystemnotificationsread

import tech.medo.systemnotification.markallsystemnotificationsread.MarkAllSystemNotificationsReadCommand


import tech.medo.systemnotification.events.AllSystemNotificationsReadEvent
import tech.medo.systemnotification.recipientnotificationacknowledgement.RecipientNotificationAcknowledgementState





interface MarkAllSystemNotificationsReadDecision {
    fun decide(command: MarkAllSystemNotificationsReadCommand, state: RecipientNotificationAcknowledgementState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            AllSystemNotificationsReadEvent(recipientUserId = command.recipientUserId, readAt = command.readAt)
        )
    }
}
