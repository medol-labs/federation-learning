package tech.medo.federationmanagement.approveparticipant

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.approveparticipant.ApproveParticipantCommand

import tech.medo.federationmanagement.federationmembership.FederationMembershipState



@Component
class ApproveParticipantCommandHandler(
    private val decision: ApproveParticipantDecision
) {
    @CommandHandler
    fun handle(
        command: ApproveParticipantCommand,
        @InjectEntity(idProperty = "selection") state: FederationMembershipState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
