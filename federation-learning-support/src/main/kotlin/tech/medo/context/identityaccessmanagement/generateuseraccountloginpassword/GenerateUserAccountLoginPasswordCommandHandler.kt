package tech.medo.identityaccessmanagement.generateuseraccountloginpassword

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordCommand
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordInput
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordService
import tech.medo.identityaccessmanagement.useraccount.UserAccountState




@Component
class GenerateUserAccountLoginPasswordCommandHandler(
    private val decision: GenerateUserAccountLoginPasswordDecision,
    private val generateUserAccountLoginPasswordService: GenerateUserAccountLoginPasswordService
) {
    @CommandHandler
    fun handle(
        command: GenerateUserAccountLoginPasswordCommand,
        @InjectEntity(idProperty = "userAccountId") state: UserAccountState,
        eventAppender: EventAppender
    ): GenerateUserAccountLoginPasswordResult {
        val input = GenerateUserAccountLoginPasswordInput(userAccountId = command.userAccountId, passwordResetRequired = command.passwordResetRequired)
        val portResult = generateUserAccountLoginPasswordService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
        return portResult
    }
}
