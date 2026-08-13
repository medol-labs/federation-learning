package tech.medo.secureaggregation.completehomomorphicaggregationsession

import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult
import tech.medo.secureaggregation.events.SecureAggregationCompletedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState





interface CompleteHomomorphicAggregationSessionDecision {
    fun decide(command: CompleteHomomorphicAggregationSessionCommand, state: SecureAggregationSessionState, portResult: CompleteHomomorphicAggregationSessionResult): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is CompleteHomomorphicAggregationSessionResult.Succeeded -> listOf(SecureAggregationCompletedEvent(secureAggregationSessionId = command.secureAggregationSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, aggregatedModelId = command.aggregatedModelId, aggregatedModelArtifactUri = command.aggregatedModelArtifactUri, aggregatedModelRegistryRef = command.aggregatedModelRegistryRef, modelFormat = command.modelFormat, modelArtifactDigest = command.modelArtifactDigest, aggregatedModelSignatureUri = command.aggregatedModelSignatureUri))
                }
    }
}
