package tech.medo.identityaccessmanagement.registerpermission

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.registerpermission.RegisterPermissionCommand
import tech.medo.identityaccessmanagement.events.PermissionRegisteredEvent

class RegisterPermissionDecisionTest {
    @Test
    fun RegisterPermissionEmitsPermissionRegisteredEvent() {
        val events = (object : RegisterPermissionDecision {}).decide(
            RegisterPermissionCommand(
            permissionCode = "",
            permissionName = "",
            description = null
            )
        )

        assertTrue(events.any { it is PermissionRegisteredEvent })
    }
}
