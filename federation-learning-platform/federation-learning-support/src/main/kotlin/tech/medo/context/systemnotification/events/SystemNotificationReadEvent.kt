package tech.medo.systemnotification.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class SystemNotificationReadEvent(
    @EventTag(key = "notificationId")
    val notificationId: UUID,
    val recipientUserId: String,
    val readAt: LocalDateTime
)
