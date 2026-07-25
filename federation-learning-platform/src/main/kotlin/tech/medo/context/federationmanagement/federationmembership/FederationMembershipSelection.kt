package tech.medo.federationmanagement.federationmembership

import java.util.UUID;


data class FederationMembershipSelection(
    val federationId: UUID,
    val organizationId: UUID
)

object FederationMembershipTags {
    const val FEDERATION_ID = "federationId"
    const val ORGANIZATION_ID = "organizationId"
}

object FederationMembershipMetadata {
    val concepts = listOf("FederationMembership")
}
