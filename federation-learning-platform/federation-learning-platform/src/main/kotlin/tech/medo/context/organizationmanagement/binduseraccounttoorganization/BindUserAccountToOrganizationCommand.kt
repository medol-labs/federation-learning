package tech.medo.organizationmanagement.binduseraccounttoorganization

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.organizationmanagement.userorganizationmembership.UserOrganizationMembershipSelection
import java.util.UUID;

import tech.medo.organizationmanagement.userorganizationmembership.UserOrganizationMembershipUserAccountIdOrganizationIdSelection

@Command
data class BindUserAccountToOrganizationCommand(
    val userOrganizationMembershipId: UUID = java.util.UUID.randomUUID(),
    val userAccountId: UUID,
    val username: String?,
    val organizationId: UUID,
    val organizationName: String?,
    val organizationUserRole: String?
) {
    @TargetEntityId
    val selection: UserOrganizationMembershipSelection = UserOrganizationMembershipSelection(userOrganizationMembershipId = userOrganizationMembershipId)

    val userOrganizationMembershipUserAccountIdOrganizationIdSelection: UserOrganizationMembershipUserAccountIdOrganizationIdSelection = UserOrganizationMembershipUserAccountIdOrganizationIdSelection(normalizedUserAccountId = userAccountId.toString().trim().lowercase(), normalizedOrganizationId = organizationId.toString().trim().lowercase())
}
