package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI
import java.time.Duration

@ConfigurationProperties("federation-learning.aggregation")
data class AggregationRuntimeProperties(
    val enabled: Boolean = true,
    val mode: String = "embedded",
    val runtimeEngineEndpoint: String = "",
    val supportEndpoint: String = "http://localhost:8080",
    val internalToken: String = "local-dev-internal-token",
    val nodeName: String = "platform-aggregator",
    val aggregationAlgorithm: String = "FED_AVG",
    val pollInterval: Duration = Duration.ofSeconds(1),
    val jobTimeout: Duration = Duration.ofMinutes(5)
) {
    fun usesEmbedded(): Boolean = mode.equals("embedded", ignoreCase = true)

    fun requireRuntimeEngineEndpoint(): String {
        val endpoint = runtimeEngineEndpoint.trim().removeSuffix("/")
        require(endpoint.isNotBlank()) {
            "federation-learning.aggregation.runtime-engine-endpoint is required when FL_AGGREGATION_MODE=runtime-engine."
        }
        require(!looksLikeFrontendGatewayRoot(endpoint)) {
            "federation-learning.aggregation.runtime-engine-endpoint points to a frontend gateway root. " +
                "Use the runtime engine API endpoint, or set FL_AGGREGATION_MODE=embedded for dev/k3d."
        }
        return endpoint
    }

    private fun looksLikeFrontendGatewayRoot(endpoint: String): Boolean = runCatching {
        val uri = URI(endpoint)
        val rootPath = uri.path.isNullOrBlank() || uri.path == "/"
        rootPath && (uri.port == 30080 || uri.port == 9080)
    }.getOrDefault(false)
}
