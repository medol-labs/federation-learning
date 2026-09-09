package tech.medo.runtimeagentoperations.dataset

import java.util.UUID;


data class DatasetSelection(
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String
)

object DatasetTags {
    const val ORGANIZATION_ID = "organizationId"
    const val FEATURE_SCHEMA_ID = "featureSchemaId"
    const val DATASET_NAME = "datasetName"
}

object DatasetMetadata {
    val concepts = listOf("Dataset")
}
