package tech.medo.organizationmanagement.organization

import java.util.UUID;


data class OrganizationSelection(
    val organizationId: UUID
)

object OrganizationTags {
    const val ORGANIZATION_ID = "organizationId"
}

object OrganizationMetadata {
    val concepts = listOf("Organization")
}
