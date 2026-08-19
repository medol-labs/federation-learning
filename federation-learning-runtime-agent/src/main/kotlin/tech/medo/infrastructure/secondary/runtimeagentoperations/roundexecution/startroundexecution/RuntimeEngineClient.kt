package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

interface RuntimeEngineClient {
    fun health(endpoint: String): RuntimeEngineHealthResponse
    fun startJob(endpoint: String, request: RuntimeEngineJobRequest): RuntimeEngineJobResponse
    fun getJob(endpoint: String, jobId: String): RuntimeEngineJobResponse
}

@Component
class RestClientRuntimeEngineClient(
    restClientBuilder: RestClient.Builder,
    private val objectMapper: ObjectMapper
) : RuntimeEngineClient {
    private val log = LoggerFactory.getLogger(javaClass)
    private val restClient = restClientBuilder.build()

    override fun health(endpoint: String): RuntimeEngineHealthResponse =
        restClient.get()
            .uri("${endpoint.trim().removeSuffix("/")}/healthz")
            .retrieve()
            .body(RuntimeEngineHealthResponse::class.java)
            ?: RuntimeEngineHealthResponse()

    override fun startJob(endpoint: String, request: RuntimeEngineJobRequest): RuntimeEngineJobResponse {
        val body = objectMapper.writeValueAsString(request)
        log.info("Submitting runtime engine job. endpoint={}, body={}", endpoint, body)
        return restClient.post()
            .uri("${endpoint.trim().removeSuffix("/")}/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve()
            .body(RuntimeEngineJobResponse::class.java)
            ?: RuntimeEngineJobResponse(jobId = request.jobId)
    }

    override fun getJob(endpoint: String, jobId: String): RuntimeEngineJobResponse =
        restClient.get()
            .uri("${endpoint.trim().removeSuffix("/")}/jobs/$jobId")
            .retrieve()
            .body(RuntimeEngineJobResponse::class.java)
            ?: RuntimeEngineJobResponse(jobId = jobId)
}

data class RuntimeEngineHealthResponse(
    val status: String? = null,
    val nodeName: String? = null
)

data class RuntimeEngineJobRequest(
    val jobId: String,
    val roundId: Int,
    val nodeName: String,
    val role: String,
    val operation: String,
    val input: Map<String, Any?>,
    val output: Map<String, String>,
    val modelParameter: Map<String, Any?>,
    val jobParameter: Map<String, Any?>,
    val runtimeRoot: String
)

data class RuntimeEngineJobResponse(
    val jobId: String? = null,
    val nodeName: String? = null,
    val status: String? = null,
    val exitCode: Int? = null,
    val configPath: String? = null,
    val output: Map<String, Any?> = emptyMap(),
    val progress: Map<String, Any?> = emptyMap(),
    val metrics: List<String> = emptyList()
)
