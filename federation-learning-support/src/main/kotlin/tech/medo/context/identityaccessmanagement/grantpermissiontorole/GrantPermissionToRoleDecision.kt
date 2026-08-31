package tech.medo.identityaccessmanagement.grantpermissiontorole

import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleCommand

import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.events.PermissionRevokedFromRoleEvent
import tech.medo.identityaccessmanagement.role.RoleState





interface GrantPermissionToRoleDecision {
    fun decide(command: GrantPermissionToRoleCommand, state: RoleState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return command.permissionCodes.map { permissionCode ->
                PermissionGrantedToRoleEvent(roleId = command.roleId, roleCode = command.roleCode, permissionCode = permissionCode)
            }
    }
}
