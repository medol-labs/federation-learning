package tech.medo.organizationmanagement.userorganizationmembership

data class UserOrganizationMembershipUserAccountIdOrganizationIdSelection(
    val normalizedUserAccountId: String,
    val normalizedOrganizationId: String
)

object UserOrganizationMembershipUserAccountIdOrganizationIdReservationTags {
    const val USER_ACCOUNT_ID = "userAccountId"
    const val ORGANIZATION_ID = "organizationId"
}
