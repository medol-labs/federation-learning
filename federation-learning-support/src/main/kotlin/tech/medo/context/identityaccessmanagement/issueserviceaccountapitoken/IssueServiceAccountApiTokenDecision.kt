package tech.medo.identityaccessmanagement.issueserviceaccountapitoken

import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenCommand
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenResult
import tech.medo.identityaccessmanagement.events.ServiceAccountApiTokenIssuedEvent
import tech.medo.identityaccessmanagement.serviceaccountapitoken.ServiceAccountApiTokenState





interface IssueServiceAccountApiTokenDecision {
    fun decide(command: IssueServiceAccountApiTokenCommand, portResult: IssueServiceAccountApiTokenResult): List<Any> {
        return when (portResult) {
                    is IssueServiceAccountApiTokenResult.Succeeded -> listOf(ServiceAccountApiTokenIssuedEvent(apiTokenId = command.apiTokenId, userAccountId = command.userAccountId, username = portResult.username, tokenName = command.tokenName, tokenPrefix = portResult.tokenPrefix, tokenDigest = portResult.tokenDigest, issuedAt = portResult.issuedAt, roles = portResult.roles, permissions = portResult.permissions))
                }
    }
}
