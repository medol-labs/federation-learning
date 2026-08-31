package tech.medo.identityaccessmanagement.registerrole

import tech.medo.identityaccessmanagement.registerrole.RegisterRoleCommand

import tech.medo.identityaccessmanagement.events.RoleRegisteredEvent
import tech.medo.identityaccessmanagement.role.RoleState





interface RegisterRoleDecision {
    fun decide(command: RegisterRoleCommand): List<Any> {
        return listOf(
            RoleRegisteredEvent(roleId = command.roleId, roleCode = command.roleCode, roleName = command.roleName)
        )
    }
}
