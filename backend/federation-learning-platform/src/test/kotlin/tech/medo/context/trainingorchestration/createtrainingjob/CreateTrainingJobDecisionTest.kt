package tech.medo.trainingorchestration.createtrainingjob

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.createtrainingjob.CreateTrainingJobCommand
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.events.TrainingJobCreatedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState
import java.util.UUID
import java.math.BigDecimal

class CreateTrainingJobDecisionTest {
    @Test
    fun CreateTrainingJobWithRunnableConfiguration() {
        val state = TrainingRunConfigurationState()
        state.evolve(
            TrainingRunConfigurationDefinedEvent(
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            configurationName = "",
            federationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            initialModelId = java.util.UUID.randomUUID(),
            initialModelName = "",
            initialModelVersion = "",
            initialModelArtifactUri = "",
            initialModelRegistryRef = "",
            initialModelFormat = "",
            initialModelArtifactDigest = "",
            initialModelSignatureUri = null,
            strategyName = "",
            aggregationAlgorithm = "",
            maxRounds = 0,
            minimumNodesPerRound = 0,
            roundTimeoutSeconds = 0,
            nodeResponseTimeoutSeconds = 0,
            localEpochs = 0,
            batchSize = 0,
            learningRate = java.math.BigDecimal.ZERO,
            optimizer = "",
            lossFunction = "",
            gradientClippingNorm = null,
            secureAggregationRequired = false,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            minimumFairnessScore = null
            )
        )

        val command = CreateTrainingJobCommand(
            trainingJobId = UUID.nameUUIDFromBytes("job-1".toByteArray()),
            federationId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("config-1".toByteArray()),
            objective = ""
        )

        val events = (object : CreateTrainingJobDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<TrainingJobCreatedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("job-1".toByteArray()), event.trainingJobId)
        assertEquals(command.federationId, event.federationId)
        assertEquals(UUID.nameUUIDFromBytes("config-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(command.objective, event.objective)
    }
}
