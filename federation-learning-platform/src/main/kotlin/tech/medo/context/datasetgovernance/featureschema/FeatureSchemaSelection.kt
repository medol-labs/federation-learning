package tech.medo.datasetgovernance.featureschema



data class FeatureSchemaSelection(
    val featureDomain: String,
    val version: String
)

object FeatureSchemaTags {
    const val FEATURE_DOMAIN = "featureDomain"
    const val VERSION = "version"
}

object FeatureSchemaMetadata {
    val concepts = listOf("FeatureSchema")
}
