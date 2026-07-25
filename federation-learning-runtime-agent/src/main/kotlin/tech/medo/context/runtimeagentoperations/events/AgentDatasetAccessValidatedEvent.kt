package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class AgentDatasetAccessValidatedEvent(
    @EventTag(key = "datasetAccessValidationId")
    val datasetAccessValidationId: UUID,
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val runtimeId: UUID,
    val readable: Boolean,
    val schemaReadable: Boolean,
    val sampleBatchReadable: Boolean
)
