package tech.medo.federationmanagement.suspendfederation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.suspendfederation.SuspendFederationCommand

import tech.medo.federationmanagement.federation.FederationState



@Component
class SuspendFederationCommandHandler(
    private val decision: SuspendFederationDecision
) {
    @CommandHandler
    fun handle(
        command: SuspendFederationCommand,
        @InjectEntity(idProperty = "federationId") state: FederationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
