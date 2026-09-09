package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.dispatchparticipantexecutionplan

import org.junit.jupiter.api.Test
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
import java.util.UUID

class RestClientRuntimeAgentExecutionPlanClientTest {
    @Test
    fun sendsInternalTokenWhenDispatchingExecutionPlan() {
        val builder = RestClient.builder()
        val server = MockRestServiceServer.bindTo(builder).build()
        val client = RestClientRuntimeAgentExecutionPlanClient(
            restClientBuilder = builder,
            securityProperties = MedolSecurityProperties().apply {
                internalToken = "service-token"
            },
        )

        server.expect(
            ExpectedCount.once(),
            requestTo("http://runtime-agent:8082/roundexecution/receiveparticipantexecutionplan"),
        )
            .andExpect(method(HttpMethod.POST))
            .andExpect(header(InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER, "service-token"))
            .andRespond(withSuccess("""{"executionPlanId":"11111111-1111-4111-8111-111111111111"}""", MediaType.APPLICATION_JSON))

        client.receiveParticipantExecutionPlan(
            endpoint = "http://runtime-agent:8082",
            request = request(),
        )

        server.verify()
    }

    private fun request(): ReceiveParticipantExecutionPlanRequest =
        ReceiveParticipantExecutionPlanRequest(
            executionPlanId = uuid("11111111-1111-4111-8111-111111111111"),
            roundExecutionId = uuid("11111111-1111-4111-8111-111111111111"),
            executionSessionId = uuid("22222222-2222-4222-8222-222222222222"),
            trainingJobId = uuid("33333333-3333-4333-8333-333333333333"),
            trainingRunConfigurationId = uuid("44444444-4444-4444-8444-444444444444"),
            featureSchemaId = uuid("55555555-5555-4555-8555-555555555555"),
            roundId = uuid("66666666-6666-4666-8666-666666666666"),
            roundNumber = 1,
            runtimeId = uuid("77777777-7777-4777-8777-777777777777"),
            organizationId = uuid("88888888-8888-4888-8888-888888888888"),
            baseModelId = uuid("99999999-9999-4999-8999-999999999999"),
            baseModelArtifactUri = "oci://registry.example.com/fl/model@sha256:abc",
            baseModelRegistryRef = "oci://registry.example.com/fl",
            baseModelFormat = "ONNX",
            baseModelArtifactDigest = "sha256:abc",
            baseModelSignatureUri = "oci://registry.example.com/fl/model.sig",
            secureAggregationRequired = true,
            secureAggregationSessionId = uuid("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            encryptionScheme = "PAILLIER",
            publicKeyVersion = "local-dev-v1",
            publicKeyRef = "local://secure-aggregation/public-keys/local-dev-v1",
            encryptedParameterScale = 1000000,
        )

    private fun uuid(value: String): UUID = UUID.fromString(value)
}
