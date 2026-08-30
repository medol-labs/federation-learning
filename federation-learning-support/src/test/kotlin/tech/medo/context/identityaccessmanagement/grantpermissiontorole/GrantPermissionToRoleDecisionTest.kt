package tech.medo.identityaccessmanagement.grantpermissiontorole

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.grantpermissiontorole.GrantPermissionToRoleCommand
import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.role.RoleState
import java.util.UUID

class GrantPermissionToRoleDecisionTest {
    @Test
    fun GrantPermissionToRoleEmitsPermissionGrantedToRoleEvent() {
        val events = (object : GrantPermissionToRoleDecision {}).decide(
            GrantPermissionToRoleCommand(
            roleId = java.util.UUID.randomUUID(),
            roleCode = "admin",
            permissionCodes = listOf("*:*")
            ),
            RoleState()
        )

        assertTrue(events.any { it is PermissionGrantedToRoleEvent })
    }
}
