package tech.medo.domain.trainingorchestration.requestsecureaggregation

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum
import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationCommand
import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationDecision
import tech.medo.trainingorchestration.traininground.TrainingRoundState

@Component
class RequestSecureAggregationDecisionComponent : RequestSecureAggregationDecision {
    private val log = LoggerFactory.getLogger(RequestSecureAggregationDecisionComponent::class.java)

    override fun decide(command: RequestSecureAggregationCommand, state: TrainingRoundState): List<Any> {
        if (state.currentState == TrainingRoundStateEnum.AGGREGATING) {
            log.info(
                "Skip secure aggregation request because round is already aggregating. trainingJobId={}, roundId={}",
                command.trainingJobId,
                command.roundId
            )
            return emptyList()
        }

        val minimumNodesPerRound = command.minimumNodesPerRound.takeIf { it > 0 }
            ?: state.minimumNodesPerRound
            ?: 1
        val acceptedModelUpdateCount = command.acceptedModelUpdateCount.takeIf { it > 0 }
            ?: command.acceptedRuntimeIds.distinct().size

        if (command.acceptedRuntimeIds.isEmpty()) {
            log.warn(
                "Skip secure aggregation request because no accepted runtime was provided. trainingJobId={}, roundId={}",
                command.trainingJobId,
                command.roundId
            )
            return emptyList()
        }
        if (acceptedModelUpdateCount < minimumNodesPerRound) {
            log.warn(
                "Skip secure aggregation request because accepted update count is below quorum. trainingJobId={}, roundId={}, acceptedModelUpdateCount={}, minimumNodesPerRound={}",
                command.trainingJobId,
                command.roundId,
                acceptedModelUpdateCount,
                minimumNodesPerRound
            )
            return emptyList()
        }
        return listOf(
            SecureAggregationRequestedEvent(
                trainingJobId = command.trainingJobId,
                trainingRunConfigurationId = state.trainingRunConfigurationId ?: command.trainingRunConfigurationId,
                featureSchemaId = state.featureSchemaId ?: command.featureSchemaId,
                roundId = command.roundId,
                acceptedModelUpdateCount = acceptedModelUpdateCount,
                acceptedRuntimeIds = command.acceptedRuntimeIds.distinct(),
                minimumNodesPerRound = minimumNodesPerRound
            )
        )
    }
}
