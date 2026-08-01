package tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion.MarkCurrentRecommendedFeatureSchemaVersionCommand
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.CurrentRecommendedFeatureSchemaVersionMarkedEvent

import tech.medo.datasetgovernance.featureschema.FeatureSchemaState

import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;


class MarkCurrentRecommendedFeatureSchemaVersionDecisionTest {
    @Test
    fun MarkNewRecommendedVersion() {
        val state = FeatureSchemaState()
        state.evolve(
            FeatureSchemaDefinedEvent(
            featureSchemaId = UUID.nameUUIDFromBytes("schema-2".toByteArray()),
            featureDomain = "CreditRisk",
            version = "1.1.0",
            dataModality = "",
            features = emptyList(),
            labels = emptyList(),
            featureCount = 0
            )
        )

        val command = MarkCurrentRecommendedFeatureSchemaVersionCommand(
            featureSchemaId = UUID.nameUUIDFromBytes("schema-2".toByteArray()),
            recommendationNote = null,
            featureDomain = "",
            version = ""
        )

        val events = (object : MarkCurrentRecommendedFeatureSchemaVersionDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<CurrentRecommendedFeatureSchemaVersionMarkedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("schema-2".toByteArray()), event.featureSchemaId)
        assertEquals(command.recommendationNote, event.recommendationNote)
    }
}
