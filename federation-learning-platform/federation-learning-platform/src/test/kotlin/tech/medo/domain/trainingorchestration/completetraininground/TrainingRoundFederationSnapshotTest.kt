package tech.medo.domain.trainingorchestration.completetraininground

import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundCommand
import tech.medo.trainingorchestration.completetraininground.CompleteTrainingRoundDecision
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState

class TrainingRoundFederationSnapshotTest {
    @Test
    fun `completed round retains the original federation for subsequent participant selection`() {
        val federationId = UUID.randomUUID()
        val state = TrainingRoundState().apply { this.federationId = federationId }
        val command = CompleteTrainingRoundCommand(
            trainingJobId = UUID.randomUUID(),
            trainingRunConfigurationId = UUID.randomUUID(),
            trainingJobObjective = "Two-round acceptance",
            featureSchemaId = UUID.randomUUID(),
            roundId = UUID.randomUUID(),
            roundNumber = 1,
            maxRounds = 2,
            minimumAccuracy = BigDecimal.ONE,
            aggregatedModelId = UUID.randomUUID(),
            modelPlugin = "SKLEARN_LOGISTIC_REGRESSION",
            aggregatedModelArtifactUri = "https://example.test/models/round-1.json",
            aggregatedModelRegistryRef = "test",
            modelFormat = "JSON",
            modelArtifactDigest = "sha256:" + "0".repeat(64),
            aggregatedModelSignatureUri = null,
            globalAccuracy = BigDecimal("0.75"),
        )
        val event = (object : CompleteTrainingRoundDecision {}).decide(command, state).single()
            as TrainingRoundCompletedEvent
        assertEquals(federationId, event.federationId)
        assertEquals(command.aggregatedModelId, event.aggregatedModelId)
        assertEquals(1, event.roundNumber)
        assertEquals(2, event.maxRounds)
    }
}
