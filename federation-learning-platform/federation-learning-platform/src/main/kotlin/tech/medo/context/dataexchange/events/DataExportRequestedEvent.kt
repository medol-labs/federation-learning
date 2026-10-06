package tech.medo.dataexchange.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.time.Instant
import java.util.UUID;



@Event
data class DataExportRequestedEvent(
    @EventTag(key = "dataExportJobId")
    val dataExportJobId: UUID,
    val resourceName: String,
    val criteriaJson: String,
    val sortJson: String,
    val columnsJson: String,
    val requestedLocale: String?,
    val requestedAt: Instant,
    val snapshotUpperBound: Instant,
    val requestHash: String,
    val fileName: String,
    val status: String
)
