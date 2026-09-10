package tech.medo.trainingorchestration.completetraininground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand
import tech.medo.trainingorchestration.events.GlobalModelEvaluationSubmittedEvent
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import java.util.UUID
import java.math.BigDecimal

class CompleteTrainingRoundDecisionTest {
    @Test
    fun CompleteRoundAfterGlobalEvaluation() {
        val state = TrainingRoundState()
        state.evolve(
            GlobalModelEvaluationSubmittedEvent(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            trainingJobObjective = "",
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            aggregatedModelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            aggregatedModelArtifactUri = "",
            aggregatedModelRegistryRef = "",
            modelFormat = "",
            modelArtifactDigest = "",
            aggregatedModelSignatureUri = null,
            globalAccuracy = BigDecimal("0.91"),
            globalFairnessScore = java.math.BigDecimal.ZERO
            )
        )

        val command = CompleteTrainingRoundCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            trainingJobObjective = "",
            featureSchemaId = java.util.UUID.randomUUID(),
            roundId = UUID.nameUUIDFromBytes("round-1".toByteArray()),
            roundNumber = 0,
            maxRounds = 0,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            aggregatedModelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            aggregatedModelArtifactUri = "",
            aggregatedModelRegistryRef = "",
            modelFormat = "",
            modelArtifactDigest = "",
            aggregatedModelSignatureUri = null,
            globalAccuracy = BigDecimal("0.91")
        )

        val events = (object : CompleteTrainingRoundDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<TrainingRoundCompletedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.trainingJobObjective, event.trainingJobObjective)
        assertEquals(command.featureSchemaId, event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("round-1".toByteArray()), event.roundId)
        assertEquals(command.roundNumber, event.roundNumber)
        assertEquals(command.maxRounds, event.maxRounds)
        assertEquals(command.minimumAccuracy, event.minimumAccuracy)
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.aggregatedModelId)
        assertEquals(command.aggregatedModelArtifactUri, event.aggregatedModelArtifactUri)
        assertEquals(command.aggregatedModelRegistryRef, event.aggregatedModelRegistryRef)
        assertEquals(command.modelFormat, event.modelFormat)
        assertEquals(command.modelArtifactDigest, event.modelArtifactDigest)
        assertEquals(command.aggregatedModelSignatureUri, event.aggregatedModelSignatureUri)
        assertEquals(BigDecimal("0.91"), event.globalAccuracy)
    }
}
