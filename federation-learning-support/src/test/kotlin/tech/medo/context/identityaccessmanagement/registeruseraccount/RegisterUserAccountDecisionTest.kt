package tech.medo.identityaccessmanagement.registeruseraccount

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.registeruseraccount.RegisterUserAccountCommand
import tech.medo.identityaccessmanagement.useraccount.UserAccountUsernameReservationState
import tech.medo.identityaccessmanagement.events.UserAccountUsernameReservedEvent
import java.util.UUID

class RegisterUserAccountDecisionTest {
    @Test
    fun RejectDuplicateUsername() {
        val userAccountUsernameReservation = UserAccountUsernameReservationState()
        userAccountUsernameReservation.evolve(
            UserAccountUsernameReservedEvent(
                userAccountId = java.util.UUID.randomUUID(),
                username = "admin",
                normalizedName = "admin".trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : RegisterUserAccountDecision {}).decide(
                        RegisterUserAccountCommand(
                        userAccountId = java.util.UUID.randomUUID(),
                        username = "admin",
                        providerSubject = null,
                        passwordHash = null,
                        organizationId = null
                        ),
                            userAccountUsernameReservation = userAccountUsernameReservation
                    )
        }
    }
}
