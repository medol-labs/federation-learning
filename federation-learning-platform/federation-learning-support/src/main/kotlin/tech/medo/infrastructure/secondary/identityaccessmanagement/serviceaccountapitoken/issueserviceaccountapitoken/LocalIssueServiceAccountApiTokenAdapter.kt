package tech.medo.infrastructure.secondary.identityaccessmanagement.serviceaccountapitoken.issueserviceaccountapitoken

import java.security.MessageDigest
import java.time.Instant
import javax.crypto.spec.SecretKeySpec
import com.nimbusds.jose.jwk.source.ImmutableSecret
import org.springframework.data.domain.PageRequest
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.stereotype.Component
import tech.jhipster.service.filter.StringFilter
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenInput
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenResult
import tech.medo.identityaccessmanagement.issueserviceaccountapitoken.IssueServiceAccountApiTokenService
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.rolepermissiongrantcatalog.RolePermissionGrantCatalogReadModelRepository
import tech.medo.identityaccessmanagement.useraccountcatalogs.UserAccountCatalogReadModelRepository
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelRepository
import tech.medo.shared.security.MedolSecurityProperties

@Component
class LocalIssueServiceAccountApiTokenAdapter(
    private val properties: MedolSecurityProperties,
    private val userAccounts: UserAccountCatalogReadModelRepository,
    private val userRoleAssignments: UserRoleAssignmentCatalogReadModelRepository,
    private val rolePermissionGrants: RolePermissionGrantCatalogReadModelRepository,
) : IssueServiceAccountApiTokenService {
    private val jwtEncoder: JwtEncoder by lazy {
        NimbusJwtEncoder(
            ImmutableSecret(
                SecretKeySpec(
                    properties.jwtSecret.toByteArray(Charsets.UTF_8),
                    "HmacSHA256",
                ),
            ),
        )
    }

    override fun supports(input: IssueServiceAccountApiTokenInput): Boolean = true

    override fun execute(input: IssueServiceAccountApiTokenInput): IssueServiceAccountApiTokenResult {
        val user = userAccounts.findById(input.userAccountId)
            ?: error("Service account user was not found: ${input.userAccountId}.")
        require(user.active == true) {
            "Service account user must be active."
        }

        val roles = roleCodesForUser(input.userAccountId)
        require(SERVICE_ACCOUNT_ROLE in roles) {
            "API tokens can only be issued for users with SERVICE_ACCOUNT role."
        }

        val permissions = roles
            .flatMap(::permissionsForRoleCode)
            .filter { it.isNotBlank() }
            .toSortedSet()
            .toList()
        val issuedAt = Instant.now()
        val tokenPrefix = "medol_sat_${input.apiTokenId.toString().take(8)}"
        val apiToken = encodeJwt(
            input = input,
            username = user.username ?: input.userAccountId.toString(),
            tokenPrefix = tokenPrefix,
            issuedAt = issuedAt,
            roles = roles.sorted(),
            permissions = permissions,
        )

        return IssueServiceAccountApiTokenResult.Succeeded(
            username = user.username ?: input.userAccountId.toString(),
            tokenPrefix = tokenPrefix,
            tokenDigest = "sha256:${sha256Hex(apiToken)}",
            issuedAt = issuedAt.toString(),
            roles = roles.sorted(),
            permissions = permissions,
            apiToken = apiToken,
        )
    }

    private fun encodeJwt(
        input: IssueServiceAccountApiTokenInput,
        username: String,
        tokenPrefix: String,
        issuedAt: Instant,
        roles: List<String>,
        permissions: List<String>,
    ): String {
        val claims = JwtClaimsSet.builder()
            .subject(input.userAccountId.toString())
            .issuedAt(issuedAt)
            .claim("username", username)
            .claim("roles", roles)
            .claim("permissions", permissions)
            .claim("provider", "service-account")
            .claim("apiTokenId", input.apiTokenId.toString())
            .claim("tokenName", input.tokenName)
            .claim("tokenPrefix", tokenPrefix)
            .build()
        val header = JwsHeader.with(MacAlgorithm.HS256).build()
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).tokenValue
    }

    private fun roleCodesForUser(userAccountId: java.util.UUID): Set<String> =
        userRoleAssignments.findAllByCriteria(
            UserRoleAssignmentCatalogReadModelCriteria().apply {
                this.userAccountId = exact(userAccountId.toString())
            },
            PageRequest.of(0, MAX_AUTH_ITEMS),
        )
            .content
            .mapNotNull { it.roleCode }
            .filter { it.isNotBlank() }
            .toSet()

    private fun permissionsForRoleCode(roleCode: String): List<String> =
        rolePermissionGrants.findAllByCriteria(
            RolePermissionGrantCatalogReadModelCriteria().apply {
                this.roleCode = exact(roleCode)
            },
            PageRequest.of(0, MAX_AUTH_ITEMS),
        )
            .content
            .mapNotNull { it.permissionCode }
            .filter { it.isNotBlank() }

    private fun exact(value: String): StringFilter =
        StringFilter().apply {
            equals = value
        }

    private fun sha256Hex(value: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(value.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }

    companion object {
        private const val MAX_AUTH_ITEMS = 1000
        private const val SERVICE_ACCOUNT_ROLE = "SERVICE_ACCOUNT"
    }
}
