package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import org.springframework.stereotype.Component
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextCommand

import tech.medo.secureaggregation.events.HomomorphicEncryptionContextPreparedEvent
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState


import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum


@Component
class PrepareHomomorphicEncryptionContextDecision {
    fun decide(command: PrepareHomomorphicEncryptionContextCommand, state: SecureAggregationSessionState): List<Any> {
        require(state.currentState == SecureAggregationSessionStateEnum.PARTICIPANTS_SELECTED) {
            "PrepareHomomorphicEncryptionContext requires SecureAggregationSession to be ParticipantsSelected."
        }
        return listOf(
            HomomorphicEncryptionContextPreparedEvent(secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, encryptedParameterScale = command.encryptedParameterScale)
        )
    }
}
