package tech.medo.identityaccessmanagement.deactivateuseraccount

import tech.medo.identityaccessmanagement.deactivateuseraccount.DeactivateUserAccountCommand


import tech.medo.identityaccessmanagement.events.UserAccountDeactivatedEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState





interface DeactivateUserAccountDecision {
    fun decide(command: DeactivateUserAccountCommand, state: UserAccountState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            UserAccountDeactivatedEvent(userAccountId = command.userAccountId, reason = command.reason)
        )
    }
}
