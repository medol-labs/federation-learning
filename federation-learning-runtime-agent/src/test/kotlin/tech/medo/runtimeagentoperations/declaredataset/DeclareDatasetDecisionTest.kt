package tech.medo.runtimeagentoperations.declaredataset

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetCommand
import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent



import java.util.UUID;


class DeclareDatasetDecisionTest {
    @Test
    fun DeclareDatasetEmitsDatasetDeclaredEvent() {
        val events = DeclareDatasetDecision().decide(
            DeclareDatasetCommand(
            datasetId = java.util.UUID.randomUUID(),
            organizationId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            datasetName = "",
            datasetType = "",
            datasetUsage = ""
            )
        )

        assertTrue(events.any { it is DatasetDeclaredEvent })
    }
}
