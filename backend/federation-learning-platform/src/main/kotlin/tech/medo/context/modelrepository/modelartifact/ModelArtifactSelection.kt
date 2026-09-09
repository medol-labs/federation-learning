package tech.medo.modelrepository.modelartifact



data class ModelArtifactSelection(
    val modelName: String,
    val modelVersion: String
)

object ModelArtifactTags {
    const val MODEL_NAME = "modelName"
    const val MODEL_VERSION = "modelVersion"
}

object ModelArtifactMetadata {
    val concepts = listOf("ModelArtifact")
}
