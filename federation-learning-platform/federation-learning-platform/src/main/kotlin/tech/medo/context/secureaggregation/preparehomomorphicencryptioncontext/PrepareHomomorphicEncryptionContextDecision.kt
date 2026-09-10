package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextCommand

import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextResult
import tech.medo.secureaggregation.events.HomomorphicEncryptionContextPreparedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState


import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


interface PrepareHomomorphicEncryptionContextDecision {
    fun decide(command: PrepareHomomorphicEncryptionContextCommand, state: SecureAggregationSessionState, portResult: PrepareHomomorphicEncryptionContextResult): List<Any> {
        require(state.currentState == SecureAggregationSessionStateEnum.PARTICIPANTS_SELECTED) {
            "PrepareHomomorphicEncryptionContext requires SecureAggregationSession to be ParticipantsSelected."
        }
        return when (portResult) {
                    is PrepareHomomorphicEncryptionContextResult.Succeeded -> listOf(HomomorphicEncryptionContextPreparedEvent(secureAggregationSessionId = command.secureAggregationSessionId, trainingJobId = command.trainingJobId, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired, encryptionScheme = portResult.encryptionScheme, publicKeyVersion = portResult.publicKeyVersion, publicKeyRef = portResult.publicKeyRef, encryptedParameterScale = portResult.encryptedParameterScale))
                }
    }
}
