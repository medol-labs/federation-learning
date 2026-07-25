package tech.medo.secureaggregation.completehomomorphicaggregationsession

import org.springframework.stereotype.Component
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand

import tech.medo.secureaggregation.events.SecureAggregationCompletedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState





@Component
class CompleteHomomorphicAggregationSessionDecision {
    fun decide(command: CompleteHomomorphicAggregationSessionCommand, state: SecureAggregationSessionState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            SecureAggregationCompletedEvent(secureAggregationSessionId = command.secureAggregationSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, aggregatedModelVersionId = command.aggregatedModelVersionId, modelFormat = command.modelFormat, modelHash = command.modelHash)
        )
    }
}
