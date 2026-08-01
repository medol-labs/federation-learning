package tech.medo.secureaggregation.createsecureaggregationsession

import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand

import tech.medo.secureaggregation.events.SecureAggregationSessionCreatedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState





interface CreateSecureAggregationSessionDecision {
    fun decide(command: CreateSecureAggregationSessionCommand): List<Any> {
        return listOf(
            SecureAggregationSessionCreatedEvent(secureAggregationSessionId = command.secureAggregationSessionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, requiredParticipantCount = command.requiredParticipantCount, acceptedRuntimeIds = command.acceptedRuntimeIds)
        )
    }
}
