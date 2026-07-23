package tech.medo.secureaggregation.selectsecureaggregationparticipants

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.selectsecureaggregationparticipants.SelectSecureAggregationParticipantsCommand

import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState



@Component
class SelectSecureAggregationParticipantsCommandHandler(
    private val decision: SelectSecureAggregationParticipantsDecision
) {
    @CommandHandler
    fun handle(
        command: SelectSecureAggregationParticipantsCommand,
        @InjectEntity(idProperty = "secureAggregationSessionId") state: SecureAggregationSessionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
