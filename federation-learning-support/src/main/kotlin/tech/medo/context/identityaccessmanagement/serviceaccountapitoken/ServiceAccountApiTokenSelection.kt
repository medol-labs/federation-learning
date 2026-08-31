package tech.medo.identityaccessmanagement.serviceaccountapitoken

import java.util.UUID;


data class ServiceAccountApiTokenSelection(
    val userAccountId: UUID
)

object ServiceAccountApiTokenTags {
    const val USER_ACCOUNT_ID = "userAccountId"
}

object ServiceAccountApiTokenMetadata {
    val concepts = listOf("ServiceAccountApiToken")
}
