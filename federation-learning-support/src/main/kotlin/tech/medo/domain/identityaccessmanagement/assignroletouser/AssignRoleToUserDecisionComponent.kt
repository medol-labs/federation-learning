package tech.medo.domain.identityaccessmanagement.assignroletouser

import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.assignroletouser.AssignRoleToUserCommand
import tech.medo.identityaccessmanagement.assignroletouser.AssignRoleToUserDecision
import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent
import tech.medo.identityaccessmanagement.events.RoleUnassignedFromUserEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelRepository

@Component
class AssignRoleToUserDecisionComponent(
    private val userRoleAssignments: UserRoleAssignmentCatalogReadModelRepository,
) : AssignRoleToUserDecision {
    override fun decide(command: AssignRoleToUserCommand, state: UserAccountState): List<Any> {
        require(command.roleCodes.isNotEmpty()) { "At least one role is required." }
        val requestedRoleCodes = command.roleCodes
            .map { roleCode -> roleCode.trim() }
            .filter { roleCode -> roleCode.isNotBlank() }
        require(requestedRoleCodes.size == command.roleCodes.size) { "Role code cannot be blank." }
        require(requestedRoleCodes.distinctBy { roleCode -> roleCode.lowercase() }.size == requestedRoleCodes.size) {
            "Role code cannot be duplicated in the same request."
        }

        val existingRoleCodes = userRoleAssignments.findProjectionsByUserAccountId(command.userAccountId)
            .mapNotNull { assignment -> assignment.roleCode?.trim() }
            .filter { roleCode -> roleCode.isNotBlank() }

        val requestedRoleCodesByKey = requestedRoleCodes.associateBy { roleCode -> roleCode.lowercase() }
        val existingRoleCodesByKey = existingRoleCodes.associateBy { roleCode -> roleCode.lowercase() }

        val unassignedEvents = existingRoleCodesByKey
            .filterKeys { roleCode -> !requestedRoleCodesByKey.containsKey(roleCode) }
            .values
            .map { roleCode ->
                RoleUnassignedFromUserEvent(
                    userAccountId = command.userAccountId,
                    roleCode = roleCode,
                )
            }

        val assignedEvents = requestedRoleCodesByKey
            .filterKeys { roleCode -> !existingRoleCodesByKey.containsKey(roleCode) }
            .values
            .map { roleCode ->
                RoleAssignedToUserEvent(
                    userAccountId = command.userAccountId,
                    roleCode = roleCode,
                )
            }

        return unassignedEvents + assignedEvents
    }
}
