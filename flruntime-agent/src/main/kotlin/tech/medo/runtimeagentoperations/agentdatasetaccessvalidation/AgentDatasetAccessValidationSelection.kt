package tech.medo.runtimeagentoperations.agentdatasetaccessvalidation

import java.util.UUID;


data class AgentDatasetAccessValidationSelection(
    val datasetAccessValidationId: UUID
)

object AgentDatasetAccessValidationTags {
    const val DATASET_ACCESS_VALIDATION_ID = "datasetAccessValidationId"
}

object AgentDatasetAccessValidationMetadata {
    val concepts = listOf("AgentDatasetAccessValidation")
}
