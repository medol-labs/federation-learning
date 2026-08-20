package tech.medo.secureaggregation.failsecureaggregationsession

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.failsecureaggregationsession.FailSecureAggregationSessionCommand

import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState




@Component
class FailSecureAggregationSessionCommandHandler(
    private val decision: FailSecureAggregationSessionDecision
) {
    @CommandHandler
    fun handle(
        command: FailSecureAggregationSessionCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
