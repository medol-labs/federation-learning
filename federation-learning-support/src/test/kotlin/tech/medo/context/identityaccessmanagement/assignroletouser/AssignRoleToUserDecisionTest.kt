package tech.medo.identityaccessmanagement.assignroletouser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.assignroletouser.AssignRoleToUserCommand
import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent
import tech.medo.identityaccessmanagement.useraccount.UserAccountState
import java.util.UUID

class AssignRoleToUserDecisionTest {
    @Test
    fun AssignRoleToUserEmitsRoleAssignedToUserEvent() {
        val events = (object : AssignRoleToUserDecision {}).decide(
            AssignRoleToUserCommand(
            userAccountId = java.util.UUID.randomUUID(),
            roleCodes = listOf("ADMIN")
            ),
            UserAccountState()
        )

        assertTrue(events.any { it is RoleAssignedToUserEvent })
    }
}
