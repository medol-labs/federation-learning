package tech.medo.identityaccessmanagement.assignroletouser

import tech.medo.identityaccessmanagement.assignroletouser.AssignRoleToUserCommand


import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent
import tech.medo.identityaccessmanagement.events.RoleUnassignedFromUserEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState





interface AssignRoleToUserDecision {
    fun decide(command: AssignRoleToUserCommand, state: UserAccountState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return command.roleCodes.map { roleCode ->
                RoleAssignedToUserEvent(userAccountId = command.userAccountId, roleCode = roleCode)
            }
    }
}
