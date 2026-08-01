package tech.medo.secureaggregation.preparehomomorphicencryptioncontext

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextCommand
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextInput
import tech.medo.secureaggregation.preparehomomorphicencryptioncontext.PrepareHomomorphicEncryptionContextService
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState



@Component
class PrepareHomomorphicEncryptionContextCommandHandler(
    private val decision: PrepareHomomorphicEncryptionContextDecision,
    private val prepareHomomorphicEncryptionContextService: PrepareHomomorphicEncryptionContextService
) {
    @CommandHandler
    fun handle(
        command: PrepareHomomorphicEncryptionContextCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        val input = PrepareHomomorphicEncryptionContextInput(secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, encryptedParameterScale = command.encryptedParameterScale)
        val portResult = prepareHomomorphicEncryptionContextService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
