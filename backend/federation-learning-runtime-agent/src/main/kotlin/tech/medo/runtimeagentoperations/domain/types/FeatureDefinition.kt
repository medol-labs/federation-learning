package tech.medo.runtimeagentoperations.domain.types

data class FeatureDefinition(
    val featureName: String,
    val dataType: String,
    val required: Boolean,
    val nullable: Boolean,
    val description: String?,
    val validationRules: List<String>,
    val defaultValue: String?,
    val isIdentifier: Boolean,
    val isSensitive: Boolean,
    val encodingStrategy: String?,
    val featureTags: List<String>
)
