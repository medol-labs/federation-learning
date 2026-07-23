package tech.medo.federationmanagement.revokeparticipantinvitation

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.revokeparticipantinvitation.RevokeParticipantInvitationCommand

import tech.medo.federationmanagement.federationmembership.FederationMembershipState



@Component
class RevokeParticipantInvitationCommandHandler(
    private val decision: RevokeParticipantInvitationDecision
) {
    @CommandHandler
    fun handle(
        command: RevokeParticipantInvitationCommand,
        @InjectEntity(idProperty = "selection") state: FederationMembershipState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
