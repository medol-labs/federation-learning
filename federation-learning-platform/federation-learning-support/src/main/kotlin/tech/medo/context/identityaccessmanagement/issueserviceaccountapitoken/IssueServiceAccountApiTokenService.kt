package tech.medo.identityaccessmanagement.issueserviceaccountapitoken

import java.util.UUID;

interface IssueServiceAccountApiTokenService {
    fun supports(input: IssueServiceAccountApiTokenInput): Boolean = true
    fun execute(input: IssueServiceAccountApiTokenInput): IssueServiceAccountApiTokenResult
}

data class IssueServiceAccountApiTokenInput(
    val apiTokenId: UUID,
    val userAccountId: UUID,
    val tokenName: String
)

sealed interface IssueServiceAccountApiTokenResult {
    data class Succeeded(
        val username: String,
        val tokenPrefix: String,
        val tokenDigest: String,
        val issuedAt: String,
        val roles: List<String>,
        val permissions: List<String>,
        val apiToken: String
    ) : IssueServiceAccountApiTokenResult


}
