package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


/* TODO: provide values for selection tags: featureSchemaId, datasetName */

@Event
data class DatasetApprovedForTrainingEvent(
    val datasetId: UUID,
    @EventTag(key = "organizationId")
    val organizationId: UUID
)
