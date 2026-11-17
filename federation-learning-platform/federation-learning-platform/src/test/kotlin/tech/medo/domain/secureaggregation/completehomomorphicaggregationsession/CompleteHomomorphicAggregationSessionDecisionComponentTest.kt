package tech.medo.domain.secureaggregation.completehomomorphicaggregationsession

import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult
import tech.medo.secureaggregation.events.SecureAggregationCompletedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelProjection
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository

class CompleteHomomorphicAggregationSessionDecisionComponentTest {
    private val repository = mock(TrainingRunConfigurationCatalogReadModelRepository::class.java)
    private val decision = CompleteHomomorphicAggregationSessionDecisionComponent(repository)
    private val command = CompleteHomomorphicAggregationSessionCommand(
        secureAggregationSessionId = UUID.randomUUID(), trainingJobId = UUID.randomUUID(),
        trainingRunConfigurationId = UUID.randomUUID(), trainingJobObjective = "Two-round training",
        featureSchemaId = UUID.randomUUID(), roundId = UUID.randomUUID(), roundNumber = 1,
        maxRounds = 2, minimumAccuracy = BigDecimal.ZERO, modelPlugin = "",
        encryptedUpdateArtifactRefs = emptyList(),
    )
    private val result = CompleteHomomorphicAggregationSessionResult.Succeeded(
        "global", "round-1", null, "FEDERATED_TRAINING", "https://example.test/model.json",
        "test", "JSON", "sha256:" + "0".repeat(64), null, 100,
    )

    @Test
    fun `secure aggregation retains configured model plugin for next round`() {
        configuration("SKLEARN_LOGISTIC_REGRESSION")
        val event = decision.decide(command, SecureAggregationSessionState(), result).single()
            as SecureAggregationCompletedEvent
        assertEquals("SKLEARN_LOGISTIC_REGRESSION", event.modelPlugin)
        assertEquals(command.aggregatedModelId, event.aggregatedModelId)
    }

    @Test
    fun `missing blank or conflicting plugin fails before emitting model event`() {
        assertThrows(IllegalArgumentException::class.java) {
            decision.decide(command, SecureAggregationSessionState(), result)
        }
        configuration("")
        assertThrows(IllegalArgumentException::class.java) {
            decision.decide(command, SecureAggregationSessionState(), result)
        }
        configuration("SKLEARN_LOGISTIC_REGRESSION")
        assertThrows(IllegalArgumentException::class.java) {
            decision.decide(command.copy(modelPlugin = "OTHER"), SecureAggregationSessionState(), result)
        }
    }

    private fun configuration(plugin: String) {
        `when`(repository.findProjectionById(command.trainingRunConfigurationId)).thenReturn(
            TrainingRunConfigurationCatalogReadModelProjection().apply { initialModelPlugin = plugin },
        )
    }
}
