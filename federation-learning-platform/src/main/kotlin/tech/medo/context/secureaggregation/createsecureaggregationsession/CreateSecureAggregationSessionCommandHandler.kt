package tech.medo.secureaggregation.createsecureaggregationsession

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.createsecureaggregationsession.CreateSecureAggregationSessionCommand





@Component
class CreateSecureAggregationSessionCommandHandler(
    private val decision: CreateSecureAggregationSessionDecision
) {
    @CommandHandler
    fun handle(
        command: CreateSecureAggregationSessionCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
