package tech.medo.infrastructure.secondary.identityaccessmanagement.serviceaccountapitoken.issueserviceaccountapitoken

import java.util.UUID
import javax.crypto.spec.SecretKeySpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModel
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelKey
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelProjection
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelRepository
import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModel
import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModelProjection
import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModelRepository
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModel
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelKey
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelProjection
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelRepository
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenInput
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenResult
import tech.medo.shared.security.MedolSecurityProperties

class LocalIssueServiceAccountApiTokenAdapterTest {
    @Test
    fun issuesLongLivedJwtForServiceAccount() {
        val userAccountId = UUID.randomUUID()
        val properties = MedolSecurityProperties(jwtSecret = "test-secret-test-secret-test-secret")
        val adapter = LocalIssueServiceAccountApiTokenAdapter(
            properties = properties,
            userAccounts = FakeUserAccounts(
                UserAccountCatalogReadModel(
                    userAccountId = userAccountId,
                    username = "dictionary-sync-service",
                    providerSubject = userAccountId.toString(),
                    passwordHash = null,
                    active = true,
                    userId = null,
                    sessionId = null,
                    correlationId = null,
                    causationId = null,
                    traceId = null,
                    tenantId = null,
                ),
            ),
            userRoleAssignments = FakeUserRoleAssignments(
                listOf(
                    role(userAccountId, "SERVICE_ACCOUNT"),
                    role(userAccountId, "DictionaryInitializer"),
                ),
            ),
            rolePermissionGrants = FakeRolePermissionGrants(
                listOf(permission("DictionaryInitializer", "register_dictionary:execute")),
            ),
        )

        val result = adapter.execute(
            IssueServiceAccountApiTokenInput(
                apiTokenId = UUID.randomUUID(),
                userAccountId = userAccountId,
                tokenName = "dictionary init",
            ),
        ) as IssueServiceAccountApiTokenResult.Succeeded
        val jwt = decoder(properties).decode(result.apiToken)

        assertEquals("dictionary-sync-service", result.username)
        assertTrue(result.tokenPrefix.startsWith("medol_sat_"))
        assertTrue(result.tokenDigest.startsWith("sha256:"))
        assertEquals(userAccountId.toString(), jwt.subject)
        assertEquals("service-account", jwt.getClaimAsString("provider"))
        assertEquals("dictionary-sync-service", jwt.getClaimAsString("username"))
        assertTrue(result.roles.contains("SERVICE_ACCOUNT"))
        assertTrue(result.permissions.contains("register_dictionary:execute"))
        assertFalse(jwt.hasClaim("exp"))
    }

    @Test
    fun rejectsNormalUserWithoutServiceAccountRole() {
        val userAccountId = UUID.randomUUID()
        val adapter = LocalIssueServiceAccountApiTokenAdapter(
            properties = MedolSecurityProperties(jwtSecret = "test-secret-test-secret-test-secret"),
            userAccounts = FakeUserAccounts(
                UserAccountCatalogReadModel(
                    userAccountId = userAccountId,
                    username = "normal-user",
                    providerSubject = userAccountId.toString(),
                    passwordHash = null,
                    active = true,
                    userId = null,
                    sessionId = null,
                    correlationId = null,
                    causationId = null,
                    traceId = null,
                    tenantId = null,
                ),
            ),
            userRoleAssignments = FakeUserRoleAssignments(listOf(role(userAccountId, "DictionaryInitializer"))),
            rolePermissionGrants = FakeRolePermissionGrants(emptyList()),
        )

        assertThrows(IllegalArgumentException::class.java) {
            adapter.execute(
                IssueServiceAccountApiTokenInput(
                    apiTokenId = UUID.randomUUID(),
                    userAccountId = userAccountId,
                    tokenName = "dictionary init",
                ),
            )
        }
    }

