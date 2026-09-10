package tech.medo.datasetgovernance.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class DatasetMetadataReprofiledEvent(
    @EventTag(key = "runtimeDatasetBindingId")
    val runtimeDatasetBindingId: UUID,
    val metadataReportId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val organizationName: String?,
    val runtimeId: UUID,
    val runtimeName: String?,
    val featureSchemaId: UUID,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val datasetName: String,
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
