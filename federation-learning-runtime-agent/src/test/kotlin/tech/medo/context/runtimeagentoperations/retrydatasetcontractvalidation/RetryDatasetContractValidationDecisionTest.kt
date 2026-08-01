package tech.medo.runtimeagentoperations.retrydatasetcontractvalidation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationCommand
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidationFailedEvent

import tech.medo.runtimeagentoperations.dataset.DatasetState
import tech.medo.runtimeagentoperations.retrydatasetcontractvalidation.RetryDatasetContractValidationResult
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime

class RetryDatasetContractValidationDecisionTest {
    @Test
    fun DatasetContractRevalidated() {
        val state = DatasetState()
        state.evolve(
            DatasetContractValidationFailedEvent(
            datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("feature-schema-1".toByteArray()),
            metadataReportId = UUID.nameUUIDFromBytes("metadata-report-1".toByteArray()),
            schemaCompatible = false,
            labelCompatible = false,
            qualityScore = BigDecimal("0.4"),
            nonIidScore = BigDecimal("0.2"),
            failureReason = "CSV schema mismatch",
            organizationId = java.util.UUID.randomUUID(),
            datasetName = ""
            )
        )

        val command = RetryDatasetContractValidationCommand(
            datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            datasetName = ""
        )

        val events = (object : RetryDatasetContractValidationDecision {}).decide(
            command,
            state = state,
            portResult = RetryDatasetContractValidationResult.Succeeded(

            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<DatasetContractRevalidatedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("dataset-1".toByteArray()), event.datasetId)
    }

    @Test
    fun DatasetContractRevalidationFailed() {
        val state = DatasetState()
        state.evolve(
            DatasetContractValidationFailedEvent(
            datasetId = UUID.nameUUIDFromBytes("dataset-2".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("feature-schema-1".toByteArray()),
            metadataReportId = UUID.nameUUIDFromBytes("metadata-report-2".toByteArray()),
            schemaCompatible = false,
            labelCompatible = false,
            qualityScore = BigDecimal("0.1"),
            nonIidScore = BigDecimal("0.3"),
            failureReason = "CSV label missing",
            organizationId = java.util.UUID.randomUUID(),
            datasetName = ""
            )
        )

        val command = RetryDatasetContractValidationCommand(
            datasetId = UUID.nameUUIDFromBytes("dataset-2".toByteArray()),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            datasetName = ""
        )

        val events = (object : RetryDatasetContractValidationDecision {}).decide(
            command,
            state = state,
            portResult = RetryDatasetContractValidationResult.Rejected(
                failureReason = ""
            ),
            now = LocalDateTime.parse("2026-01-01T00:00:00")
        )

        val event = events.filterIsInstance<DatasetContractRevalidationFailedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("dataset-2".toByteArray()), event.datasetId)
    }
}
