package tech.medo.datasetgovernance.approvedatasetfortraining

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.datasetgovernance.approvedatasetfortraining.ApproveDatasetForTrainingCommand
import tech.medo.datasetgovernance.events.DatasetContractValidatedEvent
import tech.medo.datasetgovernance.events.DatasetApprovedForTrainingEvent

import tech.medo.datasetgovernance.dataset.DatasetState

import java.util.UUID;
import java.math.BigDecimal;


class ApproveDatasetForTrainingDecisionTest {
    @Test
    fun ApproveValidatedDataset() {
        val state = DatasetState()
        state.evolve(
            DatasetContractValidatedEvent(
            datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            metadataReportId = java.util.UUID.randomUUID(),
            schemaCompatible = true,
            labelCompatible = true,
            qualityScore = BigDecimal("0.86"),
            nonIidScore = java.math.BigDecimal.ZERO
            )
        )

        val command = ApproveDatasetForTrainingCommand(
            datasetId = UUID.nameUUIDFromBytes("dataset-1".toByteArray()),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            datasetName = ""
        )

        val events = ApproveDatasetForTrainingDecision().decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<DatasetApprovedForTrainingEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("dataset-1".toByteArray()), event.datasetId)
    }
}
