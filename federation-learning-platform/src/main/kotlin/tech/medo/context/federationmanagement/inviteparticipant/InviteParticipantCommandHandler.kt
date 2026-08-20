package tech.medo.federationmanagement.inviteparticipant

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.federationmanagement.inviteparticipant.InviteParticipantCommand





@Component
class InviteParticipantCommandHandler(
    private val decision: InviteParticipantDecision
) {
    @CommandHandler
    fun handle(
        command: InviteParticipantCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
