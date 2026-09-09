package tech.medo.datasetgovernance.domain

object Concepts {
    data object FeatureSchema {
        const val NAME = "FeatureSchema"
        val slices = listOf("DefineFeatureSchema", "PublishFeatureSchema", "DeprecateFeatureSchema", "RetireFeatureSchema", "FeatureSchemaCatalog", "SupersedeFeatureSchemaVersion", "MarkCurrentRecommendedFeatureSchemaVersion", "CurrentRecommendedFeatureSchemaCatalog")
        val states = listOf("Draft", "Published", "Deprecated", "Retired")
    }

    data object RuntimeDatasetMetadata {
        const val NAME = "RuntimeDatasetMetadata"
        val slices = listOf("RecordRuntimeDatasetMetadata", "RecordRuntimeDatasetReprofiledMetadata", "RuntimeDatasetMetadataCatalog")
        val states = listOf("MetadataReported")
    }
}
