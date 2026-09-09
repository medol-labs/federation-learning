package tech.medo.identityaccessmanagement.registerpermission

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.registerpermission.RegisterPermissionCommand





@Component
class RegisterPermissionCommandHandler(
    private val decision: RegisterPermissionDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterPermissionCommand,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command))
    }
}
