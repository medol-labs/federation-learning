package tech.medo.dataexchange.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID

@Event
data class DataExportCompletedEvent(
    @EventTag(key = "dataExportJobId")
    val dataExportJobId: UUID,
    val fileName: String,
    val filePath: String,
    val rowCount: Long,
    val status: String
)
