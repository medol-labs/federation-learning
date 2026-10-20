package tech.medo.federationmanagement.federationmembership

data class FederationMembershipFederationIdOrganizationIdSelection(
    val normalizedFederationId: String,
    val normalizedOrganizationId: String
)

object FederationMembershipFederationIdOrganizationIdReservationTags {
    const val FEDERATION_ID = "federationId"
    const val ORGANIZATION_ID = "organizationId"
}
