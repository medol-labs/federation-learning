package tech.medo.identityaccessmanagement.issueserviceaccountapitoken

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenCommand
import tech.medo.identityaccessmanagement.events.UserAccountRegisteredEvent
import tech.medo.identityaccessmanagement.events.ServiceAccountApiTokenIssuedEvent
import tech.medo.identityaccessmanagement.events.RoleAssignedToUserEvent
import tech.medo.identityaccessmanagement.events.PermissionGrantedToRoleEvent
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenResult
import java.util.UUID

class IssueServiceAccountApiTokenDecisionTest {
    @Test
    fun SuperAdministratorIssuesTokenForServiceAccount() {


        val command = IssueServiceAccountApiTokenCommand(
            apiTokenId = java.util.UUID.randomUUID(),
            userAccountId = UUID.nameUUIDFromBytes("00000000-0000-0000-0000-000000000001".toByteArray()),
            tokenName = "dictionary init"
        )

        val events = (object : IssueServiceAccountApiTokenDecision {}).decide(
            command,
            portResult = IssueServiceAccountApiTokenResult.Succeeded(
                username = "",
                tokenPrefix = "",
                tokenDigest = "",
                issuedAt = "",
                roles = emptyList(),
                permissions = emptyList(),
                apiToken = ""
            )
        )

        val event = events.filterIsInstance<ServiceAccountApiTokenIssuedEvent>().single()
        assertEquals(command.apiTokenId, event.apiTokenId)
        assertEquals(UUID.nameUUIDFromBytes("00000000-0000-0000-0000-000000000001".toByteArray()), event.userAccountId)
        assertEquals("dictionary init", event.tokenName)
    }

    @Test
    fun IssuedEventStoresDigestInsteadOfPlaintextToken() {


        val command = IssueServiceAccountApiTokenCommand(
            apiTokenId = java.util.UUID.randomUUID(),
            userAccountId = java.util.UUID.randomUUID(),
            tokenName = "offline integration"
        )

        val events = (object : IssueServiceAccountApiTokenDecision {}).decide(
            command,
            portResult = IssueServiceAccountApiTokenResult.Succeeded(
                username = "",
                tokenPrefix = "",
                tokenDigest = "",
                issuedAt = "",
                roles = emptyList(),
                permissions = emptyList(),
                apiToken = ""
            )
        )

        val event = events.filterIsInstance<ServiceAccountApiTokenIssuedEvent>().single()
        assertEquals(command.apiTokenId, event.apiTokenId)
        assertEquals(command.userAccountId, event.userAccountId)
        assertEquals("offline integration", event.tokenName)
    }

    @Test
    fun IssuedTokenCapturesCurrentPermissions() {


        val command = IssueServiceAccountApiTokenCommand(
            apiTokenId = java.util.UUID.randomUUID(),
            userAccountId = java.util.UUID.randomUUID(),
            tokenName = "dictionary initializer"
        )

        val events = (object : IssueServiceAccountApiTokenDecision {}).decide(
            command,
            portResult = IssueServiceAccountApiTokenResult.Succeeded(
                username = "",
                tokenPrefix = "",
                tokenDigest = "",
                issuedAt = "",
                roles = emptyList(),
                permissions = emptyList(),
                apiToken = ""
            )
        )

        val event = events.filterIsInstance<ServiceAccountApiTokenIssuedEvent>().single()
        assertEquals(command.apiTokenId, event.apiTokenId)
        assertEquals(command.userAccountId, event.userAccountId)
        assertEquals("dictionary initializer", event.tokenName)
    }
}
