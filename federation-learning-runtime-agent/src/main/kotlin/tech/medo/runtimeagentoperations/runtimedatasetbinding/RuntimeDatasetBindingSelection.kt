package tech.medo.runtimeagentoperations.runtimedatasetbinding

import java.util.UUID;


data class RuntimeDatasetBindingSelection(
    val runtimeDatasetBindingId: UUID
)

object RuntimeDatasetBindingTags {
    const val RUNTIME_DATASET_BINDING_ID = "runtimeDatasetBindingId"
}

object RuntimeDatasetBindingMetadata {
    val concepts = listOf("RuntimeDatasetBinding")
}
