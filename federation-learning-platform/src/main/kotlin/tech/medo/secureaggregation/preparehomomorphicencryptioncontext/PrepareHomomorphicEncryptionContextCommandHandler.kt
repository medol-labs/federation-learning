package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextCommand

import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState



@Component
class PrepareHomomorphicEncryptionContextCommandHandler(
    private val decision: PrepareHomomorphicEncryptionContextDecision
) {
    @CommandHandler
    fun handle(
        command: PrepareHomomorphicEncryptionContextCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
