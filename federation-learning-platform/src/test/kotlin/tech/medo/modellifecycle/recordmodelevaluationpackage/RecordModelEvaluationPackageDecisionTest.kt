package tech.medo.modellifecycle.recordmodelevaluationpackage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modellifecycle.recordmodelevaluationpackage.RecordModelEvaluationPackageCommand
import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent
import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent

import tech.medo.modellifecycle.modelversion.ModelVersionState

import java.util.UUID;
import java.math.BigDecimal;


class RecordModelEvaluationPackageDecisionTest {
    @Test
    fun RecordCompleteEvaluationPackage() {
        val state = ModelVersionState()
        state.evolve(
            ModelCandidateRegisteredEvent(
            modelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            trainingJobId = java.util.UUID.randomUUID(),
            finalRoundId = java.util.UUID.randomUUID(),
            modelArtifactId = java.util.UUID.randomUUID(),
            modelHash = "",
            evaluationReportId = UUID.nameUUIDFromBytes("report-1".toByteArray()),
            finalGlobalAccuracy = java.math.BigDecimal.ZERO
            )
        )

        val command = RecordModelEvaluationPackageCommand(
            modelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            trainingJobId = java.util.UUID.randomUUID(),
            evaluationReportId = java.util.UUID.randomUUID(),
            experimentId = java.util.UUID.randomUUID(),
            hyperparameterSnapshotId = java.util.UUID.randomUUID(),
            reproducibilityManifestId = UUID.nameUUIDFromBytes("manifest-1".toByteArray()),
            modelCardId = UUID.nameUUIDFromBytes("card-1".toByteArray()),
            baselineModelVersionId = null
        )

        val events = RecordModelEvaluationPackageDecision().decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<ModelEvaluationPackageRecordedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.modelVersionId)
        assertEquals(command.trainingJobId, event.trainingJobId)
        assertEquals(command.evaluationReportId, event.evaluationReportId)
        assertEquals(command.experimentId, event.experimentId)
        assertEquals(command.hyperparameterSnapshotId, event.hyperparameterSnapshotId)
        assertEquals(UUID.nameUUIDFromBytes("manifest-1".toByteArray()), event.reproducibilityManifestId)
        assertEquals(UUID.nameUUIDFromBytes("card-1".toByteArray()), event.modelCardId)
        assertEquals(command.baselineModelVersionId, event.baselineModelVersionId)
    }
}
