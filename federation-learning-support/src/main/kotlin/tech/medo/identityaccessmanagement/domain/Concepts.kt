package tech.medo.identityaccessmanagement.domain

object Concepts {
    data object UserAccount {
        const val NAME = "UserAccount"
        val slices = listOf("RegisterUserAccount", "GenerateUserAccountLoginPassword", "DeactivateUserAccount", "AssignRoleToUser", "IdentityAccessCatalogs")
        val states = listOf("Active", "Deactivated")
    }

    data object Role {
        const val NAME = "Role"
        val slices = listOf("RegisterRole", "GrantPermissionToRole")
        val states = listOf("Registered")
    }

    data object Permission {
        const val NAME = "Permission"
        val slices = listOf("RegisterPermission")
        val states = listOf("Registered")
    }
}
