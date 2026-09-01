package tech.medo.identityaccessmanagement.registeruseraccount

import tech.medo.identityaccessmanagement.registeruseraccount.RegisterUserAccountCommand

import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState





interface RegisterUserAccountDecision {
    fun decide(command: RegisterUserAccountCommand): List<Any> {
        return listOf(
            UserAccountRegisteredEvent(userAccountId = command.userAccountId, username = command.username, providerSubject = command.providerSubject, userSource = command.userSource, passwordHash = command.passwordHash)
        )
    }
}
