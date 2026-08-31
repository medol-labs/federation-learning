package tech.medo.identityaccessmanagement.registerpermission

import tech.medo.identityaccessmanagement.registerpermission.RegisterPermissionCommand

import tech.medo.identityaccessmanagement.events.PermissionRegisteredEvent
import tech.medo.identityaccessmanagement.permission.PermissionState





interface RegisterPermissionDecision {
    fun decide(command: RegisterPermissionCommand): List<Any> {
        return listOf(
            PermissionRegisteredEvent(permissionId = command.permissionId, permissionCode = command.permissionCode, permissionName = command.permissionName, description = command.description)
        )
    }
}
