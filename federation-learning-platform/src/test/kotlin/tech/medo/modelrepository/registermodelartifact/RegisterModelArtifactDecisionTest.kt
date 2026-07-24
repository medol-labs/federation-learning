package tech.medo.modelrepository.registermodelartifact

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand
import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent



import java.util.UUID;


class RegisterModelArtifactDecisionTest {
    @Test
    fun RegisterPullableModelArtifact() {


        val command = RegisterModelArtifactCommand(
            modelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            modelArtifactRef = "models/model-1",
            modelRepositoryRef = "model-repo",
            modelFormat = "ONNX",
            modelHash = "sha256:abc",
            modelSignatureRef = null,
            modelSizeBytes = null,
            sourceType = "INITIAL"
        )

        val events = RegisterModelArtifactDecision().decide(
            command
        )

        val event = events.filterIsInstance<ModelArtifactRegisteredEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.modelVersionId)
        assertEquals("models/model-1", event.modelArtifactRef)
        assertEquals("model-repo", event.modelRepositoryRef)
        assertEquals("ONNX", event.modelFormat)
        assertEquals("sha256:abc", event.modelHash)
        assertEquals(command.modelSignatureRef, event.modelSignatureRef)
        assertEquals(command.modelSizeBytes, event.modelSizeBytes)
        assertEquals("INITIAL", event.sourceType)
    }
}
