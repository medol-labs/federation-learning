package tech.medo.identityaccessmanagement.grantpermissiontorole

import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleCommand

import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.events.PermissionRevokedFromRoleEvent
import tech.medo.identityaccessmanagement.role.RoleState





interface GrantPermissionToRoleDecision {
    fun decide(command: GrantPermissionToRoleCommand, state: RoleState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            PermissionGrantedToRoleEvent(roleId = command.roleId, roleCode = command.roleCode, permissionCode = requireNotNull(state.permissionCode) { "permissionCode is required from state." }, roleCodeEventTag = "" /* TODO: derive value */)
        )
    }
}
