package tech.medo.identityaccessmanagement.registerrole

import tech.medo.identityaccessmanagement.registerrole.RegisterRoleCommand

import tech.medo.identityaccessmanagement.events.RoleRegisteredEvent
import tech.medo.identityaccessmanagement.role.RoleState





interface RegisterRoleDecision {
    fun decide(command: RegisterRoleCommand, state: RoleState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            RoleRegisteredEvent(roleCode = command.roleCode, roleName = command.roleName)
        )
    }
}
