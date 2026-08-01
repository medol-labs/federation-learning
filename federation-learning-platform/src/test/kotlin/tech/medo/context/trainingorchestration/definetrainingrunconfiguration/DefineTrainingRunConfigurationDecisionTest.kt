package tech.medo.trainingorchestration.definetrainingrunconfiguration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent



import java.util.UUID;
import java.math.BigDecimal;


class DefineTrainingRunConfigurationDecisionTest {
    @Test
    fun DefineRunnableConfiguration() {


        val command = DefineTrainingRunConfigurationCommand(
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("cfg-1".toByteArray()),
            federationId = UUID.nameUUIDFromBytes("fed-1".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            initialModelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            strategyName = "FED_AVG",
            aggregationAlgorithm = "FEDERATED_AVERAGING",
            maxRounds = 10,
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
            failureToleranceRatio = BigDecimal("0.2")
        )

        val events = (object : DefineTrainingRunConfigurationDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<TrainingRunConfigurationDefinedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("cfg-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals(UUID.nameUUIDFromBytes("fed-1".toByteArray()), event.federationId)
        assertEquals(UUID.nameUUIDFromBytes("schema-1".toByteArray()), event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.initialModelVersionId)
        assertEquals("FED_AVG", event.strategyName)
        assertEquals("FEDERATED_AVERAGING", event.aggregationAlgorithm)
        assertEquals(10, event.maxRounds)
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
    }
}
