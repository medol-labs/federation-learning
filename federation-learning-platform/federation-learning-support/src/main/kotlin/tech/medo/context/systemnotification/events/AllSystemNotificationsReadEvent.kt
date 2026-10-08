package tech.medo.systemnotification.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.UUID;



@Event
data class AllSystemNotificationsReadEvent(
    val recipientUserId: String,
    val readAt: LocalDateTime,
    @EventTag(key = "notificationId")
    val notificationId: UUID
)
