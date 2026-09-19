package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;



@Event
data class RuntimeDatasetBindingConfiguredEvent(
    val runtimeDatasetBindingId: UUID,
    @EventTag(key = "datasetId")
    val datasetId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val organizationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String,
    @EventTag(key = "runtimeId")
    val runtimeId: UUID,
    val runtimeName: String?,
    val filePath: String,
    val dataFormat: String
)
