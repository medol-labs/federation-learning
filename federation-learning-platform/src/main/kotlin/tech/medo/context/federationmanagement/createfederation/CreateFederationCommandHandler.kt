package tech.medo.federationmanagement.createfederation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.createfederation.CreateFederationCommand



import tech.medo.federationmanagement.federation.FederationNameReservationState

@Component
class CreateFederationCommandHandler(
    private val decision: CreateFederationDecision
) {
    @CommandHandler
    fun handle(
        command: CreateFederationCommand,
        @InjectEntity(idProperty = "federationNameSelection") federationNameReservation: FederationNameReservationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, federationNameReservation))
    }
}
