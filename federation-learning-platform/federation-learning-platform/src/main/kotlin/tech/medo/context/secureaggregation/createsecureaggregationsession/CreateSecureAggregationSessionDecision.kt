package tech.medo.secureaggregation.createsecureaggregationsession

import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand


import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState





interface CreateSecureAggregationSessionDecision {
    fun decide(command: CreateSecureAggregationSessionCommand): List<Any> {
        return listOf(
            SecureAggregationSessionCreatedEvent(secureAggregationSessionId = command.secureAggregationSessionId, trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, roundId = command.roundId, roundNumber = command.roundNumber, requiredParticipantCount = command.requiredParticipantCount, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired)
        )
    }
}
