package tech.medo.datasetgovernance.featureschema

data class FeatureSchemaFeatureDomainVersionSelection(
    val normalizedFeatureDomain: String,
    val normalizedVersion: String
)

object FeatureSchemaFeatureDomainVersionReservationTags {
    const val FEATURE_DOMAIN = "featureDomain"
    const val VERSION = "version"
}
