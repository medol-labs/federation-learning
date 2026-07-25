package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;


/* TODO: provide values for selection tags: organizationId, featureSchemaId, datasetName */

@Event
data class DatasetTrainingApprovalRevokedEvent(
    val datasetId: UUID,
    val revokeReason: String
)
