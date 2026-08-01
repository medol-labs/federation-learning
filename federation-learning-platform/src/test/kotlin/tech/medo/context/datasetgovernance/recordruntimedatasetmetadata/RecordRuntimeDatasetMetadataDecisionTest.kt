package tech.medo.datasetgovernance.recordruntimedatasetmetadata

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.datasetgovernance.recordruntimedatasetmetadata.RecordRuntimeDatasetMetadataCommand
import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent



import java.util.UUID;
import java.math.BigDecimal;


class RecordRuntimeDatasetMetadataDecisionTest {
    @Test
    fun RecordRuntimeDatasetMetadataEmitsDatasetMetadataReportedEvent() {
        val events = (object : RecordRuntimeDatasetMetadataDecision {}).decide(
            RecordRuntimeDatasetMetadataCommand(
            metadataReportId = java.util.UUID.randomUUID(),
            datasetId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            runtimeId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            sampleCount = 0,
            featureCount = 0,
            schemaCompatible = null,
            labelCompatible = null,
            missingValueRate = null,
            duplicateRate = null,
            qualityScore = null,
            nonIidScore = null,
            classBalanceScore = null
            )
        )

        assertTrue(events.any { it is DatasetMetadataReportedEvent })
    }
}
