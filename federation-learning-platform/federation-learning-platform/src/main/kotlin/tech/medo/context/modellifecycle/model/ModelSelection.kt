package tech.medo.modellifecycle.model

import java.util.UUID;


data class ModelSelection(
    val modelId: UUID
)

object ModelTags {
    const val MODEL_ID = "modelId"
}

object ModelMetadata {
    val concepts = listOf("Model")
}
