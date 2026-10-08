package tech.medo.systemnotification.marksystemnotificationread

import tech.medo.systemnotification.marksystemnotificationread.MarkSystemNotificationReadCommand


import tech.medo.systemnotification.events.SystemNotificationReadEvent
import tech.medo.systemnotification.systemnotification.SystemNotificationState


import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum


interface MarkSystemNotificationReadDecision {
    fun decide(command: MarkSystemNotificationReadCommand, state: SystemNotificationState): List<Any> {
        if (state.currentState != SystemNotificationStateEnum.Unread) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.systemnotification.markSystemNotificationRead.requiresState",
                args = mapOf(
                    "command" to "MarkSystemNotificationRead",
                    "aggregate" to "SystemNotification",
                    "expectedState" to "Unread",
                    "actualState" to state.currentState.toString()
                ),
                message = "MarkSystemNotificationRead requires SystemNotification to be Unread."
            )
        }
        return listOf(
            SystemNotificationReadEvent(notificationId = command.notificationId, recipientUserId = command.recipientUserId, readAt = command.readAt)
        )
    }
}
