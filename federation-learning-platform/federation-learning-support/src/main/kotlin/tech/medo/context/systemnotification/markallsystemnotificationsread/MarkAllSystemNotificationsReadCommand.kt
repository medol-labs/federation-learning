package tech.medo.systemnotification.markallsystemnotificationsread

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.systemnotification.recipientnotificationacknowledgement.RecipientNotificationAcknowledgementSelection
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Command
data class MarkAllSystemNotificationsReadCommand(
    val recipientUserId: String,
    val readAt: LocalDateTime
) {

    @TargetEntityId
    val selection: RecipientNotificationAcknowledgementSelection = RecipientNotificationAcknowledgementSelection(recipientUserId = recipientUserId)



}
