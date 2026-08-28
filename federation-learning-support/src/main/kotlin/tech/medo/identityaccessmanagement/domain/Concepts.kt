package tech.medo.identityaccessmanagement.domain

object Concepts {
    data object UserAccount {
        const val NAME = "UserAccount"
        val slices = listOf("RegisterUserAccount", "DeactivateUserAccount")
        val states = listOf("Active", "Deactivated")
    }

    data object Role {
        const val NAME = "Role"
        val slices = listOf("RegisterRole", "GrantPermissionToRole")
        val states = listOf("Registered")
    }
}
