package tech.medo.federationmanagement.federation

import java.util.UUID;


data class FederationSelection(
    val federationId: UUID
)

object FederationTags {
    const val FEDERATION_ID = "federationId"
}

object FederationMetadata {
    val concepts = listOf("Federation")
}
