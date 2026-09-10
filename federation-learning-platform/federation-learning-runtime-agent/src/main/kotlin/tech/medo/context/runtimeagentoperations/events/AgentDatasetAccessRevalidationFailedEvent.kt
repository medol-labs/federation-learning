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
    val organizationId: UUID,
    val organizationName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String,
    val runtimeId: UUID,
    val runtimeName: String?,
    val failureReason: String
)
