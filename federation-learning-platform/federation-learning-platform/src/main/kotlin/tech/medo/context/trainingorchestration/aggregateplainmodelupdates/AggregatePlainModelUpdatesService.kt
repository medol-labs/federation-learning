package tech.medo.trainingorchestration.aggregateplainmodelupdates

import java.util.UUID;
import java.math.BigDecimal;

interface AggregatePlainModelUpdatesService {
    fun supports(input: AggregatePlainModelUpdatesInput): Boolean = true
    fun execute(input: AggregatePlainModelUpdatesInput): AggregatePlainModelUpdatesResult
}

data class AggregatePlainModelUpdatesInput(
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregationAlgorithm: String,
    val aggregatedModelId: UUID,
    val modelUpdateArtifactRefs: List<String>
)

sealed interface AggregatePlainModelUpdatesResult {
    data class Succeeded(
        val aggregatedModelName: String,
        val aggregatedModelVersion: String,
        val aggregatedModelDescription: String?,
        val modelSourceType: String,
        val aggregatedModelArtifactUri: String,
        val aggregatedModelRegistryRef: String,
        val modelFormat: String,
        val modelArtifactDigest: String,
        val aggregatedModelSignatureUri: String?,
        val aggregatedModelSizeBytes: Int?
    ) : AggregatePlainModelUpdatesResult


}
