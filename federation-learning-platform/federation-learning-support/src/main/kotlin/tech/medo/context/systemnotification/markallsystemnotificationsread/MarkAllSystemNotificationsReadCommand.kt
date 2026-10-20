package tech.medo.systemnotification.markallsystemnotificationsread

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.systemnotification.systemnotification.SystemNotificationSelection
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.UUID;


@Command
data class MarkAllSystemNotificationsReadCommand(
    val recipientUserId: String,
    val readAt: LocalDateTime,
    val notificationId: UUID
) {

    @TargetEntityId
    val selection: SystemNotificationSelection = SystemNotificationSelection(notificationId = notificationId)



}
