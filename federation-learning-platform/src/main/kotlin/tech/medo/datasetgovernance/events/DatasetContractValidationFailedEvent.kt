package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;


/* TODO: provide values for selection tags: organizationId, datasetName */

@Event
data class DatasetContractValidationFailedEvent(
    val datasetId: UUID,
    @EventTag(key = "featureSchemaId")
    val featureSchemaId: UUID,
    val metadataReportId: UUID,
    val schemaCompatible: Boolean,
    val labelCompatible: Boolean,
    val qualityScore: BigDecimal,
    val nonIidScore: BigDecimal,
    val failureReason: String
)
