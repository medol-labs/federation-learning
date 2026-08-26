package tech.medo.modelrepository.registermodelartifact

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand
import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import java.util.UUID

class RegisterModelArtifactDecisionTest {
    @Test
    fun RegisterPullableModelArtifact() {


        val command = RegisterModelArtifactCommand(
            modelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            modelName = "credit-risk",
            modelVersion = "v1",
            modelDescription = "Baseline credit risk classifier for federated training",
            sourceType = "EXTERNAL",
            stagedFileId = UUID.nameUUIDFromBytes("staged-file-1".toByteArray()),
            modelFormat = "ONNX"
        )

        val events = (object : RegisterModelArtifactDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<ModelArtifactRegisteredEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.modelId)
        assertEquals("credit-risk", event.modelName)
        assertEquals("v1", event.modelVersion)
        assertEquals("Baseline credit risk classifier for federated training", event.modelDescription)
        assertEquals("EXTERNAL", event.sourceType)
        assertEquals("ONNX", event.modelFormat)
    }
}
