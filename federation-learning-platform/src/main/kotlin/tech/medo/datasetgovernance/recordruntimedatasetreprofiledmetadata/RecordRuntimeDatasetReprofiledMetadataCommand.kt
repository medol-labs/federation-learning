package tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class RecordRuntimeDatasetReprofiledMetadataCommand(
    val metadataReportId: UUID,
    val datasetId: UUID,
    val organizationId: UUID,
    val runtimeId: UUID,
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
) {
    @TargetEntityId
    val selection: RecordRuntimeDatasetReprofiledMetadataSelection = RecordRuntimeDatasetReprofiledMetadataSelection(metadataReportId = metadataReportId)

}
