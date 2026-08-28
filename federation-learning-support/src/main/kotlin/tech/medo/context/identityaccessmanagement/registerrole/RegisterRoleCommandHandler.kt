package tech.medo.identityaccessmanagement.registerrole

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.registerrole.RegisterRoleCommand

import tech.medo.identityaccessmanagement.role.RoleState




@Component
class RegisterRoleCommandHandler(
    private val decision: RegisterRoleDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterRoleCommand,
        @InjectEntity(idProperty = "roleCode") state: RoleState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
