package tech.medo.identityaccessmanagement.useraccount

data class UserAccountUsernameSelection(
    val normalizedName: String
)

object UserAccountUsernameReservationTags {
    const val USERNAME = "username"
}
