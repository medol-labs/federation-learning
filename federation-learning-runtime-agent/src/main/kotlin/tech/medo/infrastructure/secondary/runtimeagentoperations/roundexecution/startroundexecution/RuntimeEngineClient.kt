package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

interface RuntimeEngineClient {
    fun health(endpoint: String): RuntimeEngineHealthResponse
    fun startJob(endpoint: String, request: RuntimeEngineJobRequest): RuntimeEngineJobResponse
}

@Component
class RestClientRuntimeEngineClient(
    restClientBuilder: RestClient.Builder
) : RuntimeEngineClient {
    private val restClient = restClientBuilder.build()

    override fun health(endpoint: String): RuntimeEngineHealthResponse =
        restClient.get()
            .uri("${endpoint.trim().removeSuffix("/")}/healthz")
            .retrieve()
            .body(RuntimeEngineHealthResponse::class.java)
            ?: RuntimeEngineHealthResponse()

    override fun startJob(endpoint: String, request: RuntimeEngineJobRequest): RuntimeEngineJobResponse =
        restClient.post()
            .uri("${endpoint.trim().removeSuffix("/")}/jobs")
            .body(request)
            .retrieve()
            .body(RuntimeEngineJobResponse::class.java)
            ?: RuntimeEngineJobResponse(jobId = request.jobId)
}

data class RuntimeEngineHealthResponse(
    val status: String? = null,
    val nodeName: String? = null
)

data class RuntimeEngineJobRequest(
    @param:JsonProperty("job_id")
    val jobId: String,
    @param:JsonProperty("task_id")
    val taskId: String,
    @param:JsonProperty("round_id")
    val roundId: Int,
    @param:JsonProperty("my_name")
    val myName: String,
    val role: String,
    val operation: String,
    val input: Map<String, Any?>,
    val output: Map<String, String>,
    @param:JsonProperty("model_parameter")
    val modelParameter: Map<String, Any?>,
    @param:JsonProperty("job_parameter")
    val jobParameter: Map<String, Any?>,
    @param:JsonProperty("runtime_root")
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
