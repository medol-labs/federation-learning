package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class AgentDatasetProfilingFailedEvent(
    @EventTag(key = "metadataReportId")
    val metadataReportId: UUID,
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val runtimeId: UUID,
    val failureReason: String
)
