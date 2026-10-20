package tech.medo.systemnotification.marksystemnotificationread

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.systemnotification.systemnotification.SystemNotificationSelection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class MarkSystemNotificationReadCommand(
    val notificationId: UUID,
    val recipientUserId: String,
    val readAt: LocalDateTime
) {

    @TargetEntityId
    val selection: SystemNotificationSelection = SystemNotificationSelection(notificationId = notificationId)



}
