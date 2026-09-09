package tech.medo.organizationmanagement.organization



data class OrganizationSelection(
    val organizationName: String
)

object OrganizationTags {
    const val ORGANIZATION_NAME = "organizationName"
}

object OrganizationMetadata {
    val concepts = listOf("Organization")
}
