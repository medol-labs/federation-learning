package tech.medo.systemnotification.publishsystemnotification

import tech.medo.systemnotification.publishsystemnotification.PublishSystemNotificationCommand


import tech.medo.systemnotification.events.SystemNotificationPublishedEvent
import tech.medo.systemnotification.systemnotification.SystemNotificationState





interface PublishSystemNotificationDecision {
    fun decide(command: PublishSystemNotificationCommand): List<Any> {
        if (!(command.status == "UNREAD")) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_ASSERTION_FAILED",
                i18nKey = "errors.commandAssertionFailed",
                args = mapOf("command" to "PublishSystemNotification", "expression" to "assert PublishSystemNotification.status == \"UNREAD\""),
                message = "New notifications must be unread."
            )
        }
        return listOf(
            SystemNotificationPublishedEvent(notificationId = command.notificationId, recipientUserId = command.recipientUserId, recipientRole = command.recipientRole, category = command.category, severity = command.severity, messageKey = command.messageKey, messageArgsJson = command.messageArgsJson, fallbackTitle = command.fallbackTitle, fallbackBody = command.fallbackBody, targetType = command.targetType, targetId = command.targetId, targetRoute = command.targetRoute, toastPolicy = command.toastPolicy, publishedAt = command.publishedAt, status = command.status)
        )
    }
}
