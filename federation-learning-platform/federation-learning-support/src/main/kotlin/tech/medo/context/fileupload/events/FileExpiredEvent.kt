package tech.medo.fileupload.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class FileExpiredEvent(
    @EventTag(key = "fileId")
    val fileId: UUID,
    val expiredAt: LocalDateTime,
    val expirationReason: String
)
