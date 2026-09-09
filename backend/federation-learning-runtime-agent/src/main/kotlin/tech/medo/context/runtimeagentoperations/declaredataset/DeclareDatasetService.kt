package tech.medo.runtimeagentoperations.declaredataset

import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition;
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition;

interface DeclareDatasetService {
    fun supports(input: DeclareDatasetInput): Boolean = true
    fun execute(input: DeclareDatasetInput): DeclareDatasetResult
}

data class DeclareDatasetInput(
    val datasetId: UUID,
    val organizationId: UUID,
    val featureSchemaId: UUID,
    val datasetName: String,
    val datasetUsage: String
)

sealed interface DeclareDatasetResult {
    data class Succeeded(
        val features: List<FeatureDefinition>,
        val labels: List<LabelDefinition>
    ) : DeclareDatasetResult


}
