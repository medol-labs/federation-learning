package tech.medo.secureaggregation.completehomomorphicaggregationsession

import java.util.UUID;
import java.math.BigDecimal;

interface CompleteHomomorphicAggregationSessionService {
    fun supports(input: CompleteHomomorphicAggregationSessionInput): Boolean = true
    fun execute(input: CompleteHomomorphicAggregationSessionInput): CompleteHomomorphicAggregationSessionResult
}

data class CompleteHomomorphicAggregationSessionInput(
    val secureAggregationSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val trainingJobObjective: String,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val maxRounds: Int,
    val minimumAccuracy: BigDecimal,
    val aggregatedModelId: UUID,
    val encryptedUpdateArtifactRefs: List<String>
)

sealed interface CompleteHomomorphicAggregationSessionResult {
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
    ) : CompleteHomomorphicAggregationSessionResult


}
