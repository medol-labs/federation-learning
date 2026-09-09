package tech.medo.identityaccessmanagement.registerrole

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.registerrole.RegisterRoleCommand





@Component
class RegisterRoleCommandHandler(
    private val decision: RegisterRoleDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterRoleCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
