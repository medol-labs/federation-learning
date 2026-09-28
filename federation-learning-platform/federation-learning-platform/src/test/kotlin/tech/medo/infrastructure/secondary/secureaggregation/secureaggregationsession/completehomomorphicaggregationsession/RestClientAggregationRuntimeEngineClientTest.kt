package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestClient

class RestClientAggregationRuntimeEngineClientTest {
    @Test
    fun downloadsArtifactBytesFromRuntimeEngine() {
        val builder = RestClient.builder()
        val server = MockRestServiceServer.bindTo(builder).build()
        val client = RestClientAggregationRuntimeEngineClient(builder)

        server.expect(requestTo("http://runtime-engine/jobs/job-1/artifacts/globalModel"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("""{"weights":[0.2]}""", MediaType.APPLICATION_JSON))

        val artifact = client.downloadArtifact("http://runtime-engine", "job-1", "globalModel")

        assertEquals("""{"weights":[0.2]}""", artifact.toString(Charsets.UTF_8))
        server.verify()
    }

    @Test
    fun downloadsHtmlArtifactBytesFromRuntimeEngine() {
        val builder = RestClient.builder()
        val server = MockRestServiceServer.bindTo(builder).build()
        val client = RestClientAggregationRuntimeEngineClient(builder)
        val html = "<!DOCTYPE html><html><body>Report</body></html>"

        server.expect(requestTo("http://runtime-engine/jobs/job-1/artifacts/globalModel"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(html, MediaType.TEXT_HTML))

        val artifact = client.downloadArtifact("http://runtime-engine", "job-1", "globalModel")

        assertEquals(html, artifact.toString(Charsets.UTF_8))
        server.verify()
    }
}
