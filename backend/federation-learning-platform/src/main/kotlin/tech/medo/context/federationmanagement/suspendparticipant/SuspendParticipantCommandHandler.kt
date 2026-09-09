package tech.medo.federationmanagement.suspendparticipant

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.suspendparticipant.SuspendParticipantCommand

import tech.medo.federationmanagement.federationmembership.FederationMembershipState



@Component
class SuspendParticipantCommandHandler(
    private val decision: SuspendParticipantDecision
) {
    @CommandHandler
    fun handle(
        command: SuspendParticipantCommand,
        @InjectEntity(idProperty = "selection") state: FederationMembershipState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
