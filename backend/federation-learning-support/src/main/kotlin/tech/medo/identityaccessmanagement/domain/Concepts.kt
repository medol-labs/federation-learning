package tech.medo.identityaccessmanagement.domain

object Concepts {
    data object UserAccount {
        const val NAME = "UserAccount"
        val slices = listOf("RegisterUserAccount", "GenerateUserAccountLoginPassword", "DeactivateUserAccount", "AssignRoleToUser", "UserAccountCatalogs")
        val states = listOf("Active", "Deactivated")
    }

    data object Role {
        const val NAME = "Role"
        val slices = listOf("RegisterRole", "GrantPermissionToRole", "RoleCatalogs")
        val states = listOf("Registered")
    }

    data object Permission {
        const val NAME = "Permission"
        val slices = listOf("RegisterPermission", "PermissionCatalogs")
        val states = listOf("Registered")
    }

    data object UserRoleAssignment {
        const val NAME = "UserRoleAssignment"
        val slices = listOf("UserRoleAssignmentCatalog")
        val states = emptyList<String>()
    }

    data object RolePermissionGrant {
        const val NAME = "RolePermissionGrant"
        val slices = listOf("RolePermissionGrantCatalog")
        val states = emptyList<String>()
    }

    data object ServiceAccountApiToken {
        const val NAME = "ServiceAccountApiToken"
        val slices = listOf("IssueServiceAccountApiToken", "ServiceAccountApiTokenCatalogs")
        val states = listOf("Issued")
    }
}
