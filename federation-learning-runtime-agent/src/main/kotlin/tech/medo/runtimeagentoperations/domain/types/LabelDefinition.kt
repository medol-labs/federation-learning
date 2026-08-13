package tech.medo.runtimeagentoperations.domain.types

data class LabelDefinition(
    val labelName: String,
    val dataType: String,
    val cardinality: Int,
    val classLabels: List<String>,
    val isMultilabel: Boolean,
    val description: String?,
    val validationRules: List<String>,
    val defaultValue: String?
)
