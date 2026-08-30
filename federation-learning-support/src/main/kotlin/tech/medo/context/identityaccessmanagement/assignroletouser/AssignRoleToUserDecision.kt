package tech.medo.identityaccessmanagement.assignroletouser

import tech.medo.identityaccessmanagement.assignroletouser.AssignRoleToUserCommand

import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState





interface AssignRoleToUserDecision {
    fun decide(command: AssignRoleToUserCommand, state: UserAccountState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            RoleAssignedToUserEvent(userAccountId = command.userAccountId, roleCodes = command.roleCodes)
        )
    }
}
