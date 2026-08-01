package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.declaredataset

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition
import java.util.UUID

@FeignClient(
    name = "federationLearningPlatformFeatureSchemaCatalogClient",
    url = "\${runtime-agent.feature-schema-lookup.platform-url:http://localhost:8081}"
)
interface PlatformFeatureSchemaCatalogClient {
    @GetMapping("/featureschema/featureschemacatalog/{id}")
    fun findFeatureSchema(@PathVariable id: UUID): PlatformFeatureSchemaCatalogResponse
}

data class PlatformFeatureSchemaCatalogResponse(
    val featureSchemaId: UUID? = null,
    val features: List<PlatformFeatureDefinition> = emptyList(),
    val labels: List<PlatformLabelDefinition> = emptyList(),
    val schemaStatus: String? = null
)

data class PlatformFeatureDefinition(
    val featureName: String = "",
    val dataType: String = "",
    val required: Boolean = false,
    val nullable: Boolean = true,
    val description: String? = null,
    val validationRules: List<String> = emptyList(),
    val defaultValue: String? = null,
    val isIdentifier: Boolean = false,
    val isSensitive: Boolean = false,
    val encodingStrategy: String? = null,
    val featureTags: List<String> = emptyList()
)

data class PlatformLabelDefinition(
    val labelName: String = "",
    val dataType: String = "",
    val cardinality: Int = 0,
    val classLabels: List<String> = emptyList(),
    val isMultilabel: Boolean = false,
    val description: String? = null,
    val validationRules: List<String> = emptyList(),
    val defaultValue: String? = null
)

fun PlatformFeatureDefinition.toRuntimeFeatureDefinition(): FeatureDefinition =
    FeatureDefinition(
        featureName = featureName,
        dataType = dataType,
        required = required,
        nullable = nullable,
        description = description,
        validationRules = validationRules,
        defaultValue = defaultValue,
        isIdentifier = isIdentifier,
        isSensitive = isSensitive,
        encodingStrategy = encodingStrategy,
        featureTags = featureTags
    )

fun PlatformLabelDefinition.toRuntimeLabelDefinition(): LabelDefinition =
    LabelDefinition(
        labelName = labelName,
        dataType = dataType,
        cardinality = cardinality,
        classLabels = classLabels,
        isMultilabel = isMultilabel,
        description = description,
        validationRules = validationRules,
        defaultValue = defaultValue
    )
