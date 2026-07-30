package tech.medo.runtimeagentoperations.runtimedatasetbinding

import java.util.UUID;


data class RuntimeDatasetBindingSelection(
    val datasetId: UUID,
    val runtimeId: UUID
)

object RuntimeDatasetBindingTags {
    const val DATASET_ID = "datasetId"
    const val RUNTIME_ID = "runtimeId"
}

object RuntimeDatasetBindingMetadata {
    val concepts = listOf("RuntimeDatasetBinding")
}
