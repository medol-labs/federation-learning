package tech.medo.runtimeagentoperations.runtimedatasetbinding

data class RuntimeDatasetBindingRuntimeIdDatasetIdSelection(
    val normalizedRuntimeId: String,
    val normalizedDatasetId: String
)

object RuntimeDatasetBindingRuntimeIdDatasetIdReservationTags {
    const val RUNTIME_ID = "runtimeId"
    const val DATASET_ID = "datasetId"
}
