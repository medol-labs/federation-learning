package tech.medo.trainingorchestration.completetraininground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand
import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent

import tech.medo.trainingorchestration.traininground.TrainingRoundState

import java.util.UUID;
import java.math.BigDecimal;


class CompleteTrainingRoundDecisionTest {
    @Test
    fun CompleteRoundAfterGlobalEvaluation() {
        val state = TrainingRoundState()
        state.evolve(
            GlobalModelEvaluationSubmittedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            aggregatedModelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            modelFormat = "",
            modelHash = "",
            globalAccuracy = BigDecimal("0.91"),
            globalFairnessScore = java.math.BigDecimal.ZERO
            )
        )

        val command = CompleteTrainingRoundCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            aggregatedModelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            modelFormat = "",
            modelHash = "",
            globalAccuracy = BigDecimal("0.91")
        )

        val events = CompleteTrainingRoundDecision().decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<TrainingRoundCompletedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.aggregatedModelVersionId)
        assertEquals(command.modelFormat, event.modelFormat)
        assertEquals(command.modelHash, event.modelHash)
        assertEquals(BigDecimal("0.91"), event.globalAccuracy)
    }
}
