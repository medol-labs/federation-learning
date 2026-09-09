package tech.medo.federationmanagement.federation



data class FederationSelection(
    val federationName: String
)

object FederationTags {
    const val FEDERATION_NAME = "federationName"
}

object FederationMetadata {
    val concepts = listOf("Federation")
}
