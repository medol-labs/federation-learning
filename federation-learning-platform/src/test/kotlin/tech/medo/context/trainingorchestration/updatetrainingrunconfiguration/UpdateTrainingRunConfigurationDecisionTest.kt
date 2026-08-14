package tech.medo.trainingorchestration.updatetrainingrunconfiguration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.updatetrainingrunconfiguration.UpdateTrainingRunConfigurationCommand
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import tech.medo.trainingorchestration.events.TrainingRunConfigurationUpdatedEvent
import tech.medo.trainingorchestration.trainingrunconfiguration.TrainingRunConfigurationState
import java.util.UUID
import java.math.BigDecimal

class UpdateTrainingRunConfigurationDecisionTest {
    @Test
    fun UpdateDraftRunnableConfiguration() {
        val state = TrainingRunConfigurationState()
        state.evolve(
            TrainingRunConfigurationDefinedEvent(
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("cfg-1".toByteArray()),
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
            differentialPrivacyEnabled = false,
            dpNoiseMultiplier = null,
            dpClipNorm = null,
            minimumAccuracy = java.math.BigDecimal.ZERO,
            minimumFairnessScore = null,
            failureToleranceRatio = java.math.BigDecimal.ZERO
            )
        )

        val command = UpdateTrainingRunConfigurationCommand(
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("cfg-1".toByteArray()),
            federationId = UUID.nameUUIDFromBytes("fed-1".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            initialModelId = UUID.nameUUIDFromBytes("model-2".toByteArray()),
            strategyName = "FED_AVG",
            aggregationAlgorithm = "FEDERATED_AVERAGING",
            maxRounds = 20,
            minimumNodesPerRound = 3,
            roundTimeoutSeconds = 1800,
            nodeResponseTimeoutSeconds = 300,
            localEpochs = 2,
            batchSize = 64,
            learningRate = BigDecimal("0.01"),
            optimizer = "SGD",
            lossFunction = "CROSS_ENTROPY",
            gradientClippingNorm = null,
            secureAggregationRequired = true,
            differentialPrivacyEnabled = false,
            dpNoiseMultiplier = null,
            dpClipNorm = null,
            minimumAccuracy = BigDecimal("0.9"),
            minimumFairnessScore = null,
            failureToleranceRatio = BigDecimal("0.2"),
            updateReason = "Tune round budget before submission."
        )

        val events = (object : UpdateTrainingRunConfigurationDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<TrainingRunConfigurationUpdatedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("cfg-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(UUID.nameUUIDFromBytes("fed-1".toByteArray()), event.federationId)
        assertEquals(UUID.nameUUIDFromBytes("schema-1".toByteArray()), event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("model-2".toByteArray()), event.initialModelId)
        assertEquals("FED_AVG", event.strategyName)
        assertEquals("FEDERATED_AVERAGING", event.aggregationAlgorithm)
        assertEquals(20, event.maxRounds)
        assertEquals(3, event.minimumNodesPerRound)
        assertEquals(1800, event.roundTimeoutSeconds)
        assertEquals(300, event.nodeResponseTimeoutSeconds)
        assertEquals(2, event.localEpochs)
        assertEquals(64, event.batchSize)
        assertEquals(BigDecimal("0.01"), event.learningRate)
        assertEquals("SGD", event.optimizer)
        assertEquals("CROSS_ENTROPY", event.lossFunction)
        assertEquals(command.gradientClippingNorm, event.gradientClippingNorm)
        assertEquals(true, event.secureAggregationRequired)
        assertEquals(false, event.differentialPrivacyEnabled)
        assertEquals(command.dpNoiseMultiplier, event.dpNoiseMultiplier)
        assertEquals(command.dpClipNorm, event.dpClipNorm)
        assertEquals(BigDecimal("0.9"), event.minimumAccuracy)
        assertEquals(command.minimumFairnessScore, event.minimumFairnessScore)
        assertEquals(BigDecimal("0.2"), event.failureToleranceRatio)
        assertEquals("Tune round budget before submission.", event.updateReason)
    }
}
