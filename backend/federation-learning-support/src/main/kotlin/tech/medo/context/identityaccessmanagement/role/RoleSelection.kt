package tech.medo.identityaccessmanagement.role



data class RoleSelection(
    val roleCode: String
)

object RoleTags {
    const val ROLE_CODE = "roleCode"
}

object RoleMetadata {
    val concepts = listOf("Role")
}
