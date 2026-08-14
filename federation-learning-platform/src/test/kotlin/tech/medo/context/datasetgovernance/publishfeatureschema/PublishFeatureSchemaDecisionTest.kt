package tech.medo.datasetgovernance.publishfeatureschema

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.datasetgovernance.publishfeatureschema.PublishFeatureSchemaCommand
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState
import java.util.UUID
import tech.medo.datasetgovernance.domain.types.FeatureDefinition
import tech.medo.datasetgovernance.domain.types.LabelDefinition

class PublishFeatureSchemaDecisionTest {
    @Test
    fun PublishDraftSchema() {
        val state = FeatureSchemaState()
        state.evolve(
            FeatureSchemaDefinedEvent(
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            featureDomain = "",
            version = "",
            dataModality = "",
            features = emptyList(),
            labels = emptyList(),
            featureCount = 0
            )
        )

        val command = PublishFeatureSchemaCommand(
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            publishNote = null,
            featureDomain = "",
            version = ""
        )

        val events = (object : PublishFeatureSchemaDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<FeatureSchemaPublishedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("schema-1".toByteArray()), event.featureSchemaId)
        assertEquals(command.publishNote, event.publishNote)
    }
}
