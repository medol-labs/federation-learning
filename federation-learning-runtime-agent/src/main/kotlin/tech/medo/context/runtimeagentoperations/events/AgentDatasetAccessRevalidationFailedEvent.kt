package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class AgentDatasetAccessRevalidationFailedEvent(
    @EventTag(key = "datasetAccessValidationId")
    val datasetAccessValidationId: UUID,
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val runtimeId: UUID,
    val failureReason: String
)
