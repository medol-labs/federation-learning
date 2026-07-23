package tech.medo.trainingorchestration.requestsecureaggregation

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationCommand

import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





@Component
class RequestSecureAggregationDecision {
    fun decide(command: RequestSecureAggregationCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate child/member state before appending events.
        return listOf(
            SecureAggregationRequestedEvent(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, acceptedModelUpdateCount = command.acceptedModelUpdateCount, acceptedRuntimeIds = command.acceptedRuntimeIds, minimumNodesPerRound = command.minimumNodesPerRound)
        )
    }
}
