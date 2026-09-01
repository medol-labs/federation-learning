package tech.medo.identityaccessmanagement.registeruseraccount

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.registeruseraccount.RegisterUserAccountCommand
import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import java.util.UUID

class RegisterUserAccountDecisionTest {
    @Test
    fun RegisterUserAccountEmitsUserAccountRegisteredEvent() {
        val events = (object : RegisterUserAccountDecision {}).decide(
            RegisterUserAccountCommand(
            userAccountId = java.util.UUID.randomUUID(),
            username = "",
            providerSubject = null,
            userSource = null,
            passwordHash = null
            )
        )

        assertTrue(events.any { it is UserAccountRegisteredEvent })
    }
}
