package tech.medo.modelrepository.downloadmodelartifact

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactCommand
import tech.medo.modelrepository.events.ModelArtifactRegisteredEvent
import tech.medo.modelrepository.events.ModelArtifactDownloadAuthorizedEvent
import tech.medo.modelrepository.modelartifact.ModelArtifactState
import tech.medo.modelrepository.downloadmodelartifact.DownloadModelArtifactResult
import java.util.UUID

class DownloadModelArtifactDecisionTest {
    @Test
    fun AuthorizeRegisteredModelArtifactDownload() {
        val state = ModelArtifactState()
        state.evolve(
            ModelArtifactRegisteredEvent(
            modelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            modelName = "credit-risk",
            modelPlugin = "",
            modelVersion = "v1",
            modelDescription = null,
            sourceType = "",
            modelArtifactUri = "http://support/api/files/file-1/content",
            modelRegistryRef = "",
            modelFormat = "ONNX",
            modelArtifactDigest = "sha256:model",
            modelSignatureUri = null,
            modelSizeBytes = null
            )
        )

        val command = DownloadModelArtifactCommand(
            modelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            modelName = "",
            modelVersion = ""
        )

        val events = (object : DownloadModelArtifactDecision {}).decide(
            command,
            state = state,
            portResult = DownloadModelArtifactResult.Succeeded(
                modelName = "",
                modelVersion = "",
                modelFormat = "",
                modelArtifactDigest = "",
                downloadUri = ""
            )
        )

        val event = events.filterIsInstance<ModelArtifactDownloadAuthorizedEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("model-1".toByteArray()), event.modelId)
    }
}
