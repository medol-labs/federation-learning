package tech.medo.identityaccessmanagement.generateuseraccountloginpassword

import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordCommand

import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordResult
import tech.medo.identityaccessmanagement.events.UserAccountLoginPasswordGeneratedEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState





interface GenerateUserAccountLoginPasswordDecision {
    fun decide(command: GenerateUserAccountLoginPasswordCommand, state: UserAccountState, portResult: GenerateUserAccountLoginPasswordResult): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return when (portResult) {
                    is GenerateUserAccountLoginPasswordResult.Succeeded -> listOf(UserAccountLoginPasswordGeneratedEvent(userAccountId = command.userAccountId, passwordHash = portResult.passwordHash, passwordResetRequired = command.passwordResetRequired))
                }
    }
}
