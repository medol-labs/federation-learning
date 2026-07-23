package tech.medo.organizationmanagement.domain

object Concepts {
    data object Organization {
        const val NAME = "Organization"
        val slices = listOf("RegisterOrganization", "ActivateOrganization", "DeactivateOrganization", "ReactivateOrganization", "OrganizationDirectory")
        val states = listOf("Registered", "Active", "Deactivated")
    }
}