    private fun decoder(properties: MedolSecurityProperties) =
        NimbusJwtDecoder
            .withSecretKey(SecretKeySpec(properties.jwtSecret.toByteArray(Charsets.UTF_8), "HmacSHA256"))
            .macAlgorithm(MacAlgorithm.HS256)
            .build()

    private fun role(userAccountId: UUID, roleCode: String) =
        UserRoleAssignmentCatalogReadModel(
            userAccountId = userAccountId,
            username = null,
            roleCode = roleCode,
            roleName = null,
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null,
        )

    private fun permission(roleCode: String, permissionCode: String) =
        RolePermissionGrantCatalogReadModel(
            roleId = null,
            roleCode = roleCode,
            roleName = null,
            permissionCode = permissionCode,
            permissionName = null,
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null,
        )

    private class FakeUserAccounts(private val user: UserAccountCatalogReadModel) : UserAccountCatalogReadModelRepository {
        override fun findAll(pageable: Pageable): Page<UserAccountCatalogReadModel> = PageImpl(listOf(user))
        override fun findAllByCriteria(
            criteria: UserAccountCatalogReadModelCriteria?,
            pageable: Pageable,
        ): Page<UserAccountCatalogReadModel> = PageImpl(listOf(user))

        override fun findById(id: UUID): UserAccountCatalogReadModel? = if (id == user.userAccountId) user else null
        override fun findProjectionById(id: UUID): UserAccountCatalogReadModelProjection? = null
        override fun save(projection: UserAccountCatalogReadModelProjection) = Unit
    }

    private class FakeUserRoleAssignments(
        private val assignments: List<UserRoleAssignmentCatalogReadModel>,
    ) : UserRoleAssignmentCatalogReadModelRepository {
        override fun findAll(pageable: Pageable): Page<UserRoleAssignmentCatalogReadModel> = PageImpl(assignments)
        override fun findAllByCriteria(
            criteria: UserRoleAssignmentCatalogReadModelCriteria?,
            pageable: Pageable,
        ): Page<UserRoleAssignmentCatalogReadModel> = PageImpl(assignments)

        override fun findById(id: UserRoleAssignmentCatalogReadModelKey): UserRoleAssignmentCatalogReadModel? = null
        override fun findProjectionById(id: UserRoleAssignmentCatalogReadModelKey): UserRoleAssignmentCatalogReadModelProjection? = null
        override fun findProjectionsByUserAccountId(userAccountId: UUID): List<UserRoleAssignmentCatalogReadModelProjection> =
            emptyList()

        override fun findProjectionsByRoleCode(roleCode: String): List<UserRoleAssignmentCatalogReadModelProjection> =
            emptyList()

        override fun save(projection: UserRoleAssignmentCatalogReadModelProjection) = Unit
    }

    private class FakeRolePermissionGrants(
        private val grants: List<RolePermissionGrantCatalogReadModel>,
    ) : RolePermissionGrantCatalogReadModelRepository {
        override fun findAll(pageable: Pageable): Page<RolePermissionGrantCatalogReadModel> = PageImpl(grants)
        override fun findAllByCriteria(
            criteria: RolePermissionGrantCatalogReadModelCriteria?,
            pageable: Pageable,
        ): Page<RolePermissionGrantCatalogReadModel> = PageImpl(grants)

        override fun findById(id: RolePermissionGrantCatalogReadModelKey): RolePermissionGrantCatalogReadModel? = null
        override fun findProjectionById(id: RolePermissionGrantCatalogReadModelKey): RolePermissionGrantCatalogReadModelProjection? = null
        override fun findProjectionsByRoleCode(roleCode: String): List<RolePermissionGrantCatalogReadModelProjection> =
            emptyList()

        override fun findProjectionsByPermissionCode(permissionCode: String): List<RolePermissionGrantCatalogReadModelProjection> =
            emptyList()

        override fun save(projection: RolePermissionGrantCatalogReadModelProjection) = Unit
    }
}
