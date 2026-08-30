package tech.medo.identityaccessmanagement.grantpermissiontorole

import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleCommand

import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.role.RoleState





interface GrantPermissionToRoleDecision {
    fun decide(command: GrantPermissionToRoleCommand, state: RoleState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            PermissionGrantedToRoleEvent(roleId = command.roleId, roleCode = command.roleCode, permissionCodes = command.permissionCodes, roleCodeEventTag = "" /* TODO: derive value */)
        )
    }
}
