package tech.medo.federationmanagement.reactivatefederation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.reactivatefederation.ReactivateFederationCommand

import tech.medo.federationmanagement.federation.FederationState



@Component
class ReactivateFederationCommandHandler(
    private val decision: ReactivateFederationDecision
) {
    @CommandHandler
    fun handle(
        command: ReactivateFederationCommand,
        @InjectEntity(idProperty = "federationName") state: FederationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
