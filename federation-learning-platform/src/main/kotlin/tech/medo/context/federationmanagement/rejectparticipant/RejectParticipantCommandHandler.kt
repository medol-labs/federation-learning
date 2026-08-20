package tech.medo.federationmanagement.rejectparticipant

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.rejectparticipant.RejectParticipantCommand

import tech.medo.federationmanagement.federationmembership.FederationMembershipState




@Component
class RejectParticipantCommandHandler(
    private val decision: RejectParticipantDecision
) {
    @CommandHandler
    fun handle(
        command: RejectParticipantCommand,
        @InjectEntity(idProperty = "selection") state: FederationMembershipState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
