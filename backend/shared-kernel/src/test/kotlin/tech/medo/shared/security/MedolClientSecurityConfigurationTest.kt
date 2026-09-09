package tech.medo.shared.security

import feign.RequestTemplate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestClient

class MedolClientSecurityConfigurationTest {
    @Test
    fun `feign client sends internal token even when inbound security is disabled`() {
        val properties = MedolSecurityProperties().apply {
            enabled = false
            internalToken = "service-token"
        }
        val template = RequestTemplate()

        MedolFeignSecurityConfiguration()
            .medolInternalTokenRequestInterceptor(properties)
            .apply(template)

        assertThat(template.headers()[InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER])
            .containsExactly("service-token")
    }

    @Test
    fun `rest client builder sends internal token even when inbound security is disabled`() {
        val properties = MedolSecurityProperties().apply {
            enabled = false
            internalToken = "service-token"
        }
        val request = RestClientSecurityRequest()

        val builder = RestClient.builder().requestInterceptor(request)
        MedolRestClientSecurityConfiguration()
            .medolInternalTokenRestClientCustomizer(properties)
            .customize(builder)
        request.client = builder.build()

        request.client.get()
            .uri("http://localhost/internal")
            .retrieve()
            .toBodilessEntity()

        assertThat(request.header(InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER))
            .isEqualTo("service-token")
    }

    private class RestClientSecurityRequest : org.springframework.http.client.ClientHttpRequestInterceptor {
        private val headers = mutableMapOf<String, String?>()

        lateinit var client: RestClient

        fun header(name: String): String? = headers[name]

        override fun intercept(
            request: org.springframework.http.HttpRequest,
            body: ByteArray,
            execution: org.springframework.http.client.ClientHttpRequestExecution
        ): org.springframework.http.client.ClientHttpResponse {
            headers[InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER] =
                request.headers.getFirst(InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER)
            return object : org.springframework.http.client.ClientHttpResponse {
                override fun getStatusCode(): org.springframework.http.HttpStatusCode =
                    org.springframework.http.HttpStatus.OK

                override fun getStatusText(): String = "OK"

                override fun getHeaders(): org.springframework.http.HttpHeaders =
                    org.springframework.http.HttpHeaders.EMPTY

                override fun getBody(): java.io.InputStream =
                    java.io.ByteArrayInputStream(ByteArray(0))

                override fun close() = Unit
            }
        }
    }
}
