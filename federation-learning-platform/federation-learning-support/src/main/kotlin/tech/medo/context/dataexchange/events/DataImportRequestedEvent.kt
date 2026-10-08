package tech.medo.dataexchange.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;



@Event
data class DataImportRequestedEvent(
    @EventTag(key = "dataImportJobId")
    val dataImportJobId: UUID,
    val commandName: String,
    val resourceName: String,
    val originalFileName: String,
    val filePath: String,
    val requestedLocale: String?,
    val requestedAt: LocalDateTime,
    val requestHash: String,
    val status: String
)
