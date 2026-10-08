package tech.medo.dataexchange.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class DataImportCompletedEvent(
    @EventTag(key = "dataImportJobId")
    val dataImportJobId: UUID,
    val totalRows: Long,
    val successRows: Long,
    val failedRows: Long,
    val failureIndexPath: String?,
    val status: String
)
