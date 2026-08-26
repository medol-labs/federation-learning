package tech.medo.runtimeagentoperations.declaredataset

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetCommand
import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetResult
import java.util.UUID
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition

class DeclareDatasetDecisionTest {
    @Test
    fun DeclareDatasetWithFeatureSchemaSnapshot() {


        val command = DeclareDatasetCommand(
            datasetId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            featureSchemaId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            datasetName = "credit-risk",
            datasetUsage = "TRAINING"
        )

        val events = (object : DeclareDatasetDecision {}).decide(
            command,
            portResult = DeclareDatasetResult.Succeeded(
                features = emptyList(),
                labels = emptyList()
            )
        )

        val event = events.filterIsInstance<DatasetDeclaredEvent>().single()
        assertEquals(UUID.fromString("11111111-1111-4111-8111-111111111111"), event.datasetId)
        assertEquals(UUID.fromString("22222222-2222-4222-8222-222222222222"), event.organizationId)
        assertEquals(UUID.fromString("33333333-3333-4333-8333-333333333333"), event.featureSchemaId)
        assertEquals("credit-risk", event.datasetName)
        assertEquals("TRAINING", event.datasetUsage)
    }
}
