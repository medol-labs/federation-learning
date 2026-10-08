package tech.medo.trainingorchestration.personalizedmodelresult

import java.util.UUID;


data class PersonalizedModelResultSelection(
    val personalizedModelId: UUID
)

object PersonalizedModelResultTags {
    const val PERSONALIZED_MODEL_ID = "personalizedModelId"
}

object PersonalizedModelResultMetadata {
    val concepts = listOf("PersonalizedModelResult")
}
