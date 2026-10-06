package tech.medo.dataexchange.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID

@Event
data class DataExportFailedEvent(
    @EventTag(key = "dataExportJobId")
    val dataExportJobId: UUID,
    val errorMessage: String,
    val status: String
)
