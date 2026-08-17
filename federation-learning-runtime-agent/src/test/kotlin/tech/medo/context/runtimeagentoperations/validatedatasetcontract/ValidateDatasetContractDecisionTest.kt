package tech.medo.runtimeagentoperations.validatedatasetcontract

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractCommand
import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.dataset.DatasetState
import tech.medo.runtimeagentoperations.validatedatasetcontract.ValidateDatasetContractResult
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

class ValidateDatasetContractDecisionTest {
    @Test
    fun DatasetContractValidationSucceeded() {
        val state = DatasetState()


        val command = ValidateDatasetContractCommand(
            datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
            metadataReportId = UUID.nameUUIDFromBytes("metadata-report-1".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("feature-schema-1".toByteArray()),
            organizationId = java.util.UUID.randomUUID(),
            datasetName = ""
        )

        val events = (object : ValidateDatasetContractDecision {}).decide(
            command,
            state = state,
            portResult = ValidateDatasetContractResult.Succeeded(
                schemaCompatible = false,
                labelCompatible = false,
                qualityScore = java.math.BigDecimal.ZERO,
                nonIidScore = java.math.BigDecimal.ZERO
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<DatasetContractValidatedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("dataset-1".toByteArray()), event.datasetId)
        assertEquals(UUID.nameUUIDFromBytes("feature-schema-1".toByteArray()), event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("metadata-report-1".toByteArray()), event.metadataReportId)
    }

    @Test
    fun DatasetContractValidationFailed() {
        val state = DatasetState()


        val command = ValidateDatasetContractCommand(
            datasetId = UUID.nameUUIDFromBytes("dataset-2".toByteArray()),
            metadataReportId = UUID.nameUUIDFromBytes("metadata-report-2".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("feature-schema-1".toByteArray()),
            organizationId = java.util.UUID.randomUUID(),
            datasetName = ""
        )

        val events = (object : ValidateDatasetContractDecision {}).decide(
            command,
            state = state,
            portResult = ValidateDatasetContractResult.Rejected(
                schemaCompatible = false,
                labelCompatible = false,
                qualityScore = java.math.BigDecimal.ZERO,
                nonIidScore = java.math.BigDecimal.ZERO,
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<DatasetContractValidationFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("dataset-2".toByteArray()), event.datasetId)
        assertEquals(UUID.nameUUIDFromBytes("feature-schema-1".toByteArray()), event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("metadata-report-2".toByteArray()), event.metadataReportId)
    }
}
