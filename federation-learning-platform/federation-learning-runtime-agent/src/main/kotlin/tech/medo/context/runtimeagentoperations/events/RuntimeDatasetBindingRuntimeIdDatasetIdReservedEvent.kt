package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


@Event
data class RuntimeDatasetBindingRuntimeIdDatasetIdReservedEvent(
    val runtimeDatasetBindingId: UUID,
    val runtimeId: UUID,
    val datasetId: UUID,
    @EventTag(key = "runtimeId")
    val normalizedRuntimeId: String,
    @EventTag(key = "datasetId")
    val normalizedDatasetId: String
)
