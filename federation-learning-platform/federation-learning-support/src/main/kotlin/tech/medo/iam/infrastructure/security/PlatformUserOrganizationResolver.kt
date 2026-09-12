package tech.medo.iam.infrastructure.security

import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException
import org.springframework.web.util.UriComponentsBuilder
import tech.medo.shared.security.InternalTokenAuthenticationFilter
import tech.medo.shared.security.MedolSecurityProperties

@ConfigurationProperties(prefix = "integration.federation-learning-platform")
@Component
data class FederationLearningPlatformIntegrationProperties(
    var endpoint: String = "",
)

@Component
class PlatformUserOrganizationResolver(
    restClientBuilder: RestClient.Builder,
    private val integrationProperties: FederationLearningPlatformIntegrationProperties,
    private val securityProperties: MedolSecurityProperties,
) : AuthOrganizationResolver {
    private val restClient = restClientBuilder.build()

    override fun resolveOrganizationId(userAccountId: UUID): UUID? {
        val baseUrl = integrationProperties.endpoint.trim().removeSuffix("/")
        if (baseUrl.isBlank()) {
            return null
        }

        val uri = UriComponentsBuilder
            .fromUriString("$baseUrl/userorganizationmembership/userorganizationmembershipdirectory")
            .queryParam("userAccountId.equals", userAccountId.toString())
            .queryParam("state.equals", "ACTIVE")
            .queryParam("size", "20")
            .build()
            .toUri()

        return try {
            restClient.get()
                .uri(uri)
                .headers { headers ->
                    val token = securityProperties.internalToken.trim()
                    if (token.isNotBlank()) {
                        headers.set(InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER, token)
                    }
                }
                .retrieve()
                .body(PlatformUserOrganizationMembershipPage::class.java)
                ?.content
                .orEmpty()
                .filter { membership -> membership.organizationId != null && isActive(membership.state) }
                .sortedWith(
                    compareBy<PlatformUserOrganizationMembership> { it.organizationName ?: "" }
                        .thenBy { it.organizationId.toString() }
                )
                .firstOrNull()
                ?.organizationId
        } catch (ex: RestClientException) {
            log.warn("Could not resolve organization membership for user account {}.", userAccountId, ex)
            null
        }
    }

    private fun isActive(state: String?): Boolean =
        state
            ?.replace(Regex("[^A-Za-z0-9]"), "")
            ?.equals("ACTIVE", ignoreCase = true) == true

    companion object {
        private val log = LoggerFactory.getLogger(PlatformUserOrganizationResolver::class.java)
    }
}

data class PlatformUserOrganizationMembershipPage(
    val content: List<PlatformUserOrganizationMembership> = emptyList(),
)

data class PlatformUserOrganizationMembership(
    val organizationId: UUID? = null,
    val organizationName: String? = null,
    val state: String? = null,
)
