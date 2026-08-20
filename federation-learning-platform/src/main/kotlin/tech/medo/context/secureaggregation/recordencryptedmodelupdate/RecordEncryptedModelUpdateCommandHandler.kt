package tech.medo.secureaggregation.recordencryptedmodelupdate

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.recordencryptedmodelupdate.RecordEncryptedModelUpdateCommand

import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState




@Component
class RecordEncryptedModelUpdateCommandHandler(
    private val decision: RecordEncryptedModelUpdateDecision
) {
    @CommandHandler
    fun handle(
        command: RecordEncryptedModelUpdateCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
