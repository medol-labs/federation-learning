package tech.medo.identityaccessmanagement.registeruseraccount

import tech.medo.identityaccessmanagement.registeruseraccount.RegisterUserAccountCommand

import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import tech.medo.identityaccessmanagement.events.UserAccountUsernameReservedEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState

import tech.medo.identityaccessmanagement.useraccount.UserAccountUsernameReservationState



interface RegisterUserAccountDecision {
    fun decide(command: RegisterUserAccountCommand, userAccountUsernameReservation: UserAccountUsernameReservationState): List<Any> {
        require(!userAccountUsernameReservation.reserved) {
            "Username already exists."
        }
        return listOf(
            UserAccountUsernameReservedEvent(userAccountId = command.userAccountId, username = command.username, normalizedName = command.username.trim().lowercase()),
            UserAccountRegisteredEvent(userAccountId = command.userAccountId, username = command.username, providerSubject = command.providerSubject, passwordHash = command.passwordHash, organizationId = command.organizationId)
        )
    }
}
