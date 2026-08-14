package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

interface GemiFlRuntimeEngineClient {
    fun health(endpoint: String): GemiFlHealthResponse
    fun startJob(endpoint: String, request: GemiFlJobRequest): GemiFlJobResponse
}

@Component
class RestClientGemiFlRuntimeEngineClient(
    restClientBuilder: RestClient.Builder
) : GemiFlRuntimeEngineClient {
    private val restClient = restClientBuilder.build()

    override fun health(endpoint: String): GemiFlHealthResponse =
        restClient.get()
            .uri("${endpoint.trim().removeSuffix("/")}/healthz")
            .retrieve()
            .body(GemiFlHealthResponse::class.java)
            ?: GemiFlHealthResponse()

    override fun startJob(endpoint: String, request: GemiFlJobRequest): GemiFlJobResponse =
        restClient.post()
            .uri("${endpoint.trim().removeSuffix("/")}/jobs")
            .body(request)
            .retrieve()
            .body(GemiFlJobResponse::class.java)
            ?: GemiFlJobResponse(jobId = request.jobId)
}

data class GemiFlHealthResponse(
    val status: String? = null,
    val nodeName: String? = null
)

data class GemiFlJobRequest(
    @JsonProperty("job_id")
    val jobId: String,
    @JsonProperty("task_id")
    val taskId: String,
    @JsonProperty("round_id")
    val roundId: Int,
    @JsonProperty("my_name")
    val myName: String,
    val role: String,
    val operation: String,
    val input: Map<String, Any?>,
    val output: Map<String, String>,
    @JsonProperty("model_parameter")
    val modelParameter: Map<String, Any?>,
    @JsonProperty("job_parameter")
    val jobParameter: Map<String, Any?>,
    @JsonProperty("runtime_root")
    val runtimeRoot: String
)

data class GemiFlJobResponse(
    val jobId: String? = null,
    val nodeName: String? = null,
    val status: String? = null,
    val exitCode: Int? = null,
    val configPath: String? = null,
    val output: Map<String, Any?> = emptyMap(),
    val progress: Map<String, Any?> = emptyMap(),
    val metrics: List<String> = emptyList()
)
