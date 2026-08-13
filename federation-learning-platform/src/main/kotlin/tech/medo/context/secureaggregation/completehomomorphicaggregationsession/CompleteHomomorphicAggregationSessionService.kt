package tech.medo.secureaggregation.completehomomorphicaggregationsession

import java.util.UUID;

interface CompleteHomomorphicAggregationSessionService {
    fun supports(input: CompleteHomomorphicAggregationSessionInput): Boolean = true
    fun execute(input: CompleteHomomorphicAggregationSessionInput): CompleteHomomorphicAggregationSessionResult
}

data class CompleteHomomorphicAggregationSessionInput(
    val secureAggregationSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val aggregatedModelId: UUID,
    val aggregatedModelArtifactUri: String,
    val aggregatedModelRegistryRef: String,
    val modelFormat: String,
    val modelArtifactDigest: String,
    val aggregatedModelSignatureUri: String?
)

sealed interface CompleteHomomorphicAggregationSessionResult {
    class Succeeded : CompleteHomomorphicAggregationSessionResult


}
