package tech.medo.dataexchange.dataexportjob

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component
import tech.medo.dataexchange.events.DataExportFailedEvent
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import tech.medo.systemnotification.publishsystemnotification.PublishSystemNotificationCommand
import java.time.LocalDateTime
import java.util.UUID

@Namespace("system-notification-data-exchange-data-export-job")
@Component
class DataExportFailedEventSystemNotification2Processor(
    private val commandGateway: CommandGateway,
    private val objectMapper: ObjectMapper
) {
    @EventHandler
    fun on(event: DataExportFailedEvent) {
        commandGateway.send(
            PublishSystemNotificationCommand(
                notificationId = UUID.randomUUID(),
                recipientUserId = "GLOBAL",
                recipientRole = null,
                category = NotificationCategory.DATA_EXCHANGE,
                severity = NotificationSeverity.ERROR,
                messageKey = "notifications.dataExportFailed",
                messageArgsJson = objectMapper.writeValueAsString(mapOf("dataExportJobId" to event.dataExportJobId, "errorMessage" to event.errorMessage, "status" to event.status)),
                fallbackTitle = "Data export failed",
                fallbackBody = event.errorMessage,
                targetType = "DataExportJob",
                targetId = event.dataExportJobId.toString(),
                targetRoute = null,
                toastPolicy = NotificationToastPolicy.IMPORTANT,
                publishedAt = LocalDateTime.now(),
                status = "UNREAD"
            )
        ).resultMessage
    }
}
