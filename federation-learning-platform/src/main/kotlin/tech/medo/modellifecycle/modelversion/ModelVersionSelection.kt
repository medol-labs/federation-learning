package tech.medo.modellifecycle.modelversion

import java.util.UUID;


data class ModelVersionSelection(
    val modelVersionId: UUID
)

object ModelVersionTags {
    const val MODEL_VERSION_ID = "modelVersionId"
}

object ModelVersionMetadata {
    val concepts = listOf("ModelVersion")
}
