package tech.medo.dataexchange.dataexportjob

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component
import tech.medo.dataexchange.events.DataExportCompletedEvent
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import tech.medo.systemnotification.publishsystemnotification.PublishSystemNotificationCommand
import java.time.LocalDateTime
import java.util.UUID

@Namespace("system-notification-data-exchange-data-export-job")
@Component
class DataExportCompletedEventSystemNotification1Processor(
    private val commandGateway: CommandGateway,
    private val objectMapper: ObjectMapper
) {
    @EventHandler
    fun on(event: DataExportCompletedEvent) {
        commandGateway.send(
            PublishSystemNotificationCommand(
                notificationId = UUID.randomUUID(),
                recipientUserId = "GLOBAL",
                recipientRole = null,
                category = NotificationCategory.DATA_EXCHANGE,
                severity = NotificationSeverity.SUCCESS,
                messageKey = "notifications.dataExportCompleted",
                messageArgsJson = objectMapper.writeValueAsString(mapOf("dataExportJobId" to event.dataExportJobId, "fileName" to event.fileName, "filePath" to event.filePath, "rowCount" to event.rowCount, "status" to event.status)),
                fallbackTitle = "Data export completed",
                fallbackBody = null,
                targetType = "DataExportJob",
                targetId = event.dataExportJobId.toString(),
                targetRoute = null,
                toastPolicy = NotificationToastPolicy.DEFAULT,
                publishedAt = LocalDateTime.now(),
                status = "UNREAD"
            )
        ).resultMessage
    }
}
