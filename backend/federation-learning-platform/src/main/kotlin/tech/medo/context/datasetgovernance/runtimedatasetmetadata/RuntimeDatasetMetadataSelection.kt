package tech.medo.datasetgovernance.runtimedatasetmetadata

import java.util.UUID;


data class RuntimeDatasetMetadataSelection(
    val runtimeDatasetBindingId: UUID
)

object RuntimeDatasetMetadataTags {
    const val RUNTIME_DATASET_BINDING_ID = "runtimeDatasetBindingId"
}

object RuntimeDatasetMetadataMetadata {
    val concepts = listOf("RuntimeDatasetMetadata")
}
