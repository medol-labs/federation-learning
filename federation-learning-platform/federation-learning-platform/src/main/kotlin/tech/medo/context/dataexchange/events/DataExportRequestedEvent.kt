package tech.medo.dataexchange.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat



@Event
data class DataExportRequestedEvent(
    @EventTag(key = "dataExportJobId")
    val dataExportJobId: UUID,
    val resourceName: String,
    val criteriaJson: String,
    val sortJson: String,
    val columnsJson: String,
    val requestedLocale: String?,
    val requestedAt: LocalDateTime,
    val snapshotUpperBound: LocalDateTime,
    val requestHash: String,
    val fileName: String,
    val status: String
)
