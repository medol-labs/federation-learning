package tech.medo.trainingorchestration.requestsecureaggregation

import tech.medo.trainingorchestration.requestsecureaggregation.RequestSecureAggregationCommand


import tech.medo.trainingorchestration.events.SecureAggregationRequestedEvent
import tech.medo.trainingorchestration.traininground.TrainingRoundState





interface RequestSecureAggregationDecision {
    fun decide(command: RequestSecureAggregationCommand, state: TrainingRoundState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            SecureAggregationRequestedEvent(trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, roundId = command.roundId, roundNumber = command.roundNumber, requiredParticipantCount = command.requiredParticipantCount, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired)
        )
    }
}
