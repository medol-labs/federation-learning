package tech.medo.identityaccessmanagement.assignroletouser

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.assignroletouser.AssignRoleToUserCommand

import tech.medo.identityaccessmanagement.useraccount.UserAccountState




@Component
class AssignRoleToUserCommandHandler(
    private val decision: AssignRoleToUserDecision
) {
    @CommandHandler
    fun handle(
        command: AssignRoleToUserCommand,
        @InjectEntity(idProperty = "userAccountId") state: UserAccountState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
