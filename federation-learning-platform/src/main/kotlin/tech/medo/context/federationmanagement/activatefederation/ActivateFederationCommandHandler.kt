package tech.medo.federationmanagement.activatefederation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.activatefederation.ActivateFederationCommand

import tech.medo.federationmanagement.federation.FederationState



@Component
class ActivateFederationCommandHandler(
    private val decision: ActivateFederationDecision
) {
    @CommandHandler
    fun handle(
        command: ActivateFederationCommand,
        @InjectEntity(idProperty = "federationName") state: FederationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
