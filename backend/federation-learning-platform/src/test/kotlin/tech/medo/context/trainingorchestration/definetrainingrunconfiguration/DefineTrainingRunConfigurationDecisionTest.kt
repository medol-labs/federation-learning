package tech.medo.trainingorchestration.definetrainingrunconfiguration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand
import tech.medo.trainingorchestration.events.TrainingRunConfigurationDefinedEvent
import java.util.UUID
import java.math.BigDecimal

class DefineTrainingRunConfigurationDecisionTest {
    @Test
    fun DefineRunnableConfiguration() {


        val command = DefineTrainingRunConfigurationCommand(
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("cfg-1".toByteArray()),
            configurationName = "Readmission Risk Baseline",
            federationId = UUID.nameUUIDFromBytes("fed-1".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            initialModelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
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
            minimumAccuracy = BigDecimal("0.9"),
            minimumFairnessScore = null
        )

        val events = (object : DefineTrainingRunConfigurationDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<TrainingRunConfigurationDefinedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("cfg-1".toByteArray()), event.trainingRunConfigurationId)
        assertEquals("Readmission Risk Baseline", event.configurationName)
        assertEquals(UUID.nameUUIDFromBytes("fed-1".toByteArray()), event.federationId)
        assertEquals(UUID.nameUUIDFromBytes("schema-1".toByteArray()), event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.initialModelId)
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
        assertEquals(BigDecimal("0.9"), event.minimumAccuracy)
        assertEquals(command.minimumFairnessScore, event.minimumFairnessScore)
    }
}
