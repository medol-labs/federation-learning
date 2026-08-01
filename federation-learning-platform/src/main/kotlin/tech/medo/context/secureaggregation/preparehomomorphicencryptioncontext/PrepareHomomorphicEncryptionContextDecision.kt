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
                    is PrepareHomomorphicEncryptionContextResult.Succeeded -> listOf(HomomorphicEncryptionContextPreparedEvent(secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, encryptedParameterScale = command.encryptedParameterScale))
                }
    }
}
