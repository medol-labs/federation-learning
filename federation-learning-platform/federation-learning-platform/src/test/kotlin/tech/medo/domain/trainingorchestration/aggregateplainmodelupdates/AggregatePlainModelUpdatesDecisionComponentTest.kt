package tech.medo.domain.trainingorchestration.aggregateplainmodelupdates

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesCommand
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import tech.medo.trainingorchestration.events.PlainModelAggregationCompletedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelProjection
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository

class AggregatePlainModelUpdatesDecisionComponentTest {
    private val configurationId = UUID.randomUUID()
    private val repository = mock(TrainingRunConfigurationCatalogReadModelRepository::class.java)
    private val decision = AggregatePlainModelUpdatesDecisionComponent(repository)
    private val command = AggregatePlainModelUpdatesCommand(
        trainingJobId = UUID.randomUUID(), trainingRunConfigurationId = configurationId,
        trainingJobObjective = "Multi-round training", featureSchemaId = UUID.randomUUID(),
        roundId = UUID.randomUUID(), roundNumber = 1, maxRounds = 2, minimumAccuracy = BigDecimal.ONE,
        modelPlugin = "", aggregationAlgorithm = "FED_AVG_JSON", modelUpdateArtifactRefs = emptyList(),
    )
    private val result = AggregatePlainModelUpdatesResult.Succeeded(
        "global", "round-1", null, "FEDERATED_TRAINING", "https://example.test/model.json",
        "test", "JSON", "sha256:" + "0".repeat(64), null, 100,
    )

    @Test
    fun `aggregated artifact retains configured plugin for the next round`() {
        configuration("SKLEARN_LOGISTIC_REGRESSION")
        val event = decision.decide(command, TrainingRoundState(), result, LocalDateTime.now()).single()
            as PlainModelAggregationCompletedEvent
        assertEquals("SKLEARN_LOGISTIC_REGRESSION", event.modelPlugin)
        assertEquals(command.aggregatedModelId, event.aggregatedModelId)
    }

    @Test
    fun `missing or conflicting plugin is not silently accepted`() {
        assertThrows(IllegalArgumentException::class.java) {
            decision.decide(command, TrainingRoundState(), result, LocalDateTime.now())
        }
        configuration("")
        assertThrows(IllegalArgumentException::class.java) {
            decision.decide(command, TrainingRoundState(), result, LocalDateTime.now())
        }
        configuration("SKLEARN_LOGISTIC_REGRESSION")
        assertThrows(IllegalArgumentException::class.java) {
            decision.decide(command.copy(modelPlugin = "OTHER"), TrainingRoundState(), result, LocalDateTime.now())
        }
    }

    private fun configuration(plugin: String) {
        `when`(repository.findProjectionById(configurationId)).thenReturn(
            TrainingRunConfigurationCatalogReadModelProjection().apply { initialModelPlugin = plugin },
        )
    }
}
