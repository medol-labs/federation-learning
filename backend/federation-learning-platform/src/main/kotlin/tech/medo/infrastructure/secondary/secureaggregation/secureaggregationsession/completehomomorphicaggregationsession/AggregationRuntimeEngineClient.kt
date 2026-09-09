package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

interface AggregationRuntimeEngineClient {
    fun submit(endpoint: String, request: AggregationRuntimeJobRequest): AggregationRuntimeJobResponse
    fun getJob(endpoint: String, jobId: String): AggregationRuntimeJobResponse
    fun downloadArtifact(endpoint: String, jobId: String, artifactName: String): ByteArray
}

@Component
class RestClientAggregationRuntimeEngineClient(restClientBuilder: RestClient.Builder) : AggregationRuntimeEngineClient {
    private val restClient = restClientBuilder.build()

    override fun submit(endpoint: String, request: AggregationRuntimeJobRequest): AggregationRuntimeJobResponse =
        requireNotNull(
            restClient.post()
                .uri("${endpoint.trimEnd('/')}/jobs")
                .body(request)
                .retrieve()
                .body(AggregationRuntimeJobResponse::class.java)
        ) { "Aggregation runtime returned an empty submission response." }

    override fun getJob(endpoint: String, jobId: String): AggregationRuntimeJobResponse =
        requireNotNull(
            restClient.get()
                .uri("${endpoint.trimEnd('/')}/jobs/{jobId}", jobId)
                .retrieve()
                .body(AggregationRuntimeJobResponse::class.java)
        ) { "Aggregation runtime returned an empty job response for $jobId." }

    override fun downloadArtifact(endpoint: String, jobId: String, artifactName: String): ByteArray =
        requireNotNull(
            restClient.get()
                .uri("${endpoint.trimEnd('/')}/jobs/{jobId}/artifacts/{artifactName}", jobId, artifactName)
                .retrieve()
                .body(ByteArray::class.java)
        ) { "Aggregation runtime returned an empty artifact for $jobId/$artifactName." }
}

data class AggregationRuntimeJobRequest(
    val jobId: String,
    val roundId: Int,
    val nodeName: String,
    val role: String,
    val operation: String,
    val input: Map<String, Any>,
    val output: Map<String, String> = emptyMap(),
    val modelParameter: Map<String, Any>,
    val jobParameter: Map<String, Any> = emptyMap()
)

data class AggregationRuntimeJobResponse(
    val jobId: String,
    val nodeName: String,
    val status: String,
    val exitCode: Int? = null,
    val output: Map<String, Any?> = emptyMap()
)
