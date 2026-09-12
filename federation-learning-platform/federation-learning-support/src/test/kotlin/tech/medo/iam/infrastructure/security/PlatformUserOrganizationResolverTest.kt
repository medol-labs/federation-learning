package tech.medo.iam.infrastructure.security

import java.util.UUID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.header
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestClient
import tech.medo.shared.security.InternalTokenAuthenticationFilter
import tech.medo.shared.security.MedolSecurityProperties

class PlatformUserOrganizationResolverTest {
    @Test
    fun resolvesActiveOrganizationMembershipFromPlatform() {
        val builder = RestClient.builder()
        val server = MockRestServiceServer.bindTo(builder).build()
        val userAccountId = uuid("11111111-1111-4111-8111-111111111111")
        val organizationId = uuid("22222222-2222-4222-8222-222222222222")
        val resolver = PlatformUserOrganizationResolver(
            restClientBuilder = builder,
            integrationProperties = FederationLearningPlatformIntegrationProperties(
                endpoint = "http://platform:8081/",
            ),
            securityProperties = MedolSecurityProperties().apply {
                internalToken = "service-token"
            },
        )

        server.expect(
            ExpectedCount.once(),
            requestTo(
                "http://platform:8081/userorganizationmembership/userorganizationmembershipdirectory" +
                    "?userAccountId.equals=$userAccountId&state.equals=ACTIVE&size=20",
            ),
        )
            .andExpect(method(HttpMethod.GET))
            .andExpect(header(InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER, "service-token"))
            .andRespond(
                withSuccess(
                    """{"content":[{"organizationId":"$organizationId","organizationName":"Acme","state":"ACTIVE"}]}""",
                    MediaType.APPLICATION_JSON,
                ),
            )

        assertEquals(organizationId, resolver.resolveOrganizationId(userAccountId))
        server.verify()
    }

    private fun uuid(value: String): UUID = UUID.fromString(value)
}
