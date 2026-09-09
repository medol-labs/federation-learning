package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class DatasetTrainingApprovalRevokedEvent(
    val datasetId: UUID,
    val revokeReason: String,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    @EventTag(key = "featureSchemaId")
    val featureSchemaId: UUID,
    @EventTag(key = "datasetName")
    val datasetName: String
)
