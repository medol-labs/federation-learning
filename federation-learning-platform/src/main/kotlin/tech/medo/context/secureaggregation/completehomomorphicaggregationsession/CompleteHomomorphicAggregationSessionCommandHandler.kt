package tech.medo.secureaggregation.completehomomorphicaggregationsession

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand

import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState



@Component
class CompleteHomomorphicAggregationSessionCommandHandler(
    private val decision: CompleteHomomorphicAggregationSessionDecision
) {
    @CommandHandler
    fun handle(
        command: CompleteHomomorphicAggregationSessionCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
