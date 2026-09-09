package tech.medo.identityaccessmanagement.registerpermission

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.registerpermission.RegisterPermissionCommand
import tech.medo.identityaccessmanagement.events.PermissionRegisteredEvent
import java.util.UUID

class RegisterPermissionDecisionTest {
    @Test
    fun RegisterPermissionEmitsPermissionRegisteredEvent() {
        val events = (object : RegisterPermissionDecision {}).decide(
            RegisterPermissionCommand(
            permissionId = java.util.UUID.randomUUID(),
            permissionCode = "",
            permissionName = "",
            description = null
            )
        )

        assertTrue(events.any { it is PermissionRegisteredEvent })
    }
}
