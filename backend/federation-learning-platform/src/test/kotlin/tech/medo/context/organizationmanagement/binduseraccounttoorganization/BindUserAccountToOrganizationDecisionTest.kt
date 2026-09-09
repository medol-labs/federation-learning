package tech.medo.organizationmanagement.binduseraccounttoorganization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.organizationmanagement.binduseraccounttoorganization.BindUserAccountToOrganizationCommand
import tech.medo.organizationmanagement.userorganizationmembership.UserOrganizationMembershipUserAccountIdOrganizationIdReservationState
import tech.medo.organizationmanagement.events.UserOrganizationMembershipUserAccountIdOrganizationIdReservedEvent
import java.util.UUID

class BindUserAccountToOrganizationDecisionTest {
    @Test
    fun RejectDuplicateUserOrganizationBinding() {
        val userOrganizationMembershipUserAccountIdOrganizationIdReservation = UserOrganizationMembershipUserAccountIdOrganizationIdReservationState()
        userOrganizationMembershipUserAccountIdOrganizationIdReservation.evolve(
            UserOrganizationMembershipUserAccountIdOrganizationIdReservedEvent(
                userOrganizationMembershipId = java.util.UUID.randomUUID(),
                userAccountId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
                organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                normalizedUserAccountId = UUID.fromString("11111111-1111-4111-8111-111111111111").toString().trim().lowercase(),
                normalizedOrganizationId = UUID.fromString("22222222-2222-4222-8222-222222222222").toString().trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : BindUserAccountToOrganizationDecision {}).decide(
                        BindUserAccountToOrganizationCommand(
                        userOrganizationMembershipId = java.util.UUID.randomUUID(),
                        userAccountId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
                        organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
                        organizationUserRole = null
                        ),
                            userOrganizationMembershipUserAccountIdOrganizationIdReservation = userOrganizationMembershipUserAccountIdOrganizationIdReservation
                    )
        }
    }
}
