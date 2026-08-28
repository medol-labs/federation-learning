package tech.medo.identityaccessmanagement.registeruseraccount

import tech.medo.identityaccessmanagement.registeruseraccount.RegisterUserAccountCommand

import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState





interface RegisterUserAccountDecision {
    fun decide(command: RegisterUserAccountCommand, state: UserAccountState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            UserAccountRegisteredEvent(userAccountId = command.userAccountId, username = command.username, providerSubject = command.providerSubject, organizationId = command.organizationId)
        )
    }
}
