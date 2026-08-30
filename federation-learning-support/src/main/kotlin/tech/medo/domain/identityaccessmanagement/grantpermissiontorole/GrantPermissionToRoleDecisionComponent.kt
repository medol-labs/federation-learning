package tech.medo.domain.identityaccessmanagement.grantpermissiontorole

import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.events.PermissionRevokedFromRoleEvent
import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleCommand
import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleDecision
import tech.medo.identityaccessmanagement.role.RoleState
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelRepository

@Component
class GrantPermissionToRoleDecisionComponent(
    private val rolePermissionGrants: RolePermissionGrantCatalogReadModelRepository,
) : GrantPermissionToRoleDecision {
    override fun decide(command: GrantPermissionToRoleCommand, state: RoleState): List<Any> {
        require(command.permissionCodes.isNotEmpty()) { "At least one permission is required." }
        val requestedPermissionCodes = command.permissionCodes
            .map { permissionCode -> permissionCode.trim() }
            .filter { permissionCode -> permissionCode.isNotBlank() }
        require(requestedPermissionCodes.size == command.permissionCodes.size) { "Permission code cannot be blank." }
        require(requestedPermissionCodes.distinctBy { permissionCode -> permissionCode.lowercase() }.size == requestedPermissionCodes.size) {
            "Permission code cannot be duplicated in the same request."
        }

        val existingPermissionCodes = rolePermissionGrants.findProjectionsByRoleCode(command.roleCode)
            .mapNotNull { grant -> grant.permissionCode?.trim() }
            .filter { permissionCode -> permissionCode.isNotBlank() }

        val requestedPermissionCodesByKey = requestedPermissionCodes.associateBy { permissionCode -> permissionCode.lowercase() }
        val existingPermissionCodesByKey = existingPermissionCodes.associateBy { permissionCode -> permissionCode.lowercase() }

        val revokedEvents = existingPermissionCodesByKey
            .filterKeys { permissionCode -> !requestedPermissionCodesByKey.containsKey(permissionCode) }
            .values
            .map { permissionCode ->
                PermissionRevokedFromRoleEvent(
                    roleId = command.roleId,
                    roleCode = command.roleCode,
                    permissionCode = permissionCode,
                )
            }

        val grantedEvents = requestedPermissionCodesByKey
            .filterKeys { permissionCode -> !existingPermissionCodesByKey.containsKey(permissionCode) }
            .values
            .map { permissionCode ->
                PermissionGrantedToRoleEvent(
                    roleId = command.roleId,
                    roleCode = command.roleCode,
                    permissionCode = permissionCode,
                )
            }

        return revokedEvents + grantedEvents
    }
}
