package tech.medo.datasetgovernance.supersedefeatureschemaversion

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.datasetgovernance.supersedefeatureschemaversion.SupersedeFeatureSchemaVersionCommand
import tech.medo.datasetgovernance.events.FeatureSchemaDefinedEvent
import tech.medo.datasetgovernance.events.FeatureSchemaVersionSupersededEvent

import tech.medo.datasetgovernance.featureschema.FeatureSchemaState

import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;


class SupersedeFeatureSchemaVersionDecisionTest {
    @Test
    fun SupersedeOlderVersion() {
        val state = FeatureSchemaState()
        state.evolve(
            FeatureSchemaDefinedEvent(
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            featureDomain = "CreditRisk",
            version = "1.0.0",
            dataModality = "",
            features = emptyList(),
            labels = emptyList(),
            featureCount = 0
            )
        )
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

        val command = SupersedeFeatureSchemaVersionCommand(
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            supersededByFeatureSchemaId = UUID.nameUUIDFromBytes("schema-2".toByteArray()),
            supersessionReason = null,
            featureDomain = "",
            version = ""
        )

        val events = (object : SupersedeFeatureSchemaVersionDecision {}).decide(
            command,
            state = state
        )

        val event = events.filterIsInstance<FeatureSchemaVersionSupersededEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("schema-1".toByteArray()), event.featureSchemaId)
        assertEquals(UUID.nameUUIDFromBytes("schema-2".toByteArray()), event.supersededByFeatureSchemaId)
        assertEquals(command.supersessionReason, event.supersessionReason)
    }
}
