package tech.medo.federationmanagement.federation

data class FederationNameSelection(
    val normalizedName: String
)

object FederationNameReservationTags {
    const val FEDERATION_NAME = "federationName"
}
