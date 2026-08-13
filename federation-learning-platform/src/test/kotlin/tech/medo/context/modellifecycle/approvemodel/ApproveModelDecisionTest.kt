package tech.medo.modellifecycle.approvemodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modellifecycle.approvemodel.ApproveModelCommand
import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent
import tech.medo.modellifecycle.events.ModelApprovedEvent

import tech.medo.modellifecycle.model.ModelState

import java.util.UUID;


class ApproveModelDecisionTest {
    @Test
    fun ApproveEvaluationPackagedModel() {
        val state = ModelState()
        state.evolve(
            ModelEvaluationPackageRecordedEvent(
            modelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            trainingJobId = java.util.UUID.randomUUID(),
            evaluationReportId = UUID.nameUUIDFromBytes("report-1".toByteArray()),
            experimentId = java.util.UUID.randomUUID(),
            hyperparameterSnapshotId = java.util.UUID.randomUUID(),
            reproducibilityManifestId = UUID.nameUUIDFromBytes("manifest-1".toByteArray()),
            modelCardId = UUID.nameUUIDFromBytes("card-1".toByteArray()),
            baselineModelId = null
            )
        )

        val command = ApproveModelCommand(
            modelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            approvalNote = null
        )

        val events = (object : ApproveModelDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<ModelApprovedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.modelId)
        assertEquals(command.approvalNote, event.approvalNote)
    }
}
