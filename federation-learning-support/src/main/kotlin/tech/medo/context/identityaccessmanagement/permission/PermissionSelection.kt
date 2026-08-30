package tech.medo.identityaccessmanagement.permission



data class PermissionSelection(
    val permissionCode: String
)

object PermissionTags {
    const val PERMISSION_CODE = "permissionCode"
}

object PermissionMetadata {
    val concepts = listOf("Permission")
}
