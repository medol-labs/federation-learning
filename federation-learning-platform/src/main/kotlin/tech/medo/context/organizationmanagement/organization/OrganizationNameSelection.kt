package tech.medo.organizationmanagement.organization

data class OrganizationNameSelection(
    val normalizedName: String
)

object OrganizationNameReservationTags {
    const val ORGANIZATION_NAME = "organizationName"
}
