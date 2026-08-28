package tech.medo.identityaccessmanagement.registeruseraccount

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.registeruseraccount.RegisterUserAccountCommand

import tech.medo.identityaccessmanagement.useraccount.UserAccountState




@Component
class RegisterUserAccountCommandHandler(
    private val decision: RegisterUserAccountDecision
) {
    @CommandHandler
    fun handle(
        command: RegisterUserAccountCommand,
        @InjectEntity(idProperty = "userAccountId") state: UserAccountState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
