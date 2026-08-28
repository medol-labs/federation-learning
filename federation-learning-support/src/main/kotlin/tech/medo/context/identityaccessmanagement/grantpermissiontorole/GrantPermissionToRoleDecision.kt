package tech.medo.identityaccessmanagement.grantpermissiontorole

import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleCommand

import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.role.RoleState





interface GrantPermissionToRoleDecision {
    fun decide(command: GrantPermissionToRoleCommand, state: RoleState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            PermissionGrantedToRoleEvent(roleCode = command.roleCode, permissionCode = command.permissionCode)
        )
    }
}
