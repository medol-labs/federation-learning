package tech.medo.organizationmanagement.userorganizationmembership

import java.util.UUID;


data class UserOrganizationMembershipSelection(
    val userOrganizationMembershipId: UUID
)

object UserOrganizationMembershipTags {
    const val USER_ORGANIZATION_MEMBERSHIP_ID = "userOrganizationMembershipId"
}

object UserOrganizationMembershipMetadata {
    val concepts = listOf("UserOrganizationMembership")
}
