package tech.medo.identityaccessmanagement.useraccount

import java.util.UUID;


data class UserAccountSelection(
    val userAccountId: UUID
)

object UserAccountTags {
    const val USER_ACCOUNT_ID = "userAccountId"
}

object UserAccountMetadata {
    val concepts = listOf("UserAccount")
}
