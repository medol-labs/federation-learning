package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class AgentDatasetAccessRevalidatedEvent(
    @EventTag(key = "datasetAccessValidationId")
    val datasetAccessValidationId: UUID,
    val runtimeDatasetBindingId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String,
    val runtimeId: UUID,
    val readable: Boolean,
    val schemaReadable: Boolean,
    val sampleBatchReadable: Boolean
)
