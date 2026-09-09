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

        if (!command.secureAggregationRequired) {
            log.info(
                "Skip secure aggregation request because secure aggregation is not required. trainingJobId={}, roundId={}",
                command.trainingJobId,
                command.roundId
            )
            return emptyList()
        }

        val minimumNodesPerRound = command.minimumNodesPerRound.takeIf { it > 0 } ?: 1
        val selectedRuntimeIds = command.selectedRuntimeIds.distinct()

        if (selectedRuntimeIds.isEmpty()) {
            log.warn(
                "Skip secure aggregation request because no selected runtime was provided. trainingJobId={}, roundId={}",
                command.trainingJobId,
                command.roundId
            )
            return emptyList()
        }
        if (selectedRuntimeIds.size < minimumNodesPerRound) {
            log.warn(
                "Skip secure aggregation request because selected runtime count is below quorum. trainingJobId={}, roundId={}, selectedRuntimeCount={}, minimumNodesPerRound={}",
                command.trainingJobId,
                command.roundId,
                selectedRuntimeIds.size,
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
                roundNumber = command.roundNumber,
                requiredParticipantCount = command.requiredParticipantCount,
                selectedOrganizationIds = command.selectedOrganizationIds.distinct(),
                selectedRuntimeIds = selectedRuntimeIds,
                selectedOrganizationCount = command.selectedOrganizationCount,
                selectedRuntimeCount = selectedRuntimeIds.size,
                minimumNodesPerRound = minimumNodesPerRound,
                maxRounds = command.maxRounds,
                minimumAccuracy = command.minimumAccuracy,
                secureAggregationRequired = command.secureAggregationRequired
            )
        )
    }
}
