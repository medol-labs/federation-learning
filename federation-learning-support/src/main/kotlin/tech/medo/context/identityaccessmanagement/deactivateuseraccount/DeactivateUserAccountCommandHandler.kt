package tech.medo.identityaccessmanagement.deactivateuseraccount

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.deactivateuseraccount.DeactivateUserAccountCommand

import tech.medo.identityaccessmanagement.useraccount.UserAccountState




@Component
class DeactivateUserAccountCommandHandler(
    private val decision: DeactivateUserAccountDecision
) {
    @CommandHandler
    fun handle(
        command: DeactivateUserAccountCommand,
        @InjectEntity(idProperty = "userAccountId") state: UserAccountState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
