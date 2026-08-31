package tech.medo.organizationmanagement.binduseraccounttoorganization

import tech.medo.organizationmanagement.binduseraccounttoorganization.BindUserAccountToOrganizationCommand

import tech.medo.organizationmanagement.events.UserAccountBoundToOrganizationEvent
import tech.medo.organizationmanagement.events.UserOrganizationMembershipUserAccountIdOrganizationIdReservedEvent
import tech.medo.organizationmanagement.userorganizationmembership.UserOrganizationMembershipState

import tech.medo.organizationmanagement.userorganizationmembership.UserOrganizationMembershipUserAccountIdOrganizationIdReservationState



interface BindUserAccountToOrganizationDecision {
    fun decide(command: BindUserAccountToOrganizationCommand, userOrganizationMembershipUserAccountIdOrganizationIdReservation: UserOrganizationMembershipUserAccountIdOrganizationIdReservationState): List<Any> {
        require(!userOrganizationMembershipUserAccountIdOrganizationIdReservation.reserved) {
            "UserAccountId OrganizationId already exists."
        }
        return listOf(
                        UserOrganizationMembershipUserAccountIdOrganizationIdReservedEvent(userOrganizationMembershipId = command.userOrganizationMembershipId, userAccountId = command.userAccountId, organizationId = command.organizationId, normalizedUserAccountId = command.userAccountId.toString().trim().lowercase(), normalizedOrganizationId = command.organizationId.toString().trim().lowercase()),
            UserAccountBoundToOrganizationEvent(userOrganizationMembershipId = command.userOrganizationMembershipId, userAccountId = command.userAccountId, organizationId = command.organizationId, organizationUserRole = command.organizationUserRole)
        )
    }
}
