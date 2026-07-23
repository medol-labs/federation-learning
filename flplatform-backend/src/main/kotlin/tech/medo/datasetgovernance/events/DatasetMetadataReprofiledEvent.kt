package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;


/* TODO: provide values for selection tags: datasetName */

@Event
data class DatasetMetadataReprofiledEvent(
    val metadataReportId: UUID,
    val datasetId: UUID,
    @EventTag(key = "organizationId")
    val organizationId: UUID,
    val runtimeId: UUID,
    @EventTag(key = "featureSchemaId")
    val featureSchemaId: UUID,
    val sampleCount: Int,
    val featureCount: Int,
    val schemaCompatible: Boolean?,
    val labelCompatible: Boolean?,
    val missingValueRate: BigDecimal?,
    val duplicateRate: BigDecimal?,
    val qualityScore: BigDecimal?,
    val nonIidScore: BigDecimal?,
    val classBalanceScore: BigDecimal?
)
