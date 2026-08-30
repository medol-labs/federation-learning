package tech.medo.identityaccessmanagement.registerrole

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.registerrole.RegisterRoleCommand
import tech.medo.identityaccessmanagement.events.RoleRegisteredEvent

class RegisterRoleDecisionTest {
    @Test
    fun RegisterRoleEmitsRoleRegisteredEvent() {
        val events = (object : RegisterRoleDecision {}).decide(
            RegisterRoleCommand(
            roleCode = "",
            roleName = ""
            )
        )

        assertTrue(events.any { it is RoleRegisteredEvent })
    }
}
