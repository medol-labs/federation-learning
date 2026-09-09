package tech.medo.runtimeagentoperations.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class DatasetContractRevalidationFailedEvent(
    val datasetId: UUID,
    @EventTag(key = "featureSchemaId")
    val featureSchemaId: UUID,
    val metadataReportId: UUID,
    val schemaCompatible: Boolean,
    val labelCompatible: Boolean,
    val qualityScore: BigDecimal,
    val nonIidScore: BigDecimal,
    val failureReason: String,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    @EventTag(key = "datasetName")
    val datasetName: String
)
