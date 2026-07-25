package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class DatasetDeclaredEvent(
    val datasetId: UUID,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    @EventTag(key = "featureSchemaId")
    val featureSchemaId: UUID,
    @EventTag(key = "datasetName")
    val datasetName: String,
    val datasetType: String,
    val datasetUsage: String
)
