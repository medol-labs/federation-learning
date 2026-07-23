package tech.medo.modelrepository.modelartifact

import java.util.UUID;


data class ModelArtifactSelection(
    val modelVersionId: UUID
)

object ModelArtifactTags {
    const val MODEL_VERSION_ID = "modelVersionId"
}

object ModelArtifactMetadata {
    val concepts = listOf("ModelArtifact")
}
