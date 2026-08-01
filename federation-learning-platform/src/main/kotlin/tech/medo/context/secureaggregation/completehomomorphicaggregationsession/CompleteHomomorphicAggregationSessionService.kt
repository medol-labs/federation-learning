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
    val aggregatedModelVersionId: UUID,
    val modelFormat: String,
    val modelHash: String
)

sealed interface CompleteHomomorphicAggregationSessionResult {
    class Succeeded : CompleteHomomorphicAggregationSessionResult


}
