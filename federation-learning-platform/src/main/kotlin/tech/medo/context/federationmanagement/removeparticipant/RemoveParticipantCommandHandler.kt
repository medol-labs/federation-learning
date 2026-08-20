package tech.medo.federationmanagement.removeparticipant

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.removeparticipant.RemoveParticipantCommand

import tech.medo.federationmanagement.federationmembership.FederationMembershipState




@Component
class RemoveParticipantCommandHandler(
    private val decision: RemoveParticipantDecision
) {
    @CommandHandler
    fun handle(
        command: RemoveParticipantCommand,
        @InjectEntity(idProperty = "selection") state: FederationMembershipState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
